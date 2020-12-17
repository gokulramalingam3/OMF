package com.omf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.omf.entity.VendorFoodItems;

public interface VendorFoodItemsRepository extends CrudRepository<VendorFoodItems, Integer> {

	@Query(value = "SELECT * FROM vendor_food_items  inner join food_items  on vendor_food_items.food_items_id = food_items.id where vendor_food_items.vendor_id =?", nativeQuery = true)
	List<VendorFoodItems> findByVendorFoodItems(String vendorid);

}
