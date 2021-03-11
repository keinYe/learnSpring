package com.keinye.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keinye.spring.common.struct.Result;
import com.keinye.spring.entity.User;
import com.keinye.spring.service.UserService;

import netscape.javascript.JSObject;


@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;
	
	@GetMapping(value = "/all")
	public List<User> getAll() {
		return userService.getUsers();
	}
	
	@PostMapping(value = "/register", produces = "application/json")
	public Result register(@RequestBody User user) {
		return userService.register(user);
	}
}
