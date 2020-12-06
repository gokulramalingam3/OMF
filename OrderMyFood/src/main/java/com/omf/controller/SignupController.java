package com.omf.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omf.dto.UserData;
import com.omf.service.impl.SignupServiceImpl;

@RestController
@RequestMapping(path="/signup")
public class SignupController {
	
	@Autowired
	private SignupServiceImpl signupServiceImpl;
	/*
	 * To register a customer
	 */
	@CrossOrigin
	@PostMapping(path = "/customer")
	public ResponseEntity<String> registerCustomer(@Valid @RequestBody UserData userDto) {
		try {
			return signupServiceImpl.registerCustomer(userDto);
		} catch (Exception e) {
			return new ResponseEntity<>("Unable to register the user. Please verify details or try again later!", HttpStatus.BAD_REQUEST);
		}
	}

	/*
	 * To register a vendor
	 */
	@CrossOrigin
	@PostMapping(path = "/vendor")
	public ResponseEntity<String> registerVendor(@RequestBody UserData userDto) {
		try {
			return signupServiceImpl.registerCustomer(userDto);
		} catch (Exception e) {
			return new ResponseEntity<>("Unable to register the user. Please verify details or try again later!", HttpStatus.BAD_REQUEST);
		}
	}	
}
