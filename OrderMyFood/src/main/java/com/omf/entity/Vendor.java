package com.omf.entity;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "vendor")
public class Vendor {

	@Id
	@Column(name = "vendor_id", length = 30)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long vendorId;

	@Size(min=2, message="Name should have atleast 2 characters")
	@Column(name = "first_name", length = 30)
	private String firstName;

	@Column(name = "last_name", length = 30)
	private String lastName;

	@Column(name = "email_id", length = 50, unique = true)
	private String emailId;
	
	@Column(name = "password", length = 100)
	private String password;

	private static final long OTP_VALID_DURATION = 5 * 60 * 1000;// 5 minutes
	
	@Column(name = "one_time_password")
	private String oneTimePassword;

	@Column(name = "otp_requested_time")
	private Date otpRequestedTime;

	@Column(name="status")
	private String status;
	
	@Column(name = "reset_password_token")
    private String resetPasswordToken;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "vendor")
	@JsonManagedReference
	private VendorAddress vendorAddress;
	
	public Vendor() {
		/*
		 * Empty constructor for Hibernate to instantiate object
		 */
	}

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOneTimePassword() {
		return oneTimePassword;
	}

	public void setOneTimePassword(String oneTimePassword) {
		this.oneTimePassword = oneTimePassword;
	}

	public Date getOtpRequestedTime() {
		return otpRequestedTime;
	}

	public void setOtpRequestedTime(Date otpRequestedTime) {
		this.otpRequestedTime = otpRequestedTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isOTPRequired() {
		if (this.getOneTimePassword() == null) {
			return false;
		}
		long currentTimeInMillis = System.currentTimeMillis();
		long otpRequestedTimeInMillis = this.otpRequestedTime.getTime();
		if (otpRequestedTimeInMillis + OTP_VALID_DURATION < currentTimeInMillis) {
			//OTP expires
			return false;
		}
		return true;
	}

	public VendorAddress getVendorAddress() {
		return vendorAddress;
	}

	public void setVendorAddress(VendorAddress vendorAddress) {
		this.vendorAddress = vendorAddress;
	}

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}
}
