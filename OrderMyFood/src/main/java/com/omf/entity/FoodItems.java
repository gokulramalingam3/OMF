package com.omf.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "food_items")
public class FoodItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", length = 20)
	private String name;

	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "category", length = 10)
	private String category;

	@JsonBackReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "foodItems")

	private List<VendorFoodItems> vendorFoodItems;

	public FoodItems() {
		/*
		 * Empty constructor for Hibernate to instantiate object
		 */
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<VendorFoodItems> getVendorFoodItems() {
		return vendorFoodItems;
	}

	public void setVendorFoodItems(List<VendorFoodItems> vendorFoodItems) {
		this.vendorFoodItems = vendorFoodItems;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
