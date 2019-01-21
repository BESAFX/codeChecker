package com.rmgs.codeChecker.dto;
/**
 * 
 * @author rehab.sayed
 *
 */
public class ComponentDto {

	private String organization;
	private String id;
	private String key;
	private String name;
	private String projectUrl;
	private String projectUserName;
	private String projectPassword;
	private String analysisDate;
	private String visibility;
	private Boolean processed = false;
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAnalysisDate() {
		return analysisDate;
	}
	public void setAnalysisDate(String analysisDate) {
		this.analysisDate = analysisDate;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	public String getProjectUrl() {
		return projectUrl;
	}
	public void setProjectUrl(String projectUrl) {
		this.projectUrl = projectUrl;
	}
	public String getProjectUserName() {
		return projectUserName;
	}
	public void setProjectUserName(String projectUserName) {
		this.projectUserName = projectUserName;
	}
	public String getProjectPassword() {
		return projectPassword;
	}
	public void setProjectPassword(String projectPassword) {
		this.projectPassword = projectPassword;
	}
	public Boolean getProcessed() {
		return processed;
	}
	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}
     
	
    // "tags": [],
  
	
	
}
