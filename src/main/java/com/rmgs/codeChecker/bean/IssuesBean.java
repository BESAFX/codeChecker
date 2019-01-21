package com.rmgs.codeChecker.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rmgs.codeChecker.api.ApiClient;
import com.rmgs.codeChecker.dto.IssuesDto;
/**
 * 
 * @author rehab.sayed
 *
 */
@ManagedBean
@RequestScoped
@ViewScoped
@Component
public class IssuesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7733359804447604308L;
	public static final Logger logger = LoggerFactory.getLogger(IssuesBean.class);
	private IssuesDto issuesDto;
	@Autowired
	ApiClient apiClient;

	
	public IssuesDto getIssuesDto() {
		return issuesDto;
	}
	public void setIssuesDto(IssuesDto issuesDto) {
		this.issuesDto = issuesDto;
	}



	@PostConstruct
	public void init() {
		
		try{
			
			issuesDto = apiClient.getIssues();
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	


	public void buttonAction(ActionEvent actionEvent) {
        addMessage("Welcome to Primefaces!!");
    }
     
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
