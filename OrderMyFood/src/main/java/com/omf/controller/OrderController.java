package com.omf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omf.dto.OrderDTO;
import com.omf.entity.Order;
import com.omf.service.OrderService;

@RestController
@RequestMapping(path="/order")
public class OrderController {
	@Autowired
	private OrderService orderService;

	@PostMapping("/placeOrder")
	public Order insert(@RequestBody OrderDTO orderDto)
	{
		return orderService.cart(orderDto);
	}
	
	@GetMapping("/getAllOrders")
	public List<Order> getAll()
	{
		return orderService.getAllOrders();
	}

}
