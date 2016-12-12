package com.hireX.controllers;
import com.google.gson.Gson;
import org.bson.Document;
import java.util.ArrayList;
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
		enableCORS("http://dev.localhost.com:8080", "POST", "");
		before((request, response)->{
			// This should be executed for everything except login/signup/logout
			// If email in session, then check for role. if role does not match, then not allowed.
			// Halt with an error code. The error code should indicate a specific error.
			// which should be handled and displayed at the frontend
		});
		
		post("/login",(req, res)->{
			System.out.println("Logging the user in.");
			res.type("application/json");
			JSONParser parser = new JSONParser();
			JSONObject jsonObj = (JSONObject)parser.parse(req.body());
			
			int status = BaseModel.verifyLogin((String)jsonObj.get("emailId"), (String)jsonObj.get("password"), (String)jsonObj.get("role"));
			System.out.println(status);
			if(status==0) {
				// setup session
				req.session().attribute("email", (String)jsonObj.get("emailId"));
				System.out.println(req.session().id());
			}
			System.out.println("Success"+status);
			return status;
		});
		
		post("/signup",(req, res)->{
			System.out.println("Signing up new user.");
//			Gson gson = new Gson();
			res.type("application/json");
//			signupControllerParams params = gson.fromJson(req.body(),signupControllerParams.class);
//			System.out.println(params.role);
			JSONParser parser = new JSONParser();
			JSONObject jsonObj = (JSONObject)parser.parse(req.body());
			int status = BaseModel.addUser((String)jsonObj.get("emailId"), (String)jsonObj.get("password"), (String)jsonObj.get("role"), (String)jsonObj.get("name"));
			if(status == 0) {
				// setup session
			}
			return status;
		});
		
		post("/updateJobseeker",(req, res)->{
			// Only job seekers should be able to access this.
			// If no session, redirect to login
			System.out.println("Updating job seeker details.");
			
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
		
		post("/searchCandidates",(req, res)->{
			// Only employers should be able to access this.
			// If no session, redirect to login
			System.out.println("Getting matching candidates.");
			res.type("application/json");
			JSONParser parser = new JSONParser();
			JSONObject jsonObj = (JSONObject)parser.parse(req.body());
			ArrayList<String> candidates = BaseModel.getJobseekers((String)jsonObj.get("major"), (String)jsonObj.get("exp"), (String)jsonObj.get("relocation"), (String)jsonObj.get("uscitizen"), (String)jsonObj.get("notice"), (String)jsonObj.get("gender"), (String)jsonObj.get("veteran"), (String)jsonObj.get("disabled"));
			return candidates;
		});
		
		post("/getJobseekerDetails",(req, res)->{
			// Only job seekers should be able to access this.
			// If no session, redirect to login
			System.out.print("Getting details for ");
			res.type("application/json");
			JSONParser parser = new JSONParser();
			JSONObject jsonObj = (JSONObject)parser.parse(req.body());
			System.out.println((String)jsonObj.get("emailId"));
			String details = BaseModel.getJobseekerDetails((String)jsonObj.get("emailId"));
			System.out.println(req.session().id()+" "+req.session().attribute("email"));
			return details;
		});
		
		post("/logout",(req, res)->{
			System.out.println("Logging out");
			// delete the session and redirect to login page
			req.session().invalidate();
			return 1;
		});
	}
}