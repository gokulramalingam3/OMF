package com.omf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omf.dto.LoginDto;
import com.omf.entity.Vendor;
import com.omf.service.VendorService;

@RestController
@RequestMapping(path = "/vendor")
public class VendorController {
	@Autowired
	private VendorService vendorService;

	@PostMapping(path = "/login")
	public Vendor loginUser(@RequestBody LoginDto dto) throws Exception {
		return vendorService.loginVendor(dto);
	}
	
	@PutMapping(path="/update/{vendorId}")
	public Vendor updateVendor(@PathVariable Long  vendorId, @RequestBody Vendor vendor) throws Exception{
		Vendor updatedVendor = vendorService.editVendorById(vendorId,vendor);
		return updatedVendor;
	}
	
	@GetMapping(path = "vendor/{vendorId}")
	public Vendor getVendorDetailsById(@PathVariable Long vendorId) {
		return vendorService.getVendorById(vendorId);
	}

}
