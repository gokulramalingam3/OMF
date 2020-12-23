package com.omf.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omf.entity.City;
import com.omf.entity.Vendor;
import com.omf.service.FoodItemsService;
import com.omf.service.SearchService;

@RestController
@RequestMapping(path = "/search")
public class SearchController {
	@Autowired
	SearchService SearchServices;
	@Autowired
	FoodItemsService fooditemservice;

	// List Of different items provided by the restaurant
	@GetMapping(path = "/items")
	public List<String> searchItems() {
		return fooditemservice.itemsList();

	}

	// List of different cities
	@GetMapping(path = "/cities")
	public List<City> cities() {
		return SearchServices.citynames();

	}

	// List of restaurant based on city and item name
	@GetMapping(path = "/items/{selectedItem}/{selectedCity}")
	public List<Vendor> restaurantList(@PathVariable String selectedItem, @PathVariable String selectedCity) {
		List<Vendor> vendors = new ArrayList<Vendor>();
		vendors = SearchServices.getRestaurantListByItemAndCity(selectedItem, selectedCity);
		return vendors;
	}

}
