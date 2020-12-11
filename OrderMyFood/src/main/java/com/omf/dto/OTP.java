package com.omf.dto;

public class OTP {
	
	private String emailId;
	
	private String otp;

	public OTP() {
		/*
		 * Empty constructor
		 */
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}
	
}
