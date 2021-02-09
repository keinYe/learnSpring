package com.keinye.spring.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
}
