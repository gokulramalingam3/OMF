package com.omf.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.omf.dto.OrderDTO;
import com.omf.entity.Customer;
import com.omf.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {

	ArrayList<Order> findByCustomer(Customer customer);

	void save(List<OrderDTO> orderlist);

}
