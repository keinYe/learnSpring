package com.keinye.spring.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="users")
public class User extends AbstractEntity{
	
	@Column(nullable = false, unique = false, length = 100)
	private String name;
	@Column(nullable = false, length = 100)
	private String password;


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

	
	@Override
	public String toString() {
		return String.format("User[id=%s, name=%s, createdAt=%s, createdAtDataTime=%s]", 
				getId(), getName(), getCreatedAt(), getCreatedDateTime());
	}
	
	@Override
	public int hashCode() {
		
	}
}
