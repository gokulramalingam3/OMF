package com.omf.dto;

import java.util.List;

public class OrderDTO {
	private double totalBill;
	private Long customerId;
	private Long vendorId;
	private List<Integer> foodItemIds;
	
	public double getTotalBill() {
		return totalBill;
	}
	public void setTotalBill(double totalBill) {
		this.totalBill = totalBill;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getVendorId() {
		return vendorId;
	}
	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}
	public List<Integer> getFoodItemIds() {
		return foodItemIds;
	}
	public void setFoodItemIds(List<Integer> foodItemIds) {
		this.foodItemIds = foodItemIds;
	}
	
}