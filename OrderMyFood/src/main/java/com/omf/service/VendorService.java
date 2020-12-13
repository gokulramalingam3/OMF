package com.omf.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.omf.dto.LoginDto;
import com.omf.dto.OTP;
import com.omf.dto.UserData;
import com.omf.entity.Vendor;

public interface VendorService {

	ResponseEntity<String> registerUser(UserData vendorDto) throws Exception;

	ResponseEntity<String> verifyOtp(OTP otp);
	
	public Vendor loginVendor(LoginDto dto) throws Exception;

	public List<Vendor> getVendor();

	public Vendor getVendorById(Long vendorId);

}
