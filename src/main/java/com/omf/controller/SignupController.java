package com.omf.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omf.dto.OTP;
import com.omf.dto.UserData;
import com.omf.service.CustomerService;
import com.omf.service.VendorService;

@RestController
@RequestMapping(path="/signup")
public class SignupController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private VendorService vendorService;
	
	/*
	 * To register a customer
	 */
	@PostMapping(path = "/customer")
	public ResponseEntity<String> registerCustomer(@Valid @RequestBody UserData userDto) {
		try {
			return customerService.registerUser(userDto);
		} catch (Exception e) {
			return new ResponseEntity<>("Unable to register the customer. Please verify details or try again later!", HttpStatus.BAD_REQUEST);
		}
	}

	/*
	 * To register a vendor
	 */
	@PostMapping(path = "/vendor")
	public ResponseEntity<String> registerVendor(@RequestBody UserData userDto) {
		try {
			return vendorService.registerUser(userDto);
		} catch (Exception e) {
			return new ResponseEntity<>("Unable to register the vendor. Please verify details or try again later!", HttpStatus.BAD_REQUEST);
		}
	}
	
	/*
	 *To verify customer 
	 */
	@PostMapping(path = "customer/verify")
	public ResponseEntity<String> verifyCustomer(@RequestBody OTP otp) {
		return customerService.verifyOtp(otp);
	}
	
	/*
	 *To verify vendor 
	 */
	@PostMapping(path = "vendor/verify")
	public ResponseEntity<String> verifyVendor(@RequestBody OTP otp) {
		return vendorService.verifyOtp(otp);
	}
}
