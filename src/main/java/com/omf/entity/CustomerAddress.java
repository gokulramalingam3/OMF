package com.omf.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "customer_address")
public class CustomerAddress {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	@OneToOne
	@JoinColumn(name = "customer_id")
	@JsonBackReference
	private Customer customer;
	
	@Column(name = "street", length = 20)
	private String street;

	@Column(name = "city", length = 30)
	private String city;

	@Column(name = "pincode", length = 6)
	private String pincode;

	@Column(name = "state", length = 15)
	private String state;

	@Column(name = "phone_number", unique = true, length = 10)
	private String phoneNumber;
	
	@Column(name = "landline_number", length = 15)
	private String landlineNumber;
	
	@Column(name = "nearest_location", length = 15)
	private String nearestLocation;
	
	@Column(name = "dist_from_nearest_loc", length = 10)
	private String distanceFromNearestLocation;
	
	public CustomerAddress() {
		/*
		 * Empty constructor for Hibernate to instantiate object
		 */
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getLandlineNumber() {
		return landlineNumber;
	}

	public void setLandlineNumber(String landlineNumber) {
		this.landlineNumber = landlineNumber;
	}

	public String getNearestLocation() {
		return nearestLocation;
	}

	public void setNearestLocation(String nearestLocation) {
		this.nearestLocation = nearestLocation;
	}

	public String getDistanceFromNearestLocation() {
		return distanceFromNearestLocation;
	}

	public void setDistanceFromNearestLocation(String distanceFromNearestLocation) {
		this.distanceFromNearestLocation = distanceFromNearestLocation;
	}
	
}
