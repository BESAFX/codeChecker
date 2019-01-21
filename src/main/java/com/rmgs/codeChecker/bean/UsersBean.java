package com.rmgs.codeChecker.bean;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.rmgs.codeChecker.api.ApiClient;
import com.rmgs.codeChecker.dto.RoleDto;
import com.rmgs.codeChecker.dto.UserDetailsDto;
import com.rmgs.codeChecker.dto.UserDto;
/**
 * 
 * @author rehab.sayed
 *
 */
@ManagedBean
@RequestScoped
//@ViewScoped
@Component
@Scope(value = "request")
public class UsersBean implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 732232090039558309L;
	public static final Logger logger = LoggerFactory.getLogger(UsersBean.class);
	private List<RoleDto> roles;
	private List<UserDto> users;
	

	private UserDto user =new UserDto();
	private List<UserDto> filteredUsers;
	FacesContext facesContext = FacesContext.getCurrentInstance();
	HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
	private UserDto deletedUser;
	@Autowired
	ApiClient apiClient;

	
	
	
	public List<RoleDto> getRoles() {
		return roles;
	}
	public List<UserDto> getUsers() {
		return users;
	}
	public void setUsers(List<UserDto> users) {
		this.users = users;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public List<UserDto> getFilteredUsers() {
		return filteredUsers;
	}
	public void setFilteredUsers(List<UserDto> filteredUsers) {
		this.filteredUsers = filteredUsers;
	}
	public UserDto getDeletedUser() {
		return deletedUser;
	}
	public void setDeletedUser(UserDto deletedUser) {
		this.deletedUser = deletedUser;
	}
	public void setRoles(List<RoleDto> roles) {
		this.roles = roles;
	}
	
	
	@PostConstruct
	public void init() {
		
		try{
			
				roles = apiClient.getRoles();
				users = apiClient.getUsers();
				filteredUsers = users;
				
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	public final void handleEvent(final AjaxBehaviorEvent event) {    
		FacesContext context = FacesContext.getCurrentInstance(); 
		this.user = context.getApplication().evaluateExpressionGet(context, "#{user}", UserDto.class);  		
	}
	
	public String deleteAction(UserDto user) throws IOException {
		session.setAttribute("deletedUser_object", user);
		deletedUser = user;
		return null;
	}
	
	public String confirmDelete() throws IOException
	{
		deletedUser =(UserDto) session.getAttribute("deletedUser_object");
		users.remove(deletedUser);
		if(deletedUser.getId()!=0)
			apiClient.deleteUser(deletedUser.getId());
		reload();
		return null;
	}
	
	private void reload() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}
	

	public String initEditModal(UserDto user) throws IOException {
		session.setAttribute("editModal", true);
		this.user=user;
		
		if(user.getRoles()!=null){
			ArrayList<String> rls=new ArrayList<>();
			for(RoleDto ro:user.getRoles()){
				rls.add(ro.getId()+"");
			}
			user.setSelectedRoles(rls);
		}
		session.setAttribute("originalEditedUserObject", user);
		return null;
	}
	
	public String proccessModalAction() throws IOException{
		boolean editAction = (boolean) session.getAttribute("editModal");
		if(editAction)
		{
			UserDto originalEditedUser = (UserDto) session.getAttribute("originalEditedUserObject");
			

			originalEditedUser.setUsername(user.getUsername());
			UserDetailsDto userDetails=originalEditedUser.getUserDatils();
			
			userDetails.setEmail(user.getUserDatils().getEmail());
			userDetails.setFirstName(user.getUserDatils().getFirstName());
			userDetails.setLastName(user.getUserDatils().getLastName());
			userDetails.setMobile(user.getUserDatils().getMobile());
			userDetails.setHourlyRate(user.getUserDatils().getHourlyRate());
			originalEditedUser.setUserDatils(userDetails);
			
			originalEditedUser.setRoles(createRolesOjbect(user));
			
			boolean successfulyAdded = apiClient.updateUser(originalEditedUser);

		}else {
			
			UserDto addedUser = null;
			String p=new BCryptPasswordEncoder().encode(user.getPassword());
			user.setPassword(p);
			
			user.setRoles(createRolesOjbect(user));
			addedUser=apiClient.addUser(user);
			users.add(addedUser);
			reload();
		}
		reload();
		return null;
	}

	private ArrayList<RoleDto>  createRolesOjbect(UserDto userDto){
		ArrayList<RoleDto> rolesOnUser = new ArrayList<RoleDto>();
		
		ArrayList<String> usersList = (ArrayList)userDto.getSelectedRoles();
		for (String roleId : usersList) {
			for(RoleDto rl:roles){
				if(rl.getId().longValue()==Long.parseLong(roleId)){
					rolesOnUser.add(rl);
				}
			}
			
			
		}


		return rolesOnUser;
	}

	
	
	
	public void openModalForNewUser()
	{
		session.setAttribute("editModal", false);
		user=new UserDto();
		
	}
	
	
	
	
	public void customValidators(ComponentSystemEvent event) throws ParseException  {
		UIComponent components = event.getComponent();
		UIInput uiInputPassword = (UIInput) components.findComponent("password");
		String password = uiInputPassword.getLocalValue()==null? "" : uiInputPassword.getLocalValue().toString();
		
		
		UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmPassword");
		String confirmPassword = uiInputConfirmPassword.getLocalValue()==null? "" : uiInputConfirmPassword.getLocalValue().toString();
		String uiConfirmPasswordId = uiInputConfirmPassword.getClientId();
		
		
		
		if(confirmPassword!=null && password!=null && !password.isEmpty()&& !confirmPassword.isEmpty() && !password.equals(confirmPassword))
		{ 
			FacesMessage msg = new FacesMessage("Passwords are not equal!!");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			facesContext.addMessage(uiConfirmPasswordId, msg);
			facesContext.renderResponse();	
			
		}
		
	}
}
