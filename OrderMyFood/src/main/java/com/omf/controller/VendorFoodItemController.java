package com.omf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omf.dto.VendorFoodItemsDTO;
import com.omf.entity.VendorFoodItems;
import com.omf.service.VendorFoodItemsService;

@RestController
@RequestMapping(path = "/vendorFood")
public class VendorFoodItemController {

	
	@Autowired
	private VendorFoodItemsService vendorFoodItemsService;
	
	@PostMapping(path = "/addFoodItem")
	public VendorFoodItems addVendorFoodItem(@RequestBody VendorFoodItemsDTO vendorFoodItem) throws Exception {
		return vendorFoodItemsService.addVendorFoodItem(vendorFoodItem);
	}
}
