package com.omf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.omf.entity.FoodItems;

public interface FoodItemsRepository extends CrudRepository<FoodItems, Long> {

	public FoodItems findByName(String name);

	@Query(value = "SELECT distinct name FROM food_items", nativeQuery = true)
	public List<String> findNames();

}
