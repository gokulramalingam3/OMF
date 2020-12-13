package com.omf.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.omf.entity.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

	Vendor findByEmailIdIgnoreCase(String emailId);
	
}
