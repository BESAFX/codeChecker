package com.rmgs.codeChecker.dto;
/**
 * 
 * @author rehab.sayed
 *
 */
public class Rule {
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean getIsTemplate() {
		return isTemplate;
	}
	public void setIsTemplate(boolean isTemplate) {
		this.isTemplate = isTemplate;
	}
	public String[] getTages() {
		return tages;
	}
	public void setTages(String[] tages) {
		this.tages = tages;
	}
	public String[] getSysTags() {
		return sysTags;
	}
	public void setSysTags(String[] sysTags) {
		this.sysTags = sysTags;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getLangName() {
		return langName;
	}
	public void setLangName(String langName) {
		this.langName = langName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}

	private String key;
	private String name;
	private String status;
	private boolean isTemplate;
	private String[] tages;
	private String[] sysTags;
	private String lang;
	private String langName;
	private String type;
	private String severity;
	
	//private RuleDetails details=new RuleDetails();
	
}

