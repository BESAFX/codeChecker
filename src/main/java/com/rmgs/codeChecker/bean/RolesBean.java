package com.rmgs.codeChecker.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rmgs.codeChecker.api.ApiClient;
import com.rmgs.codeChecker.dto.PrivilegeDto;
import com.rmgs.codeChecker.dto.RoleDto;

/**
 * 
 * @author rehab.sayed
 *
 */
@ManagedBean
@RequestScoped
// @ViewScoped
@Component
@Scope(value = "request")
public class RolesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6184066944412011243L;
	/**
	 * 
	 */
	public static final Logger logger = LoggerFactory.getLogger(RolesBean.class);
	private List<RoleDto> roles;
	private List<PrivilegeDto> privileges;

	private RoleDto role = new RoleDto();
	private List<RoleDto> filteredRoles;
	FacesContext facesContext = FacesContext.getCurrentInstance();
	HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
	private RoleDto deletedRole;
	@Autowired
	ApiClient apiClient;

	public RoleDto getDeletedRole() {
		return deletedRole;
	}

	public List<PrivilegeDto> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<PrivilegeDto> privileges) {
		this.privileges = privileges;
	}

	public void setDeletedRole(RoleDto deletedRole) {
		this.deletedRole = deletedRole;
	}

	public List<RoleDto> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDto> roles) {
		this.roles = roles;
	}

	public List<RoleDto> getFilteredRoles() {
		return filteredRoles;
	}

	public void setFilteredRoles(List<RoleDto> filteredRoles) {
		this.filteredRoles = filteredRoles;
	}

	public RoleDto getRole() {
		return role;
	}

	public void setRole(RoleDto role) {
		this.role = role;
	}

	@PostConstruct
	public void init() {

		try {

			roles = apiClient.getRoles();
			filteredRoles = roles;
			privileges = apiClient.getPrivilages();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public final void handleEvent(final AjaxBehaviorEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		this.role = context.getApplication().evaluateExpressionGet(context, "#{role}", RoleDto.class);
	}

	public String deleteAction(RoleDto role) throws IOException {
		session.setAttribute("deletedRole_object", role);
		deletedRole = role;
		return null;
	}

	public String confirmDelete() throws IOException {
		deletedRole = (RoleDto) session.getAttribute("deletedRole_object");
		roles.remove(deletedRole);
		if (deletedRole.getId() != 0)
			apiClient.deleteRole(deletedRole.getId());
		reload();
		return null;
	}

	private void reload() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}

	public String initEditModal(RoleDto role) throws IOException {
		session.setAttribute("editModal", true);
		this.role = role;

		if (role.getPrivileges() != null) {
			ArrayList<String> privs = new ArrayList<>();
			for (PrivilegeDto priv : role.getPrivileges()) {
				privs.add(priv.getId() + "");
			}
			role.setPrivs(privs);
		}
		session.setAttribute("originalEditedRoleObject", role);
		return null;
	}

	public String proccessModalAction() throws IOException {
		boolean editAction = (boolean) session.getAttribute("editModal");
		if (editAction) {
			RoleDto originalEditedRole = (RoleDto) session.getAttribute("originalEditedRoleObject");

			originalEditedRole.setName(originalEditedRole.getName());
			originalEditedRole.setDescription(role.getDescription());
			originalEditedRole.setPrivileges(createPrivsOjbect(role));
			boolean successfulyAdded = apiClient.updateRole(originalEditedRole);
		} else {

			RoleDto addedRole = null;
			role.setPrivileges(createPrivsOjbect(role));
			addedRole = apiClient.addRole(role);
			roles.add(addedRole);
			reload();
		}
		reload();
		return null;
	}

	private ArrayList<PrivilegeDto> createPrivsOjbect(RoleDto roleDto) {
		ArrayList<PrivilegeDto> privsOnRole = new ArrayList<PrivilegeDto>();

		ArrayList<String> usersList = (ArrayList) roleDto.getPrivs();
		for (String privId : usersList) {
			for (PrivilegeDto priv : privileges) {
				if (priv.getId().longValue() == Long.parseLong(privId)) {
					privsOnRole.add(priv);
				}
			}

		}

		return privsOnRole;
	}

	public void openModalForNewRole() {
		session.setAttribute("editModal", false);
		role = new RoleDto();

	}
}
