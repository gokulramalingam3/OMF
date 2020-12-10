package com.omf.service;

import org.springframework.http.ResponseEntity;

import com.omf.dto.UserData;

public interface SignupService {
	
	ResponseEntity<String> registerUser(UserData customerDto) throws Exception;

	boolean checkIfUserExist(String email);

}
