package com.omf.dto;

public class VendorFoodItemsDTO {

	private Long vendorId;
	
	private Long foodItemId;
	
	private double price;
	
	private Long count;
	
	VendorFoodItemsDTO() {
		
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public Long getFoodItemId() {
		return foodItemId;
	}

	public void setFoodItemId(Long foodItemId) {
		this.foodItemId = foodItemId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
}
