package com.rmgs.codeChecker.dto;

import java.util.List;
/**
 * 
 * @author rehab.sayed
 *
 */
public class Facet {
	private String property;
	private List<KeyCount> values;
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public List<KeyCount> getValues() {
		return values;
	}
	public void setValues(List<KeyCount> values) {
		this.values = values;
	}
	

}
