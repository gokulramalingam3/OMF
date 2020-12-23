package com.omf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omf.entity.City;

public interface CityRepository extends JpaRepository<City, Integer> {
}
