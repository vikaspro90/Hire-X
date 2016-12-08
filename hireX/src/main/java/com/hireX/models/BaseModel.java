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
		MongoCollection<Document> col = db.getCollection("users");
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
		MongoCollection<Document> col = db.getCollection("users");
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
}
