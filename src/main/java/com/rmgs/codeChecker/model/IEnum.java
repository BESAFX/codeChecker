package com.rmgs.codeChecker.model;

public interface IEnum {
	public abstract String name();
	public abstract String getSeverty();
	public default String getName(){
		return name();
	}
}
