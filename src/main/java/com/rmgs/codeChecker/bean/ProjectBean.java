package com.rmgs.codeChecker.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rmgs.codeChecker.api.ApiClient;
import com.rmgs.codeChecker.dto.Project;

/**
 * 
 * @author michael.george
 *
 */
@ManagedBean
@RequestScoped
@Component
@Scope(value = "request")
public class ProjectBean implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final Logger logger = LoggerFactory.getLogger(ProjectBean.class);
	private Project project = new Project();
	private Project deletedProject;

	public Project getDeletedProject() {
		return deletedProject;
	}

	public void setDeletedProject(Project deletedProject) {
		this.deletedProject = deletedProject;
	}

	FacesContext facesContext = FacesContext.getCurrentInstance();
	HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

	@Autowired
	ApiClient apiClient;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@PostConstruct
	public void init() {

		try {

			project = apiClient.getProject();
			if (session.getAttribute("originalEditedProjectObject") != null) {
				project = (Project) session.getAttribute("originalEditedProjectObject");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String initEditModal(Project project) throws IOException {
		session.setAttribute("editProjectModal", true);

		this.project = project;
		session.setAttribute("originalEditedProjectObject", project);
		return null;
	}

	public void openModalForNewProject() {
		session.setAttribute("createProjectModal", false);
		this.project = new Project();
	}

	public final void handleEvent(final AjaxBehaviorEvent event) {

		FacesContext context = FacesContext.getCurrentInstance();
		this.project = context.getApplication().evaluateExpressionGet(context, "#{project}", Project.class);
	}

	/*
	 * 
	 * Delete
	 */
	public String deleteAction(Project project) throws IOException {
		session.setAttribute("deletedProject_object", project);
		deletedProject = project;
		return null;
	}

	public String confirmDelete() throws IOException {
		deletedProject = (Project) session.getAttribute("deletedProject_object");
		apiClient.deleteProject(deletedProject.getId());
		reload();
		return null;
	}

	private void reload() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}

	public void proccessModalAction() throws IOException {
		boolean editAction = false;
		if (session.getAttribute("editProjectModal") != null) {
			editAction = (boolean) session.getAttribute("editProjectModal");
		}

		if (editAction) {
			Project originalEditedProject = (Project) session.getAttribute("originalEditedProjectObject");

			originalEditedProject.setName(project.getName());
			apiClient.updateProject(originalEditedProject);
		} else {
			this.project.setName(project.getName());
			apiClient.createProject(this.project);
		}
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "What we do in life",
				"Echoes in eternity.");

		PrimeFaces.current().dialog().showMessageDynamic(message);
		reload();
	}

}
