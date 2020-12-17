package com.omf.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omf.entity.City;
import com.omf.entity.FoodItems;
import com.omf.entity.Vendor;
import com.omf.entity.VendorFoodItems;
import com.omf.repository.CityRepository;
import com.omf.repository.FoodItemsRepository;
import com.omf.repository.VendorAddressRepository;
import com.omf.repository.VendorFoodItemsRepository;
import com.omf.repository.VendorRepository;
import com.omf.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	CityRepository cityRepository;
	
	@Autowired
	VendorRepository vendorRepository;
	@Autowired
	VendorAddressRepository vendorAddressRepository;
	@Autowired
	VendorFoodItemsRepository vendorfoodrepository;

	@Autowired
	FoodItemsRepository foodItemsRepository;

	@Override
	public List<City> citynames() {

		return cityRepository.findAll();
	}

	@Override
	public List<Vendor> getRestaurantListByItemAndCity(String keyword, String city) {

		FoodItems food = foodItemsRepository.findByName(keyword);
		List<Vendor> vends = vendorRepository.findByVendorAddressCity(city);
		try {
			List<VendorFoodItems> vendorsFood = food.getVendorFoodItems();

			List<Vendor> vendors = new ArrayList<Vendor>();

			for (VendorFoodItems v : vendorsFood) {

				vendors.add(v.getVendor());
			}

			if (vendors != null) {
				vends.retainAll(vendors);
			}

		} catch (Exception e) {

		}
		return vends;
	}

	@Override
	public List<VendorFoodItems> items(String vendorid) {
		return vendorfoodrepository.findByVendorFoodItems(vendorid);
	}

}
