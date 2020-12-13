package com.omf.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omf.dto.LoginDto;
import com.omf.dto.UserData;
import com.omf.entity.Customer;
import com.omf.service.CustomerService;

@RestController
@RequestMapping(path="/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@PostMapping(path = "/login")
	public Customer loginUser(@RequestBody LoginDto dto) throws Exception {
		return customerService.loginCustomer(dto);
	}
	
	@GetMapping(path="/{customerId}")
	public Customer getCustomerDetailsById(@PathVariable Long customerId){
		return customerService.getCustomerById(customerId);
	}
}
