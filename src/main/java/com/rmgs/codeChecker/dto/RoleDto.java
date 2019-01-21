package com.rmgs.codeChecker.dto;

import java.util.ArrayList;
import java.util.List;


public class RoleDto {
	private Long id;
	private String name;
	private String description;
	private ArrayList<PrivilegeDto> privileges;
	private List<String> privs;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<PrivilegeDto> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(ArrayList<PrivilegeDto> privileges) {
		this.privileges = privileges;
	}
	public List<String> getPrivs() {
		return privs;
	}
	public void setPrivs(List<String> privs) {
		this.privs = privs;
	}
	
	

}
