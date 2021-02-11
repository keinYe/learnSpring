package com.keinye.spring.entity;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class AbstractEntity {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Integer id;
	
	@Column(nullable = false, updatable = false)
	private Long createdAt;
	
	
	public Integer getId() {
		return id;
	}
	
	public Long getCreatedAt() {
		return createdAt;
	}
	
	@Transient
    public ZonedDateTime getCreatedDateTime() {
        return Instant.ofEpochMilli(this.createdAt).atZone(ZoneId.systemDefault());
    }	
	
	private void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}
	
	@PrePersist
    public void preInsert() {
        setCreatedAt(System.currentTimeMillis());
    }	

}
