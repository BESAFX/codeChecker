package com.rmgs.codeChecker.dto;

import java.util.List;

public class RuleDetails {
	private String key;
	private String repo;
	private String name;
	private String createdAt;
	private String htmlDesc;
	private String mdDesc;
	private String mdNote;
	private String noteLogin;
	private String severity;
	private String status;
	private String internalKey;
	private String isTemplate;
	private List<String> tags;
	private List<String> sysTags;
	private List<String> parms;
	private String lang;
	private String langName;
	private String defaultDebtRemFnType;
	private String defaultDebtRemFnOffset;
	private String debtRemFnType;
	private String debtRemFnOffset;
	private String defaultRemFnType;
	private String defaultRemFnBaseEffort;
	private String remFnType;
	private String remFnBaseEffort;
	private String type;
	private boolean debtOverloaded;
	private boolean remFnOverloaded;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<String> getSysTags() {
		return sysTags;
	}
	public void setSysTags(List<String> sysTags) {
		this.sysTags = sysTags;
	}
	public String getRepo() {
		return repo;
	}
	public void setRepo(String repo) {
		this.repo = repo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getHtmlDesc() {
		return htmlDesc;
	}
	public void setHtmlDesc(String htmlDesc) {
		this.htmlDesc = htmlDesc;
	}
	public String getMdDesc() {
		return mdDesc;
	}
	public void setMdDesc(String mdDesc) {
		this.mdDesc = mdDesc;
	}
	public String getMdNote() {
		return mdNote;
	}
	public void setMdNote(String mdNote) {
		this.mdNote = mdNote;
	}
	public String getNoteLogin() {
		return noteLogin;
	}
	public void setNoteLogin(String noteLogin) {
		this.noteLogin = noteLogin;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInternalKey() {
		return internalKey;
	}
	public void setInternalKey(String internalKey) {
		this.internalKey = internalKey;
	}
	public String getIsTemplate() {
		return isTemplate;
	}
	public void setIsTemplate(String isTemplate) {
		this.isTemplate = isTemplate;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public List<String> getParms() {
		return parms;
	}
	public void setParms(List<String> parms) {
		this.parms = parms;
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
	public String getDefaultDebtRemFnType() {
		return defaultDebtRemFnType;
	}
	public void setDefaultDebtRemFnType(String defaultDebtRemFnType) {
		this.defaultDebtRemFnType = defaultDebtRemFnType;
	}
	public String getDefaultDebtRemFnOffset() {
		return defaultDebtRemFnOffset;
	}
	public void setDefaultDebtRemFnOffset(String defaultDebtRemFnOffset) {
		this.defaultDebtRemFnOffset = defaultDebtRemFnOffset;
	}
	public String getDebtRemFnType() {
		return debtRemFnType;
	}
	public void setDebtRemFnType(String debtRemFnType) {
		this.debtRemFnType = debtRemFnType;
	}
	public String getDebtRemFnOffset() {
		return debtRemFnOffset;
	}
	public void setDebtRemFnOffset(String debtRemFnOffset) {
		this.debtRemFnOffset = debtRemFnOffset;
	}
	public String getDefaultRemFnType() {
		return defaultRemFnType;
	}
	public void setDefaultRemFnType(String defaultRemFnType) {
		this.defaultRemFnType = defaultRemFnType;
	}
	public String getDefaultRemFnBaseEffort() {
		return defaultRemFnBaseEffort;
	}
	public void setDefaultRemFnBaseEffort(String defaultRemFnBaseEffort) {
		this.defaultRemFnBaseEffort = defaultRemFnBaseEffort;
	}
	public String getRemFnType() {
		return remFnType;
	}
	public void setRemFnType(String remFnType) {
		this.remFnType = remFnType;
	}
	public String getRemFnBaseEffort() {
		return remFnBaseEffort;
	}
	public void setRemFnBaseEffort(String remFnBaseEffort) {
		this.remFnBaseEffort = remFnBaseEffort;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isDebtOverloaded() {
		return debtOverloaded;
	}
	public void setDebtOverloaded(boolean debtOverloaded) {
		this.debtOverloaded = debtOverloaded;
	}
	public boolean isRemFnOverloaded() {
		return remFnOverloaded;
	}
	public void setRemFnOverloaded(boolean remFnOverloaded) {
		this.remFnOverloaded = remFnOverloaded;
	}
	
}
