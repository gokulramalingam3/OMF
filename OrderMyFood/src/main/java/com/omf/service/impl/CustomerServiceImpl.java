package com.omf.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.MessagingException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.omf.dto.LoginDto;
import com.omf.dto.OTP;
import com.omf.dto.UserData;
import com.omf.entity.Customer;
import com.omf.exception.UserAlreadyExistException;
import com.omf.repository.CustomerRepository;
import com.omf.service.CustomerService;
import com.omf.service.EmailService;

import net.bytebuddy.utility.RandomString;

@Service
public class CustomerServiceImpl implements CustomerService 
{
	private static final long OTP_VALID_DURATION = 5 * 60 * 1000;   // 5 minutes
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
    PasswordEncoder passcodeEncoder;
	
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
		userEntity.setPassword(passcodeEncoder.encode(user.getPassword()));
	}
	
	public Customer loginCustomer(LoginDto dto) throws Exception {
		Customer customer = customerRepository.findByEmailIdIgnoreCase(dto.getEmailId());
		if (customer == null) {
			// User not found
			throw new Exception("User Not Found");
		} else if (!customer.getStatus().equals("verified")) {
			// User Not verified
			throw new Exception("User Not Verified");
		} else if (passcodeEncoder.matches(dto.getPassword(), customer.getPassword())) {
			// User logged in
			customer.setPassword("");
			return customer;
		}
		return null;
	}
	
	public void sendOTPEmail(Customer user)
            throws UnsupportedEncodingException, MessagingException {
    	String to = user.getEmailId();
    	String subject = "Welcome to OrderMyFood";
    	String text = "Hi "+ user.getFirstName()+ ", Please enter the following otp to activate your account. OTP: "+user.getOneTimePassword();
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
				customerRepository.save(customerEntity);
				return new ResponseEntity<>("Verified Successfully", HttpStatus.OK);
			}
		}
		return new ResponseEntity<>("Verification failed", HttpStatus.OK);
	}
	
	// Get Specific customer By Id
	@Override
	public Customer getCustomerById(Long customerId) {
		Customer customer = customerRepository.findById(customerId).orElse(new Customer());
		return  customer;
	}

	
}
