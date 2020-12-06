package com.omf.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.omf.dto.UserData;
import com.omf.entity.UserEntity;
import com.omf.exception.UserAlreadyExistException;
import com.omf.repository.UserRepository;
import com.omf.service.SignupService;

@Service
public class SignupServiceImpl implements SignupService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Bean
	PasswordEncoder getEncoder() {
	    return new BCryptPasswordEncoder(5);
	}
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	public ResponseEntity<String> registerCustomer(UserData userDto) throws UserAlreadyExistException,Exception {
		
		//Let's check if user already registered with us
        if(checkIfUserExist(userDto.getEmailId())){
            throw new UserAlreadyExistException("User already exists for this email");
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        encodePassword(userEntity, userDto);
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

}
