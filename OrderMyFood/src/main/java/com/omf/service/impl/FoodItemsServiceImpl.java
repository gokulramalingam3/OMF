package com.omf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omf.repository.FoodItemsRepository;
import com.omf.service.FoodItemsService;

@Service
public class FoodItemsServiceImpl implements FoodItemsService {

	@Autowired
	private FoodItemsRepository foodItemsRepository;

	@Override
	public List<String> itemsList() {
		return foodItemsRepository.findNames();
	}

}
