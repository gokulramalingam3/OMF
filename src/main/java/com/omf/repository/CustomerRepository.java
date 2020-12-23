package com.omf.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.omf.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Customer findByEmailIdIgnoreCase(String emailId);
	
	public Customer findByResetPasswordToken(String token);
}
