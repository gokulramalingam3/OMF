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
import com.omf.service.VendorService;

@Controller
public class VendorForgotPasswordController {

	@Autowired
	private VendorService vendorService;

	@PostMapping("vendor/forgot_password")
	public ResponseEntity<String> processForgotPassword(HttpServletRequest httpServletRequest, @RequestBody ForgotDTO forgotDto) throws Exception {
		return vendorService.processForgotPassword(httpServletRequest, forgotDto);
	}

	@PostMapping("vendor/reset_password")
	public ResponseEntity<String> processResetPassword(@RequestParam("token") String token, @RequestBody ResetPasswordDTO resetPasswordDTO) {
		return vendorService.processResetPassword(token, resetPasswordDTO);
	}
}