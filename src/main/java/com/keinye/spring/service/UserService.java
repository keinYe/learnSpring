package com.keinye.spring.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.keinye.spring.entity.User;



@Component
@Transactional
public class UserService {
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	public List<User> getUsers() {
		return List.of();
	}
}
