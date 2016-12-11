package com.hireX.models;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;
import java.util.ArrayList;

public class BaseModel {
	private static MongoClient mongoClient = new MongoClient("localhost");
	public static MongoDatabase getConnection(){
		return mongoClient.getDatabase("hirex");
	}

	public static int verifyLogin(String email, String password, String role) {
		boolean emailValid=false, pwdValid=false, roleValid=false;
		int status = 0;
		MongoDatabase db = getConnection();
		MongoCollection<Document> col = db.getCollection("jobseekers");
		Document query = new Document("email", new Document("$eq", email));
		long count = col.count(query);
		if(count == 1) {
			emailValid = true;
			Document doc = col.find(query).into(new ArrayList<Document>()).get(0);
			if (doc.get("password").equals(password) && doc.get("role").equals(role)) {
				pwdValid = true;
				roleValid = true;
			}
			else {
				// invalid password or role.
				status = 1;
			}
		}
		else {
			// Email does not exist. Say invalid credentials
			status = 1;
		}
		return status;
	}
	public static int addUser(String email, String password, String role, String fullName) {
		int status = 0;
		MongoDatabase db = getConnection();
		MongoCollection<Document> col;
		// Decide which collection to add the user to
		if(role=="employer") {
			col = db.getCollection("employers");
		}
		else {
			col = db.getCollection("jobseekers");
		}
		// Need to check if the email id is already registered
		Document query = new Document("email", new Document("$eq", email));
		long count = col.count(query);
		if(count > 0) {
			status = 1;
		}
		else {
			Document user = new Document();
			user.put("email", email);
			user.put("password", password);
			user.put("role", role);
			user.put("name", fullName);
			col.insertOne(user);
		}
		return status;
	}
	
	public static boolean updateJobseeker(String email, String major, String exp, String relocation, String uscitizen, String notice, String gender, String veteran, String disabled) {
		// Need this to update the details of the job seeker
		boolean status = true;
		MongoDatabase db = getConnection();
		MongoCollection<Document> col = db.getCollection("jobseekers");
		// update the document for the given emailID
		Document data = new Document();
		Document who = new Document("email", new Document("$eq", email));
		data.put("$set", new Document("major", major));
		col.updateOne(who, data);
		data.put("$set", new Document("exp", exp));
		col.updateOne(who, data);
		data.put("$set", new Document("relocation", relocation));
		col.updateOne(who, data);
		data.put("$set", new Document("uscitizen", uscitizen));
		col.updateOne(who, data);
		data.put("$set", new Document("notice", notice));
		col.updateOne(who, data);
		data.put("$set", new Document("veteran", veteran));
		col.updateOne(who, data);
		data.put("$set", new Document("gender", gender));
		col.updateOne(who, data);
		data.put("$set", new Document("disabled", disabled));
		col.updateOne(who, data);
		return status;
	}
	
	public static String getJobseekerDetails(String email) {
		// Need this to fetch the details of the job seeker 
		// whose profile is to be updated or who is being viewed by the employer.
		MongoDatabase db = getConnection();
		MongoCollection<Document> col = db.getCollection("jobseekers");
		Document query = new Document("email", new Document("$eq", email));
		if (col.count(query) == 0) {
			return "{}";
		}
		else {
			Document details = col.find(query).into(new ArrayList<Document>()).get(0);
			return details.toJson();
		}
	}
	
	public static ArrayList<Document> getJobseekers(String major, String exp, String relocation, String uscitigen, String notice, String gender, String veteran, String disabled) {
		// This gets the details of all the job seekers that match the criteria
		// and returns a list of json strings
		ArrayList<Document> jobseekers = new ArrayList<Document>();
		
		return jobseekers;
	}
}