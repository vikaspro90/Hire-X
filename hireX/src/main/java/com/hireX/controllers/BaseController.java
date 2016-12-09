package com.hireX.controllers;
import com.google.gson.Gson;
import org.bson.Document;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.hireX.models.*;
import static spark.Spark.*;

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
			res.type("application/json");
			JSONParser parser = new JSONParser();
			JSONObject jsonObj = (JSONObject)parser.parse(req.body());
			
			if(BaseModel.verifyLogin((String)jsonObj.get("emailId"), (String)jsonObj.get("password"), (String)jsonObj.get("role")))
				return 1;
			else
				return 0;
		});
		
		post("/signup",(req, res)->{
			
//			Gson gson = new Gson();
			res.type("application/json");
//			signupControllerParams params = gson.fromJson(req.body(),signupControllerParams.class);
//			System.out.println(params.role);
			JSONParser parser = new JSONParser();
			JSONObject jsonObj = (JSONObject)parser.parse(req.body());
			if(BaseModel.addUser((String)jsonObj.get("emailId"), (String)jsonObj.get("password"), (String)jsonObj.get("role"), (String)jsonObj.get("name")))
				return 1;
			else
				return 0;
		});
		
		post("/updateJobseeker",(req, res)->{
			
//			Gson gson = new Gson();
			res.type("application/json");
//			updateControllerParams params = gson.fromJson(req.body(), updateControllerParams.class);
//			System.out.println(params.major);
			JSONParser parser = new JSONParser();
			JSONObject jsonObj = (JSONObject)parser.parse(req.body());
			if(BaseModel.updateJobseeker((String)jsonObj.get("emailId"), (String)jsonObj.get("major"), (String)jsonObj.get("exp"), (String)jsonObj.get("relocation"), (String)jsonObj.get("uscitizen"), (String)jsonObj.get("notice"), (String)jsonObj.get("gender"), (String)jsonObj.get("veteran"), (String)jsonObj.get("disabled")))
				return 1;
			else
				return 0;
		});
		
		post("/getJobseekerDetails",(req, res)->{
			System.out.println("Getting job seeker details.");
//			Gson gson = new Gson();
			res.type("application/json");
			JSONParser parser = new JSONParser();
			JSONObject jsonObj = (JSONObject)parser.parse(req.body());
//			getDetailsControllerParams params = gson.fromJson(req.body(), getDetailsControllerParams.class);
			String details = BaseModel.getJobseekerDetails((String)jsonObj.get("emailId"));
			return details;
		});
	}
}