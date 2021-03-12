package com.keinye.spring.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.keinye.spring.BaseTest;
import com.keinye.spring.entity.User;
import com.keinye.spring.service.UserService;

import junit.framework.Assert;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest extends BaseTest {
	private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserService userService;

	@Test
	void testGetAll() {
		
		List<User> users = new ArrayList<>();
		users = userService.getUsers();
		logger.info(users.toString());
		Mockito.when(userService.getUsers()).thenReturn(users);
		users.forEach(user -> logger.info(user.toString()));
	}

	@Test
	void testRegister() throws Exception {
		String JSON = "{\"name\":\"register\",\"password\":\"register\"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/register")
				.accept(MediaType.APPLICATION_JSON).content(JSON)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

}
