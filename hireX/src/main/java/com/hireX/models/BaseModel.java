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

	public static boolean verifyLogin(String email, String password, String role) {
		boolean status=false, emailValid=false, pwdValid=false, roleValid=false;
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
				status = true;
			}
		}
		return status;
	}
	public static boolean addUser(String email, String password, String role, String fullName) {
		boolean status = true;
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
			return false;
		}
		Document user = new Document();
		user.put("email", email);
		user.put("password", password);
		user.put("role", role);
		user.put("name", fullName);
		col.insertOne(user);
		return status;
	}
	
	public static boolean updateJobseeker(String email, String major, String exp, String relocation, String uscitizen, String notice, String gender, String veteran, String disabled) {
		boolean status = true;
		MongoDatabase db = getConnection();
		MongoCollection<Document> col = db.getCollection("jobseekers");
		// update the document for the given emailID
		Document data = new Document();
		data.put("$set", new Document("major", major));
		data.put("$set", new Document("exp", exp));
		data.put("$set", new Document("relocation", relocation));
		data.put("$set", new Document("uscitizen", uscitizen));
		data.put("$set", new Document("notice", notice));
		data.put("$set", new Document("veteran", veteran));
		data.put("$set", new Document("gender", gender));
		data.put("$set", new Document("disabled", disabled));
		Document who = new Document("email", new Document("$eq", email));
		col.updateOne(who, data);
		return status;
	}
	
	public static ArrayList<Document> getJobseekers(String major, String exp, String relocation, String uscitigen, String notice, String gender, String veteran, String disabled) {
		
		ArrayList<Document> jobseekers = new ArrayList<Document>();
		
		return jobseekers;
	}
}