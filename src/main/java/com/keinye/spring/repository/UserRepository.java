package com.keinye.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.keinye.spring.common.utils.PasswordUtil;
import com.keinye.spring.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	User findByName(String name);
}
