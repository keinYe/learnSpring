package com.keinye.spring.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.keinye.spring.common.utils.PasswordUtil;


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
		this.password = PasswordUtil.encoder(password);
	}

	
	@Override
	public String toString() {
		return String.format("User[id=%s, name=%s, createdAt=%s, createdAtDataTime=%s]", 
				getId(), getName(), getCreatedAt(), getCreatedDateTime());
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
	        User p = (User) obj;
	        boolean nameEquals = false;
	        if (this.name == null && p.name == null) {
	            nameEquals = true;
	        }
	        if (this.name != null) {
	            nameEquals = this.name.equals(p.name);
	        }
	        return nameEquals;
		}
		return false;
	}
}
