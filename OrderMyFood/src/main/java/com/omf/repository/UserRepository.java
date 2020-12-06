package com.omf.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.omf.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByEmailIdIgnoreCase(String emailId);
	
}
