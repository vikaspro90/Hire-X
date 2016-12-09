package com.hireX.controllers;
import com.google.gson.Gson;
import com.hireX.models.*;
import static spark.Spark.*;
import java.util.*;

public class signupController {
	public static void main(String[] args){
		post("/signup",(req, res)->{
		
			Gson gson = new Gson();
			res.type("application/json");
			signupControllerParams params = gson.fromJson(req.body(),signupControllerParams.class);
			System.out.println(params.role);
			if(BaseModel.addUser(params.emailId,params.password,params.role,params.name))
				return 1;
			else
				return 0;
		});
	}
}
