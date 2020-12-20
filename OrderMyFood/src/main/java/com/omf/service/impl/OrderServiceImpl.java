package com.omf.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omf.dto.OrderDTO;
import com.omf.entity.Customer;
import com.omf.entity.Order;
import com.omf.entity.Vendor;
import com.omf.entity.VendorFoodItems;
import com.omf.repository.CustomerRepository;
import com.omf.repository.OrderRepository;
import com.omf.repository.VendorFoodItemsRepository;
import com.omf.repository.VendorRepository;
import com.omf.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private VendorRepository vendorRepository;

	@Autowired
	private VendorFoodItemsRepository vendorFoodItemsRepository;

	public Order cart(OrderDTO orderDto)

	{
		Order order = new Order();

		Customer customer = null;

		Vendor vendor = null;

		List<Integer> idList = orderDto.getFoodItemIds();

		Optional optional;

		optional = customerRepository.findById(orderDto.getCustomerId());

		List<VendorFoodItems> orderedItems;

		if (optional.isPresent()) {
			customer = (Customer) optional.get();
		}

		optional = vendorRepository.findById(orderDto.getVendorId());

		if (optional.isPresent()) {
			vendor = (Vendor) optional.get();
		}

		orderedItems = (List<VendorFoodItems>) vendorFoodItemsRepository.findAllById(idList);

		order.setCustomer(customer);
		order.setVendor(vendor);
		order.setVendorFoodItems(orderedItems);
		order.setTotalBill(orderDto.getTotalBill());
		Order savedOrder = orderRepository.save(order);
		return savedOrder;
	}

	@Override
	public List<Order> getCustomerOrderDetailsById(Long customerId) {
		Customer customer = new Customer();
		customer.setCustomerId(customerId);
		return orderRepository.findByCustomer(customer);
	}

	public List<Order> getAllOrders() {
		return (List<Order>) orderRepository.findAll();

	}

}
