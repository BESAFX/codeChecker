package com.rmgs.codeChecker.dto;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author rehab.sayed
 *
 */
public class UserDto  {


	private Long id;
	private String username;
	private String password;
	private String confirmPassword;
	
	private UserDto parent;
	private boolean active=true;
	private UserDetailsDto userDatils =  new UserDetailsDto();
    private ArrayList<RoleDto> roles;
    private List<String> selectedRoles;
	public Long getId() {
		return id;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public void setId(Long id) {
		this.id = id;
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
	public UserDto getParent() {
		return parent;
	}
	public void setParent(UserDto parent) {
		this.parent = parent;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public UserDetailsDto getUserDatils() {
		return userDatils;
	}
	public void setUserDatils(UserDetailsDto userDatils) {
		this.userDatils = userDatils;
	}
	public ArrayList<RoleDto> getRoles() {
		return roles;
	}
	public void setRoles(ArrayList<RoleDto> roles) {
		this.roles = roles;
	}
	public List<String> getSelectedRoles() {
		return selectedRoles;
	}
	public void setSelectedRoles(List<String> selectedRoles) {
		this.selectedRoles = selectedRoles;
	}
    

}
