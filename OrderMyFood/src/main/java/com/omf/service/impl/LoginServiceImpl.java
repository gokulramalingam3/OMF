package com.omf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.omf.dto.LoginDto;
import com.omf.entity.UserEntity;
import com.omf.repository.UserRepository;
import com.omf.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    PasswordEncoder passcodeEncoder;

	@Override
	public UserEntity loginUser(LoginDto dto) throws Exception {
		UserEntity userEntity = userRepository.findByEmailIdIgnoreCaseAndRole(dto.getEmailId(), dto.getRole());
		if (userEntity == null) {
			//User not found
			throw new Exception("User Not Found");
		} else if(!userEntity.getStatus().equals("verified")) {
			//User Not verified
			throw new Exception("User Not Verified");
		} else if(passcodeEncoder.matches(dto.getPassword(),userEntity.getPassword())) {
			//User logged in
			userEntity.setPassword("");
			return userEntity;
		}
		return null;
	}
}
