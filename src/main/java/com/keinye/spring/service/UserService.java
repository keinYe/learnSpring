package com.keinye.spring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.keinye.spring.common.MyError;
import com.keinye.spring.common.struct.Result;
import com.keinye.spring.common.utils.PasswordUtil;
import com.keinye.spring.entity.User;
import com.keinye.spring.repository.UserRepository;



@Component
@Transactional
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	UserRepository userRepository;	
	
	
	public Result getUsers() {
		return Result.ok(userRepository.findAll());
	}
	
	public Result register(User user) {
		User exist = userRepository.findByName(user.getName());
		
		if (exist != null) {
			logger.info(exist.toString());
			return Result.error(MyError.USER_IS_EXIST);
		}
		user.setPassword(PasswordUtil.encoder(user.getPassword()));
		userRepository.save(user);
		return Result.ok();
	}
}
