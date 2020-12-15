package com.omf.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

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
import com.omf.entity.Vendor;
import com.omf.exception.UserAlreadyExistException;
import com.omf.repository.VendorRepository;
import com.omf.service.EmailService;
import com.omf.service.VendorService;

import net.bytebuddy.utility.RandomString;

@Service
public class VendorServiceImpl implements VendorService {
	
	private static final long OTP_VALID_DURATION = 5 * 60 * 1000;   // 5 minutes
	
	@Autowired
	private VendorRepository vendorRepository;

	@Autowired
    PasswordEncoder passcodeEncoder;
	
	@Autowired
    EmailService emailService;
	
	@Override
	public ResponseEntity<String> registerUser(UserData vendorDto) throws Exception {
		//Let's check if user already registered with us
        if(checkIfUserExist(vendorDto.getEmailId())){
            throw new UserAlreadyExistException("User already exists for this email");
        }
        Vendor vendor = new Vendor();
        BeanUtils.copyProperties(vendorDto, vendor);
        encodePassword(vendor, vendorDto);
        
        //Generating OTP
        String OTP = RandomString.make(8);         
        vendor.setOneTimePassword(OTP);
        vendor.setOtpRequestedTime(new Date());
        
        //Sending OTP
        sendOTPEmail(vendor);
        vendor.setStatus("created");
        vendorRepository.save(vendor);
		return new ResponseEntity<>("Registered Successfully", HttpStatus.OK);
	}
	
    public boolean checkIfUserExist(String email) {
        return vendorRepository.findByEmailIdIgnoreCase(email) !=null ? true : false;
    }
	
	private void encodePassword(Vendor vendor, UserData user) {
		vendor.setPassword(passcodeEncoder.encode(user.getPassword()));
	}
	
	public void sendOTPEmail(Vendor user)
            throws UnsupportedEncodingException, MessagingException {
    	String to = user.getEmailId();
    	String subject = "Welcome to OrderMyFood";
    	String text = "Hi "+ user.getFirstName()+ ", Please enter the following otp to activate your account. OTP: "+user.getOneTimePassword();
    	emailService.sendSimpleMessage(to, subject, text);     
    }
	
	public ResponseEntity<String> verifyOtp(OTP otp) {
		// TODO Auto-generated method stub
		Vendor vendorEntity = vendorRepository.findByEmailIdIgnoreCase(otp.getEmailId());
		if(vendorEntity != null && vendorEntity.getStatus().equals("created") && vendorEntity.getOneTimePassword().equals(otp.getOtp())) {
			long currentTimeInMillis = System.currentTimeMillis();
			long otpRequestedTimeInMillis = vendorEntity.getOtpRequestedTime().getTime();
			if (otpRequestedTimeInMillis + OTP_VALID_DURATION < currentTimeInMillis) {
				//OTP expires
				return new ResponseEntity<>("OTP Expired", HttpStatus.OK);
			} else {
				vendorEntity.setStatus("verified");
				vendorRepository.save(vendorEntity);
				return new ResponseEntity<>("Verified Successfully", HttpStatus.OK);
			}
		}
		return new ResponseEntity<>("Verification failed", HttpStatus.OK);
	}
	
	@Override
	public Vendor loginVendor(LoginDto dto) throws Exception {
		Vendor vendor = vendorRepository.findByEmailIdIgnoreCase(dto.getEmailId());
		if (vendor == null) {
			// User not found
			throw new Exception("User Not Found");
		} else if (!vendor.getStatus().equals("verified")) {
			// User Not verified
			throw new Exception("User Not Verified");
		} else if (passcodeEncoder.matches(dto.getPassword(), vendor.getPassword())) {
			// User logged in
			vendor.setPassword("");
			return vendor;
		}
		return null;
	}

	@Override
	public List<Vendor> getVendor() {
		return vendorRepository.findAll();
	}

	@Override
	public Vendor getVendorById(Long vendorId) {
		Vendor vendor = vendorRepository.findById(vendorId).orElse(new Vendor());
		return vendor;
	}

	@Override
	public Vendor editVendorById(Long vendorId, UserData vendorWithUpdate) throws Exception {
		Vendor vendor = vendorRepository.findById(vendorId).orElse(new Vendor());
		if(vendor.getCustomerId() != null) {
			BeanUtils.copyProperties(vendorWithUpdate, vendor);
			return vendorRepository.save(vendor);
		} else {
			// User not found
			throw new Exception("Vendor Not Found");
		}
	}

}
