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
									//console.log($scope.loginData);
									//based on the response, we will either show an error message or redirect the user to the homepage
									if($scope.loginData == 0){
										$scope.loginMessage = "Logging in. Please wait..";
										if ($scope.user.role == "jobseeker") {
											$window.location.href = "jobseeker.html";
										}
										else {
											$window.location.href = "employer.html"
										}
									} else {
										$scope.loginMessage = "Invalid credentials. Please try again.";
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
							// check if name is empty
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
								if($scope.user.password === $scope.password2) {
									$scope.signupMessage = "Please wait while we submit your data...";
									console.log($scope.user.name);
									$http({
										method 	: "POST",
										url 	: instanceURL + "signup",
										data 	: $scope.user
									})
									.then(function(response){
										$scope.signupResponse = response;
										$scope.signupData = response.data;
										$scope.signupStatus = response.status;
										if ($scope.signupData == '0') {
											$scope.signupMessage = "Registered. Redirecting to home..";
											// redirect to home page.
											if ($scope.user.role == "jobseeker") {
												$window.location.href = "jobseeker.html";
											}
											else {
												$window.location.href = "employer.html"
											}
										}
										else {
											$scope.signupMessage = "This email Id is already registered with us. Please use a different email Id if you are registering for a different role.";
										}
									},
									function(response){
										$scope.signupMessage = "Sorry something went wrong on the server.";
									});
								} else{
									$scope.signupMessage = "Both passwords should match.";
								}
							}
						};
					})
					.controller("updateController", function($scope, $http, $window){
						$scope.details = {};
						$scope.updateMessage = "";
						//////
						// First of all we need to get the details of the user and diplay them
						//////
						$scope.update = function(){
							$scope.updateMessage = "Your details are being updated...";
							// angular trims the input fields by default, so no need to trim them before sending
							console.log($scope.details);
							$http({
									method : "POST",
									url : instanceURL + "updateJobseeker",
									data : $scope.details
							})
							.then(function(response) {
								// This is a success callback and will be called for status 200-299
								$scope.updateData = response.data;
								console.log($scope.responseData);
								//based on the response, we will either show an error message or redirect the user to the homepage
								if($scope.responseData== '1') {
									$scope.updateMessage = "Your details have been updated as requested.";
								} else {
									$scope.updateMessage = "Sorry. An error occured and your details could not be updated.";
								}
							},
							function(response){
								// This is a failure callback
								$scope.updateMessage = "Oops something went wrong. Please try after sometime.";
							});
						};
					})
					.controller("searchController", function($scope, $http, $window){
						// take input from the search employer page and send the criteria to backend,
						// If any matching profile exists, take to the next page and display the results on that page.
						// Pass the details from this page to the next page
						$scope.criteria = {};
						$scope.search = function() {
							$http({
									method : "POST",
									url : instanceURL + "updateJobseeker",
									data : $scope.details
							})
							.then(function(response) {
								// This is a success callback and will be called for status 200-299
								$scope.updateData = response.data;
								console.log($scope.responseData);
								//based on the response, we will either show an error message or redirect the user to the homepage
								if($scope.responseData== '1') {
									$scope.updateMessage = "Your details have been updated as requested.";
								} else {
									$scope.updateMessage = "Sorry. An error occured and your details could not be updated.";
								}
							},
							function(response){
								// This is a failure callback
								$scope.updateMessage = "Oops something went wrong. Please try after sometime.";
							});
						};
					})
					.controller("resultsController", function($scope, $http, $window){
						// If we get to this page, we already know that there are matching profiles.
						// Need to get the details from the previous page
					})
					.controller("profileController", function($scope, $http, $window){
						$scope.emailId = "";
						$scope.mainDiv = true;

						var url = document.location.href;
						var params = url.split('?')[1];
						if (params){
							$scope.emailId = params.split('=')[1];
						}
						if($scope.emailId){
							$scope.profileMessage = "Please wait while we fetch the profile details of "+$scope.emailId+".";
							// fetch the details for the email
							var details = {"emailId":$scope.emailId};
							// console.log("sending req");
							// console.log(details);
							$http({
									method : "POST",
									url : instanceURL + "getJobseekerDetails",
									data : details
							})
							.then(function(response) {
								// This is a success callback and will be called for status 200-299
								$scope.profileData = response.data;
								// console.log($scope.profileData);
								if($scope.profileData.email) {
									$scope.profileMessage = "Below are the details of "+$scope.profileData.name;
									if($scope.profileData.major) {
										$scope.major = $scope.profileData.major;
									} else {
										$scope.major = "Not updated";
									}
									if($scope.profileData.exp) {
										$scope.exp = $scope.profileData.exp;
									} else {
										$scope.exp = "Not updated";
									}
									if($scope.profileData.relocation) {
										$scope.relocation = $scope.profileData.relocation;
									} else {
										$scope.relocation = "Not updated";
									}
									if($scope.profileData.uscitizen) {
										$scope.uscitizen = $scope.profileData.uscitizen;
									} else {
										$scope.uscitizen = "Not updated";
									}
									if($scope.profileData.notice) {
										$scope.notice = $scope.profileData.notice;
									} else {
										$scope.notice = "Not updated";
									}
									if($scope.profileData.gender) {
										$scope.gender = $scope.profileData.gender;
									} else {
										$scope.gender = "Not updated";
									}
									if($scope.profileData.veteran) {
										$scope.veteran = $scope.profileData.veteran;
									} else {
										$scope.veteran = "Not updated";
									}
									if($scope.profileData.disabled) {
										$scope.disabled = $scope.profileData.disabled;
									} else {
										$scope.disabled = "Not updated";
									}			
								} else {
									$scope.mainDiv = false;
									$scope.profileMessage = "We do not have any profile with the email ID "+$scope.emailId;
								}
							},
							function(response){
								console.log(response.data);
								// This is a failure callback
								$scope.profileMessage = "Oops something went wrong. Please try after sometime.";
							});
						} else {
							$scope.mainDiv = false;
							$scope.profileMessage = "We need an email ID to get the details of a profile.";
						}
					})
					.controller("logoutController", function($scope, $http, $window){
						console.log("In Logout");
						$scope.logout = function(){
							console.log("Logging out");
							$http({
										method : "POST",
										url : instanceURL + "logout",
								})
								.then(function(response) {
									// This is a success callback and will be called for status 200-299
									$window.alert("You have been logged out. Click Ok to continue.");
									$window.location.href = "login.html";
								},
								function(response){
									// This is a failure callback
									$window.alert("Oops something went wrong. Could not log you out.");
								});
						};
					})
					.config(function ($httpProvider) {
						$httpProvider.defaults.withCredentials = true;
					});