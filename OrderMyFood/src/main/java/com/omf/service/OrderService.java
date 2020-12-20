package com.omf.service;

import java.util.List;

import com.omf.dto.OrderDTO;
import com.omf.entity.Order;

public interface OrderService {


	Order cart(OrderDTO order);

	List<Order> getCustomerOrderDetailsById(Long customerId);

	List<Order> getAllOrders();

}
