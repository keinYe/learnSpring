package com.keinye.spring.entity;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.event.spi.PreInsertEvent;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Integer id;
	
	@Column(nullable = false, unique = false, length = 100)
	private String name;
	@Column(nullable = false, length = 100)
	private String password;
	@Column(nullable = false, updatable = false)
	private Long createdAt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}
	
	@Transient
	public String getCreatedDateTime() {
		return Instant.ofEpochMilli(this.createdAt).atZone(ZoneId.systemDefault())
				.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}
	
	@PrePersist
	public void preInsert() {
		setCreatedAt(System.currentTimeMillis());
	}
	
	@Override
	public String toString() {
		return String.format("User[id=%s, name=%s, createdAt=%s, createdAtDataTime=%s]", 
				getId(), getName(), getCreatedAt(), getCreatedDateTime());
	}
}
