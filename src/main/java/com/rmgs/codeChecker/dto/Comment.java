package com.rmgs.codeChecker.dto;

public class Comment {
	private String key;
	private String login;
	private String htmlText;
	private String markdown;
	private boolean updatable;
	private String createdAt;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getHtmlText() {
		return htmlText;
	}
	public void setHtmlText(String htmlText) {
		this.htmlText = htmlText;
	}
	public String getMarkdown() {
		return markdown;
	}
	public void setMarkdown(String markdown) {
		this.markdown = markdown;
	}
	public boolean isUpdatable() {
		return updatable;
	}
	public void setUpdatable(boolean updatable) {
		this.updatable = updatable;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

}
