package com.omf.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.omf.dto.ForgotDTO;
import com.omf.dto.ResetPasswordDTO;
import com.omf.service.CustomerService;

@Controller
public class CustomerForgotPasswordController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("customer/forgot_password")
	public ResponseEntity<String> processForgotPassword(HttpServletRequest httpServletRequest, @RequestBody ForgotDTO forgotDto) throws Exception {
		return customerService.processForgotPassword(httpServletRequest, forgotDto);
	}

	@PostMapping("customer/reset_password")
	public ResponseEntity<String> processResetPassword(@RequestParam("token") String token, @RequestBody ResetPasswordDTO resetPasswordDTO) {
		return customerService.processResetPassword(token, resetPasswordDTO);
	}
}