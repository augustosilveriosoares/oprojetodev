package com.opd.core.annotation;

public class UserInfo {

	private String principal;

	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean hasUser() {
		return this.principal != null && !this.principal.isBlank();
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

}