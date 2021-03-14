package com.keinye.spring.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.keinye.spring.common.MyError;
import com.keinye.spring.common.struct.Result;
import com.keinye.spring.common.struct.ResultException;
import com.keinye.spring.common.utils.KeyUtils;
import com.keinye.spring.common.utils.PasswordUtil;
import com.keinye.spring.common.utils.TokenUtils;
import com.keinye.spring.entity.User;
import com.keinye.spring.properties.KeyProperties;
import com.keinye.spring.repository.UserRepository;



@Component
@Transactional
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	UserRepository userRepository;	
	@Autowired
	KeyProperties keyProperties;
	
	
	public Result getUsers() {
		return Result.ok(userRepository.findAll());
	}
	
	public Result register(User user) {
		User exist = userRepository.findByName(user.getName());
		if (exist != null) {
			logger.info(exist.toString());
			throw ResultException.of(MyError.USER_IS_EXIST);
		}
		user.setPassword(user.getPassword());
		userRepository.save(user);
		return Result.ok();
	}
	
	public Result login(User user) {
		User exist = userRepository.findByName(user.getName());
		if (exist == null) {
			throw ResultException.of(MyError.USER_NOT_EXIST);
		}
		logger.info(exist.toString());
		if (!PasswordUtil.matches(user.getPassword(), exist.getPassword())) {
			throw ResultException.of(MyError.USER_NOT_EXIST);
		}
		Map map = new HashMap<String, String>();
		map.put("Token", TokenUtils.createToken(user, keyProperties.getPrivateKey()));
		return Result.ok(map);
	}
 }
