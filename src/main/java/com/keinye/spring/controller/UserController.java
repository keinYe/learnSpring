package com.keinye.spring.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keinye.spring.common.struct.Result;
import com.keinye.spring.entity.User;
import com.keinye.spring.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	UserService userService;
	
	@GetMapping(value = "/all")
	public Result getAll() {
		return userService.getUsers();
	}
	
	@PostMapping(value = "/register", produces = "application/json")
	public Result register(@RequestBody User user) {
		Result result = userService.register(user);
		logger.info(result.toString());
		return result;
	}
}
