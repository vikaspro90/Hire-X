package com.hireX.controllers;

public class signupControllerParams {

	public String emailId;
	public String password;
	public String role;
	public String fullName;
	public signupControllerParams(){
		
	}
	public signupControllerParams(String emailId, String password, String role,String fullName){
		this.emailId=emailId;
		this.password=password;
		this.role=role;
		this.fullName=fullName;
	}
}
