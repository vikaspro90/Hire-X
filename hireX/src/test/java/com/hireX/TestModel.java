package com.hireX;
import com.hireX.models.BaseModel;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String email = "vikas1590@gmail.com";
		String password = "vikas1590";
		String role = "aspirant";
//		BaseModel test = new BaseModel();
		System.out.println(BaseModel.verifyLogin(email, password, role));
		BaseModel.addUser("vikaspro90@gmail.com", "vvvv", "aspirant", "vikas");
	}
}