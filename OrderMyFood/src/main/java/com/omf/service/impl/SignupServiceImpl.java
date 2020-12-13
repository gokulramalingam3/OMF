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

import com.omf.dto.OTP;
import com.omf.dto.UserData;
import com.omf.entity.UserEntity;
import com.omf.exception.UserAlreadyExistException;
import com.omf.repository.UserRepository;
import com.omf.service.EmailService;
import com.omf.service.SignupService;

import net.bytebuddy.utility.RandomString;

@Service
public class SignupServiceImpl implements SignupService {
	
	private static final long OTP_VALID_DURATION = 5 * 60 * 1000;   // 5 minutes
	 
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	@Autowired
    EmailService emailService;
	
	public ResponseEntity<String> registerUser(UserData userDto) throws UserAlreadyExistException,Exception {
		
		//Let's check if user already registered with us
        if(checkIfUserExist(userDto.getEmailId())){
            throw new UserAlreadyExistException("User already exists for this email");
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        encodePassword(userEntity, userDto);
        
        //Generating OTP
        String OTP = RandomString.make(8);         
        userEntity.setOneTimePassword(OTP);
        userEntity.setOtpRequestedTime(new Date());
        
        //Sending OTP
        sendOTPEmail(userEntity);
        userEntity.setStatus("created");
        userRepository.save(userEntity);
		return new ResponseEntity<>("Registered Successfully", HttpStatus.OK);
	}

    @Override
    public boolean checkIfUserExist(String email) {
        return userRepository.findByEmailIdIgnoreCase(email) !=null ? true : false;
    }

    private void encodePassword( UserEntity userEntity, UserData user){
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
	}
    
    public void sendOTPEmail(UserEntity user)
            throws UnsupportedEncodingException, MessagingException {
    	String to = user.getEmailId();
    	String subject = "Welcome to OrderMyFood";
    	String text = "Hi "+ user.getFirstName()+ ", Please enter the following otp to activate your account. OTP: "+user.getOneTimePassword();
    	emailService.sendSimpleMessage(to, subject, text);     
    }

	public ResponseEntity<String> verifyOtp(OTP otp) {
		// TODO Auto-generated method stub
		UserEntity userEntity = userRepository.findByEmailIdIgnoreCase(otp.getEmailId());
		if(userEntity != null && userEntity.getStatus().equals("created") && userEntity.getOneTimePassword().equals(otp.getOtp())) {
			long currentTimeInMillis = System.currentTimeMillis();
			long otpRequestedTimeInMillis = userEntity.getOtpRequestedTime().getTime();
			if (otpRequestedTimeInMillis + OTP_VALID_DURATION < currentTimeInMillis) {
				//OTP expires
				return new ResponseEntity<>("OTP Expired", HttpStatus.OK);
			} else {
				userEntity.setStatus("verified");
				userRepository.save(userEntity);
				return new ResponseEntity<>("Verified Successfully", HttpStatus.OK);
			}
		}
		return new ResponseEntity<>("Verification failed", HttpStatus.METHOD_FAILURE);
	}
}
