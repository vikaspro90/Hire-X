package com.hireX.controllers;

public class updateControllerParams {

	public String emailId;
	public String major;
	public String exp;
	public String relocation;
	public String uscitizen;
	public String notice;
	public String gender;
	public String veteran;
	public String disabled;
	public updateControllerParams(){
		
	}
	public updateControllerParams(String emailId, String major, String exp, String relocation, 
			String uscitizen, String notice, String gender, String veteran, String disabled){
		this.emailId=emailId;
		this.major=major;
		this.exp=exp;
		this.relocation=relocation;
		this.uscitizen=uscitizen;
		this.notice=notice;
		this.gender=gender;
		this.veteran=veteran;
		this.disabled=disabled;
	}
}