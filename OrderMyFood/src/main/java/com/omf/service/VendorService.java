package com.omf.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.omf.dto.ForgotDTO;
import com.omf.dto.LoginDto;
import com.omf.dto.OTP;
import com.omf.dto.ResetPasswordDTO;
import com.omf.dto.UserData;
import com.omf.entity.Vendor;
import com.omf.exception.UserNotFoundException;

public interface VendorService {

	ResponseEntity<String> registerUser(UserData vendorDto) throws Exception;

	ResponseEntity<String> verifyOtp(OTP otp);
	
	public Vendor loginVendor(LoginDto dto) throws Exception;

	public List<Vendor> getVendor();

	public Vendor getVendorById(Long vendorId);

	Vendor editVendorById(Long vendorId, Vendor vendor) throws Exception;

	ResponseEntity<String> processForgotPassword(HttpServletRequest httpServletRequest, ForgotDTO forgotDto) throws UserNotFoundException;

	ResponseEntity<String> processResetPassword(String token, ResetPasswordDTO resetPasswordDTO);

}
