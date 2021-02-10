package com.keinye.spring.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.keinye.spring.entity.User;


@SpringBootTest
class UserRepositoryTest {
	@Autowired
	UserRepository userRepository;

	@BeforeEach
	void setUp() throws Exception {
		User user = new User();
		user.setName("test");
		user.setPassword("123");
		userRepository.save(user);
	}

	@AfterEach
	void tearDown() throws Exception {
		userRepository.deleteAll();;
	}

	@Test
	void testFind() {
		Optional<User> userOptional = userRepository.findById(1);
		assertTrue(userOptional.isPresent());
		User user = userOptional.get();
		assertNotNull(user);
		assertEquals(user.getName(), "test");
		assertEquals(user.getId(), 1);
	}

}
