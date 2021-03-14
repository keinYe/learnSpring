package com.keinye.spring.controller;


import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.databind.Module.SetupContext;
import com.keinye.spring.BaseTest;
import com.keinye.spring.common.MyError;
import com.keinye.spring.common.struct.Result;
import com.keinye.spring.common.struct.ResultException;
import com.keinye.spring.common.utils.JsonUtil;
import com.keinye.spring.common.utils.PasswordUtil;
import com.keinye.spring.entity.User;
import com.keinye.spring.repository.UserRepository;
import com.keinye.spring.service.UserService;

import junit.framework.Assert;


@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
class UserControllerTest extends BaseTest {
	private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

	private MockMvc mockMvcController;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	UserController userController;
	@Autowired
	private MockMvc mockMvcBean;
	
	private User loginUser;
	
	@BeforeEach
	public void setUp() {
		this.mockMvcController = MockMvcBuilders.standaloneSetup(this.userController).build();
		this.loginUser = new User();
		this.loginUser.setName("login");
		this.loginUser.setPassword(PasswordUtil.encoder("login"));
		userRepository.save(this.loginUser);
	}
	
	@Test
	void testGetAll() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/all");
		MvcResult result = mockMvcController.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		Result<List<User>> dataResult = JsonUtil.fromJson(result.getResponse().getContentAsString(), Result.class);
		assertFalse(dataResult == null);
		assertTrue(dataResult.isSuccess());
		assertEquals(0, dataResult.getErrorCode());
		assertTrue(dataResult.getData() instanceof List<?>);
	}

	/**
	 * 用户注册测试
	 * @throws Exception
	 */
	@Test
	void testRegisterA() throws Exception {
		String JSON = "{\"name\":\"register\",\"password\":\"register\"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/register")
				.accept(MediaType.APPLICATION_JSON).content(JSON).characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvcController.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		Result dataResult = JsonUtil.fromJson(response.getContentAsString(), Result.class);
		assertNotNull(dataResult);
		assertTrue(dataResult.isSuccess());
		assertEquals(0, dataResult.getErrorCode());
		assertTrue(dataResult.getData() == null);
	}
	
	/**
	 * 用户注册错误测试：
	 *    新注册用户名已存在
	 * @throws Exception
	 */
	@Test
	void testRegisterB() {
		String JSON = "{\"name\":\"register\",\"password\":\"register\"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/register")
				.content(JSON)
				.contentType(MediaType.APPLICATION_JSON);
		assertThrows(NestedServletException.class, ()->{
			mockMvcController.perform(requestBuilder).andReturn();
		});
	}
	
	@Test
	void testRegisterC() throws Exception {
		String JSON = "{\"name\":\"register\",\"password\":\"register\"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/register")
				.accept(MediaType.APPLICATION_JSON).content(JSON).characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvcBean.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		Result dataResult = JsonUtil.fromJson(response.getContentAsString(), Result.class);
		assertNotNull(dataResult);
		assertFalse(dataResult.isSuccess());
		assertEquals(MyError.USER_IS_EXIST.getErrorCode(), dataResult.getErrorCode());
	}
	
	@Test
	void testLogin() throws Exception {
		String JSON = "{\"name\":\"login\",\"password\":\"login\"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/login")
				.accept(MediaType.APPLICATION_JSON).content(JSON).characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvcController.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		logger.info(response.getContentAsString());
		Result<Map<String, String>> dataResult = JsonUtil.fromJson(response.getContentAsString(), Result.class);
		assertNotNull(dataResult);
		assertTrue(dataResult.isSuccess());
		Map map = dataResult.getData();
		assertTrue(map.get("Token") instanceof String);
	}
}
