package com.hireX.controllers;

import static spark.Spark.*;

public class BaseController {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		get("/hello", (req, res) -> {
			return "Hello World";
		});
	}
}