package com.keinye.spring.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.keinye.spring.common.MyError;
import com.keinye.spring.common.struct.ErrorCode;
import com.keinye.spring.common.struct.Result;
import com.keinye.spring.common.utils.PasswordUtil;
import com.keinye.spring.entity.User;
import com.keinye.spring.repository.UserRepository;



@Component
@Transactional
public class UserService {
	@Autowired
	UserRepository userRepository;	
	
	
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	public Result register(User user) {
		User exist = userRepository.findByName(user.getName());
		System.out.print(exist);
		if (exist != null) {
			return Result.error(MyError.USER_IS_EXIST);
		}
		user.setPassword(PasswordUtil.encoder(user.getPassword()));
		userRepository.save(user);
		return Result.ok();
	}
}
