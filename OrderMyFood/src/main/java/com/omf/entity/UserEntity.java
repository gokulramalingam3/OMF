package com.omf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "customer")
public class UserEntity {

	@Id
	@Column(name = "customer_id", length = 30)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long customerId;

	@Size(min=2, message="Name should have atleast 2 characters")
	@Column(name = "first_name", length = 30)
	private String firstName;

	@Column(name = "last_name", length = 30)
	private String lastName;

	@Column(name = "email_id", length = 50, unique = true)
	private String emailId;
	
	@Column(name = "password", length = 50)
	private String password;
	
	@Column(name = "role", length = 10)
	private String role;

	public UserEntity() {
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

	
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}


}
