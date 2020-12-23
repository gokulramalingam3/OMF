package com.omf.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.omf.dto.ForgotDTO;
import com.omf.dto.LoginDto;
import com.omf.dto.OTP;
import com.omf.dto.ResetPasswordDTO;
import com.omf.dto.UserData;
import com.omf.entity.Customer;
import com.omf.exception.UserNotFoundException;

public interface CustomerService {
	ResponseEntity<String> registerUser(UserData customerDto) throws Exception;

	Customer loginCustomer(LoginDto Dto) throws Exception;

	ResponseEntity<String> verifyOtp(OTP otp);

	public Customer getCustomerById(Long customerId);

	Customer editCustomerById(Long customerId, Customer customer) throws Exception;

	ResponseEntity<String> processForgotPassword(HttpServletRequest httpServletRequest, ForgotDTO forgotDto)
			throws UserNotFoundException;

	ResponseEntity<String> processResetPassword(String token, ResetPasswordDTO resetPasswordDTO);
}
