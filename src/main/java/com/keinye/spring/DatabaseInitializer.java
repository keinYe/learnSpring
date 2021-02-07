package com.keinye.spring;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	public void init() {
		
	}
}
