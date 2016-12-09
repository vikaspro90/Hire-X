package com.hireX.controllers;

public class signupControllerParams {

	public String emailId;
	public String password;
	public String role;
	public String name;
	
	public signupControllerParams(String emailId, String password, String role, String name){
		this.emailId=emailId;
		this.password=password;
		this.role=role;
		this.name=name;
	}
}