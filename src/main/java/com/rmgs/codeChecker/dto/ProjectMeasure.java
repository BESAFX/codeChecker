package com.rmgs.codeChecker.dto;

public class ProjectMeasure {
	private ComponentDto project=new ComponentDto();
	private String alertStatus;
	private String bugs;
	private String codeSmell;
	private String coverage;
	private String duplicatedLineDensity;
	private String nclocl;
	private String nclocLangDist;
	private String relRating;
	private String secRating;
	private String sqaleRating;
	private String vulnerabilities;

	public ComponentDto getProject() {
		return project;
	}

	public void setProject(ComponentDto project) {
		this.project = project;
	}

	public String getAlertStatus() {
		return alertStatus;
	}

	public void setAlertStatus(String alertStatus) {
		this.alertStatus = alertStatus;
	}

	public String getBugs() {
		return bugs;
	}

	public void setBugs(String bugs) {
		this.bugs = bugs;
	}

	public String getCodeSmell() {
		return codeSmell;
	}

	public void setCodeSmell(String codeSmell) {
		this.codeSmell = codeSmell;
	}

	public String getDuplicatedLineDensity() {
		return duplicatedLineDensity;
	}

	public void setDuplicatedLineDensity(String duplicatedLineDensity) {
		this.duplicatedLineDensity = duplicatedLineDensity;
	}

	public String getNclocl() {
		return nclocl;
	}

	public void setNclocl(String nclocl) {
		this.nclocl = nclocl;
	}

	public String getNclocLangDist() {
		return nclocLangDist;
	}

	public void setNclocLangDist(String nclocLangDist) {
		this.nclocLangDist = nclocLangDist;
	}

	public String getRelRating() {
		return relRating;
	}

	public void setRelRating(String relRating) {
		this.relRating = relRating;
	}

	public String getSecRating() {
		return secRating;
	}

	public void setSecRating(String secRating) {
		this.secRating = secRating;
	}

	public String getSqaleRating() {
		return sqaleRating;
	}

	public void setSqaleRating(String sqaleRating) {
		this.sqaleRating = sqaleRating;
	}

	public String getVulnerabilities() {
		return vulnerabilities;
	}

	public void setVulnerabilities(String vulnerabilities) {
		this.vulnerabilities = vulnerabilities;
	}

	public String getCoverage() {
		return coverage;
	}

	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

}
