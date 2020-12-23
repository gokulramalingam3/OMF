package com.omf.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.omf.dto.ForgotDTO;
import com.omf.dto.LoginDto;
import com.omf.dto.OTP;
import com.omf.dto.ResetPasswordDTO;
import com.omf.dto.UserData;
import com.omf.entity.Customer;
import com.omf.exception.UserAlreadyExistException;
import com.omf.exception.UserNotFoundException;
import com.omf.repository.CustomerRepository;
import com.omf.service.CustomerService;
import com.omf.service.EmailService;
import com.omf.utils.Utility;

import net.bytebuddy.utility.RandomString;

@Service
public class CustomerServiceImpl implements CustomerService 
{
	private static final long OTP_VALID_DURATION = 5 * 60 * 1000;   // 5 minutes
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	@Autowired
    EmailService emailService;
	
	@Override
	public ResponseEntity<String> registerUser(UserData customerDto) throws Exception {
		//Let's check if user already registered with us
        if(checkIfUserExist(customerDto.getEmailId())){
            throw new UserAlreadyExistException("User already exists for this email");
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        encodePassword(customer, customerDto);
        
        //Generating OTP
        String OTP = RandomString.make(8);         
        customer.setOneTimePassword(OTP);
        customer.setOtpRequestedTime(new Date());
        
        //Sending OTP
        sendOTPEmail(customer);
        customer.setStatus("created");
        customerRepository.save(customer);
		return new ResponseEntity<>("Registered Successfully", HttpStatus.OK);
	}
	
    public boolean checkIfUserExist(String email) {
        return customerRepository.findByEmailIdIgnoreCase(email) !=null ? true : false;
    }
	
	private void encodePassword(Customer userEntity, UserData user) {
		userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
	}
	
	public void sendOTPEmail(Customer user)
            throws UnsupportedEncodingException, MessagingException {
    	String to = user.getEmailId();
    	String subject = "Welcome to OrderMyFood";
    	String text = "<p>Hello " + user.getFirstName() + "</p>"
        + "<p>Please enter the following otp to activate your account. "
        + "One Time Password to activate:</p>"
        + "<p><b>" + user.getOneTimePassword() + "</b></p>"
        + "<br>"
        + "<p>Note: This OTP is set to expire in 5 minutes.</p>";
    	emailService.sendSimpleMessage(to, subject, text);     
    }
	
	public ResponseEntity<String> verifyOtp(OTP otp) {
		Customer customerEntity = customerRepository.findByEmailIdIgnoreCase(otp.getEmailId());
		if(customerEntity != null && customerEntity.getStatus().equals("created") && customerEntity.getOneTimePassword().equals(otp.getOtp())) {
			long currentTimeInMillis = System.currentTimeMillis();
			long otpRequestedTimeInMillis = customerEntity.getOtpRequestedTime().getTime();
			if (otpRequestedTimeInMillis + OTP_VALID_DURATION < currentTimeInMillis) {
				//OTP expires
				return new ResponseEntity<>("OTP Expired", HttpStatus.OK);
			} else {
				customerEntity.setStatus("verified");
				customerEntity.setOneTimePassword(null);
				customerEntity.setOtpRequestedTime(null);
				customerRepository.save(customerEntity);
				return new ResponseEntity<>("Verified Successfully", HttpStatus.OK);
			}
		}
		return new ResponseEntity<>("Verification failed", HttpStatus.OK);
	}
	
	public Customer loginCustomer(LoginDto dto) throws Exception {
		Customer customer = customerRepository.findByEmailIdIgnoreCase(dto.getEmailId());
		if (customer == null) {
			// User not found
			throw new Exception("User Not Found");
		} else if (!customer.getStatus().equals("verified")) {
			// User Not verified
			throw new Exception("User Not Verified");
		} else if (passwordEncoder.matches(dto.getPassword(), customer.getPassword())) {
			// User logged in
			customer.setPassword("");
			return customer;
		}
		return null;
	}
	
	// Get Specific customer By Id
	@Override
	public Customer getCustomerById(Long customerId) {
		Customer customer = customerRepository.findById(customerId).orElse(new Customer());
		return  customer;
	}

	// Edit Specific customer By Id
	@Override
	public Customer editCustomerById(@PathVariable Long customerId, @RequestBody Customer Updatedcustomer) throws Exception {
		Customer cust = customerRepository.findById(customerId).orElse(new Customer());
		if(cust.getCustomerId() != null) {
			BeanUtils.copyProperties(Updatedcustomer, cust);
			return customerRepository.save(cust);
		} else {
			// User not found
			throw new Exception("Customer Not Found");
		}		
	}

	@Override
	public ResponseEntity<String> processForgotPassword(HttpServletRequest httpServletRequest, ForgotDTO forgotDto) throws UserNotFoundException {
		String email = forgotDto.getEmailId();
		String token = RandomString.make(30);
		Customer customer = customerRepository.findByEmailIdIgnoreCase(email);
        if (customer != null) {
            customer.setResetPasswordToken(token);
            customerRepository.save(customer);
        } else {
            throw new UserNotFoundException("Could not find any customer with the email " + email);
        }
		sendResetTokenEmail(Utility.getSiteURL(httpServletRequest), token, customer);
		return new ResponseEntity<>("Email sent Successfully", HttpStatus.OK);
	}
	
	private void sendResetTokenEmail(String contextPath, String token, Customer customer) {
		String url = contextPath + "/customer/reset_password?token=" + token;
		String message = "<p>Hello,</p>"
        + "<p>You have requested to reset your password.</p>"
        + "<p>Click the link below to change your password:</p>"
        + "<p><a href=\"" + url + "\">Change my password</a></p>"
        + "<br>"
        + "<p>Ignore this email if you do remember your password, "
        + "or you have not made the request.</p>";
		emailService.sendSimpleMessage(customer.getEmailId(), "Reset Password", message);  
	}

	@Override
	public ResponseEntity<String> processResetPassword(String token, ResetPasswordDTO resetPasswordDTO) {
	    String password = resetPasswordDTO.getPassword();
	    Customer customer = getByResetPasswordToken(token);
	    if (customer == null) {
	    	return new ResponseEntity<>("Invalid Token", HttpStatus.OK);
	    } else {           
	        updatePassword(customer, password);
	        return new ResponseEntity<>("You have successfully changed your password.", HttpStatus.OK);
	    }
	}
	
	public Customer getByResetPasswordToken(String token) {
		return customerRepository.findByResetPasswordToken(token);
	}
	
	public void updatePassword(Customer customer, String newPassword) {
		String encodedPassword = passwordEncoder.encode(newPassword);
		customer.setPassword(encodedPassword);
		customer.setResetPasswordToken(null);
		customerRepository.save(customer);
	}
}
