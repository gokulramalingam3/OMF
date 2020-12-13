package com.omf.service;

import com.omf.dto.LoginDto;
import com.omf.entity.UserEntity;

public interface LoginService {

	UserEntity loginUser(LoginDto Dto) throws Exception;

}