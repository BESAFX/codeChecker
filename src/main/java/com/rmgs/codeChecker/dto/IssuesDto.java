package com.rmgs.codeChecker.dto;

import java.util.List;

public class IssuesDto {
	List<Issue> issues;
	List<Facet> facets;
	List<Component> components;
	public List<Issue> getIssues() {
		return issues;
	}
	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}
	public List<Facet> getFacets() {
		return facets;
	}
	public void setFacets(List<Facet> facets) {
		this.facets = facets;
	}
	public List<Component> getComponents() {
		return components;
	}
	public void setComponents(List<Component> components) {
		this.components = components;
	}
	

}
