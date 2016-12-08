function validEmail(email) {
 	var regex = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
 	return regex.test(email);
}
var instanceURL = "http://localhost:4567/";
var hirex = angular .module("hirexModule",[])
					.controller("loginController", function($scope, $http, $window){
						$scope.loginMessage = "";
						$scope.user = {};
						$scope.login = function(){
							var allGood = true;
							var email = document.getElementsByName("email")[0];
							var password = document.getElementsByName("password")[0];
							var role = document.getElementsByName("role")[0];
							// check if email field is empty
							if(email.value.trim() === ""){
								allGood = false;
								email.classList.add('error');
								setTimeout(function() {
									email.classList.remove('error');
								}, 300);
							}
							// check if password field is empty
							if(password.value.trim() === ""){
								allGood = false;
								password.classList.add('error');
								setTimeout(function() {
									password.classList.remove('error');
								}, 300);
							}
							if(role.value.trim() === "? undefined:undefined ?"){
								allGood = false;
								role.classList.add('error');
								setTimeout(function() {
									role.classList.remove('error');
								}, 300);
							}
							if(allGood){
								$scope.loginMessage = "Please wait while we try to log you in...";
								// angular trims the input fields by default, so no need to trim them before sending
								console.log($scope.user);
								$http({
										method : "POST",
										url : instanceURL + "login",
										data : $scope.user
								})
								.then(function(response) {
									// This is a success callback and will be called for status 200-299
									$scope.loginData = response.data;
									console.log($scope.loginData);
									//based on the response, we will either show an error message or redirect the user to the homepage
									if ($scope.loginData) {
										if($scope.loginData== '1'){
											$scope.loginMessage = "Logging in. Please wait..";
											if ($scope.user.role == "employee") {
												$window.location.href = "jobseeker.html";
											}
											else {
												$window.location.href = "employer.html"
											}
										} else {
											$scope.loginMessage = "Invalid credentials. Please try again.";
											$scope.user = {};
										}
									} else {
										$scope.loginMessage = "Sorry. Internal server error.";
										$scope.user = {};
									}
								},
								function(response){
									// This is a failure callback
									$scope.loginMessage = "Oops something went wrong. Please try after sometime.";
								});
							}
						};
					})
					.controller("signupController", function($scope, $http, $window){
						$scope.user = {};
						$scope.password2="";
						$scope.signup=function(){
							console.log($scope.user);
							var allGood = true;
							var name = document.getElementsByName("name")[0];
							var email = document.getElementsByName("email")[0];
							var password = document.getElementsByName("password")[0];
							var password2 = document.getElementsByName("password2")[0];
							var role = document.getElementsByName("role")[0];
							if(role.value.trim() === "? undefined:undefined ?"){
								allGood = false;
								role.classList.add('error');
								setTimeout(function() {
									role.classList.remove('error');
								}, 300);
								$scope.signupMessage = "";
							}
							
							// check if password is between 6 and 12 characters
							if(!(password.value.trim().length>5 && password.value.trim().length<13)){
								allGood = false;
								$scope.signupMessage = "Password should be between 6 and 12 characters.";
							}
							// check if email is valid
							if(!validEmail(email.value.trim())){
								allGood = false;
								$scope.signupMessage = "Please enter a valid email id.";
							}
							// check if first name is empty
							if(name.value.trim() === ""){
								allGood = false;
								name.classList.add('error');
								setTimeout(function() {
									name.classList.remove('error');
								}, 300);
								$scope.signupMessage = "";
							}
							// check if email field is empty
							if(email.value.trim() === ""){
								allGood = false;
								email.classList.add('error');
								setTimeout(function() {
									email.classList.remove('error');
								}, 300);
								$scope.signupMessage = "";
							}
							// check if password field is empty
							if(password.value.trim() === ""){
								allGood = false;
								password.classList.add('error');
								setTimeout(function() {
									password.classList.remove('error');
								}, 300);
								$scope.signupMessage = "";
							}
							// check if confirm password field is empty
							if(password2.value.trim() === ""){
								allGood = false;
								password2.classList.add('error');
								setTimeout(function() {
									password2.classList.remove('error');
								}, 300);
								$scope.signupMessage = "";
							}
							
							//Need to check if passswords match or not and execute as below if they match or need to take input again.
							if(allGood){
								$scope.signupMessage = "Please wait while we submit your data...";
								if($scope.user.password === $scope.password2) {
									$scope.signupMessage = "";
									$http({
										method 	: "POST",
										url 	: instanceURL + "signup",
										data 	: $scope.user
									})
									.then(function(response){
										$scope.signupResponse = response;
										$scope.signupData = response.data;
										$scope.signupStatus = response.status;
										if ($scope.signupData == '1') {
											$scope.signupMessage = "Registered. Redirecting to home..";
											// redirect to home page.
											if ($scope.user.role == "employee") {
												$window.location.href = "jobseeker.html";
											}
											else {
												$window.location.href = "employer.html"
											}
										}
										else {
											$scope.signupMessage = "There was an error processing your request.";
										}
									},
									function(response){
										$scope.signupMessage = "Sorry something went wrong on the server.";
									});
								} else{
									$scope.signupMessage = "Passwords do not match.";
								}
							}
						};
					})
					.config(function ($httpProvider) {
						$httpProvider.defaults.withCredentials = true;
					});