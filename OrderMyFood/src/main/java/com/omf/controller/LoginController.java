package com.omf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omf.dto.LoginDto;
import com.omf.entity.UserEntity;
import com.omf.service.LoginService;

@RestController
@RequestMapping(path = "/user")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@PostMapping(path = "/login")
	public UserEntity loginUser(@RequestBody LoginDto dto) throws Exception {
		return loginService.loginUser(dto);
	}
}
