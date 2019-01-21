package com.rmgs.codeChecker.dto;

import java.io.Serializable;

public class Project implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Project() {
	}

	private Long id;

	private String name;
	private String projKey;
	private String path;

	private String username;
	private String password;
	private Boolean processed = false;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getProjKey() {
		return projKey;
	}

	public void setProjKey(String projKey) {
		this.projKey = projKey;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

	
}