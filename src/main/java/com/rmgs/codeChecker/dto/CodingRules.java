package com.rmgs.codeChecker.dto;

import java.util.List;

/**
 * @author rehab.sayed
 *
 */
public class CodingRules {
	private int p;
	private int ps;
	private int total;
	private List<Rule> rules;
	private List<Facet> facets;
	public int getP() {
		return p;
	}
	public void setP(int p) {
		this.p = p;
	}
	public int getPs() {
		return ps;
	}
	public void setPs(int ps) {
		this.ps = ps;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<Rule> getRules() {
		return rules;
	}
	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	public List<Facet> getFacets() {
		return facets;
	}
	public void setFacets(List<Facet> facets) {
		this.facets = facets;
	}

}
