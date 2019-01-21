package com.rmgs.codeChecker.dto;

import java.util.List;

/**
 * 
 * @author rehab.sayed
 *
 */
public class Issue {
	private String key;
	private String rule;
	private String serverity;
	private String component;
	private String project;
	private int line;
	private String hash;
	private TextRange textRange;
 //   "flows": [],
    private String status;
    private String message;
    private String author;
    private String[] tags;
   
   // "transitions": [],
    //"actions": [],
    
    private List<Comment> comments;
    private String creationDate;
   	private String updateDate;
   	private String type;
   	private String organization;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getServerity() {
		return serverity;
	}
	public void setServerity(String serverity) {
		this.serverity = serverity;
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public TextRange getTextRange() {
		return textRange;
	}
	public void setTextRange(TextRange textRange) {
		this.textRange = textRange;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
   	

}


