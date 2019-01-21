package com.rmgs.codeChecker.dto;

import java.util.List;
/**
 * 
 * @author rehab.sayed
 *
 */
public class DashbaordDto {
	private Paging paging;
	private List<ComponentDto> components;
	private List<Facet> facets;

	public Paging getPaging() {
		return paging;
	}
	public void setPaging(Paging paging) {
		this.paging = paging;
	}
	public List<ComponentDto> getComponents() {
		return components;
	}
	public void setComponents(List<ComponentDto> components) {
		this.components = components;
	}
	public List<Facet> getFacets() {
		return facets;
	}
	public void setFacets(List<Facet> facets) {
		this.facets = facets;
	}
	
	
}
