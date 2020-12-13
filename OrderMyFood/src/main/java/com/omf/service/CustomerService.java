package com.omf.service;
import org.springframework.http.ResponseEntity;

import com.omf.dto.LoginDto;
import com.omf.dto.OTP;
import com.omf.dto.UserData;
import com.omf.entity.Customer;

public interface CustomerService 
{
	ResponseEntity<String> registerUser(UserData customerDto) throws Exception;
	
	Customer loginCustomer(LoginDto Dto) throws Exception;

	ResponseEntity<String> verifyOtp(OTP otp);
	
	public Customer getCustomerById(Long customerId);


}

	
