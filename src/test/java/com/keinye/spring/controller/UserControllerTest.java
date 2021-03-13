package com.keinye.spring.controller;


import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;
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
import com.keinye.spring.common.MyError;
import com.keinye.spring.common.struct.Result;
import com.keinye.spring.common.utils.JsonUtil;
import com.keinye.spring.entity.User;
import com.keinye.spring.repository.UserRepository;
import com.keinye.spring.service.UserService;

import junit.framework.Assert;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
class UserControllerTest extends BaseTest {
	private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	
	UserService mockuser = Mockito.mock(UserService.class);

	@Test
	void testGetAll() throws Exception {
		
		List<User> users = new ArrayList<>();
		users = userRepository.findAll();
		Mockito.when(mockuser.getUsers()).thenReturn(Result.ok(users));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/all");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		Result<List<User>> dataResult = JsonUtil.fromJson(result.getResponse().getContentAsString(), Result.class);
		assertFalse(dataResult == null);
		assertTrue(dataResult.isSuccess());
		assertEquals(0, dataResult.getErrorCode());
		assertTrue(dataResult.getData() instanceof List<?>);
	}

	@Test
	void testRegisterA() throws Exception {
		String JSON = "{\"name\":\"register\",\"password\":\"register\"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/register")
				.accept(MediaType.APPLICATION_JSON).content(JSON).characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		Result dataResult = JsonUtil.fromJson(result.getResponse().getContentAsString(), Result.class);
		assertFalse(dataResult == null);
		assertTrue(dataResult.isSuccess());
		assertEquals(0, dataResult.getErrorCode());
		assertTrue(dataResult.getData() == null);
	}
	
	@Test
	void testRegisterB() throws Exception {
		String JSON = "{\"name\":\"register\",\"password\":\"register\"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/register")
				.content(JSON)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		Result dataResult = JsonUtil.fromJson(result.getResponse().getContentAsString(), Result.class);
		assertFalse(dataResult == null);
		assertTrue(dataResult.isSuccess());
		assertEquals(0, dataResult.getErrorCode());
		assertTrue(dataResult.getData() == null);
	}
}
