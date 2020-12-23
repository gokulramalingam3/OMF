package com.omf.service;

import java.util.List;

import com.omf.entity.City;
import com.omf.entity.Vendor;
import com.omf.entity.VendorFoodItems;

public interface SearchService {

	List<City> citynames();

	List<Vendor> getRestaurantListByItemAndCity(String keyword, String city);
	
	List<VendorFoodItems> items(String vendorid);
}
