package com.omf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omf.dto.VendorFoodItemsDTO;
import com.omf.entity.FoodItems;
import com.omf.entity.Vendor;
import com.omf.entity.VendorFoodItems;
import com.omf.repository.FoodItemsRepository;
import com.omf.repository.VendorFoodItemsRepository;
import com.omf.repository.VendorRepository;
import com.omf.service.VendorFoodItemsService;

@Service
public class VendorFoodItemsServiceImpl implements VendorFoodItemsService{

	@Autowired
	VendorFoodItemsRepository vendorFoodItemsRepository;
	
	@Autowired
	VendorRepository vendorRepository;
	
	@Autowired
	FoodItemsRepository foodItemsRepository;
	
	@Override
	public VendorFoodItems addVendorFoodItem(VendorFoodItemsDTO vendorFoodItem) {
		VendorFoodItems foodItemObj = new VendorFoodItems();

		foodItemObj.setVendor(vendorRepository.findById(vendorFoodItem.getVendorId()).orElse(new Vendor()));
		foodItemObj.setFoodItems(foodItemsRepository.findById(vendorFoodItem.getFoodItemId()).orElse(new FoodItems()));

		foodItemObj.setPrice(vendorFoodItem.getPrice());
		foodItemObj.setCount(vendorFoodItem.getCount());
		return vendorFoodItemsRepository.save(foodItemObj);
	}
}
