package com.hireX.controllers;
import com.google.gson.Gson;

import com.hireX.models.*;
import static spark.Spark.*;
import java.util.*;

public class BaseController {
	private static void enableCORS(final String origin, final String methods, final String headers) {

	    options("/*", (request, response) -> {

	        String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
	        if (accessControlRequestHeaders != null) {
	            response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
	        }

	        String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
	        if (accessControlRequestMethod != null) {
	            response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
	        }
	        return "OK";
	    });

	    before((request, response) -> {
	        response.header("Access-Control-Allow-Origin", origin);
	        response.header("Access-Control-Request-Method", methods);
	        response.header("Access-Control-Allow-Headers", headers);
	        response.header("Access-Control-Allow-Credentials", "true");
	        // Note: this may or may not be necessary in your particular application
	        response.type("application/json");
	    });
	}
	
	public static void main(String[] args){
		enableCORS("http://dev.localhost.com", "POST", "");
		post("/login",(req, res)->{
			Gson gson = new Gson();
			res.type("application/json");
			System.out.println(req.contentType());
			System.out.println(req.body());
			System.out.println(req.queryParams("role"));
			loginControllerParams params = gson.fromJson(req.body(),loginControllerParams.class);
			System.out.println(params.emailId);
			System.out.println(params.password);
			System.out.println(params.role);
			if(BaseModel.verifyLogin(params.emailId,params.password,params.role))
				return 1;
			else
				return 0;
		});
		
		post("/signup",(req, res)->{
			
			Gson gson = new Gson();
			res.type("application/json");
			signupControllerParams params = gson.fromJson(req.body(),signupControllerParams.class);
			System.out.println(params.role);
			if(BaseModel.addUser(params.emailId, params.password, params.role, params.fullName))
				return 1;
			else
				return 0;
		});
		
		post("/updateJobseeker",(req, res)->{
			
			Gson gson = new Gson();
			res.type("application/json");
			updateControllerParams params = gson.fromJson(req.body(), updateControllerParams.class);
			System.out.println(params.major);
			if(BaseModel.updateJobseeker(params.emailId, params.major, params.exp, params.relocation, params.uscitizen, params.notice, params.gender, params.veteran, params.disabled))
				return 1;
			else
				return 0;
		});
	}
}