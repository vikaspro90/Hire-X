package com.hireX.controllers;

public class loginControllerParams {
	String emailId;
	String password;
	String role;
	
	public loginControllerParams(String emailId, String password, String role){
		this.emailId=emailId;
		this.password=password;
		this.role=role;
	}

}
