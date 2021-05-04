//var path = "http://localhost:8080";

function checkCredentials(){
	
	let form = document.getElementById("loginform");
	
	let username = form['username'].value;
	let password = form['password'].value;
	
	if(username!="" && password!=""){
		
		isSuperAdmin(username,password);
		
	
	}
	else invalid();
	
	/*
	if(username=="admin" && password=="admin"){
		var a = document.createElement("a");
		a.href = "/dashboard";
		a.click();
	}
	
	
	
	/*
	if(username.length<6){
		document.getElementById("msg").innerHTML = "Username is to short to be verified";
		document.getElementById("msg").style.color = "red";
		form['password'].value = "";
		return;
		
	}
	else if(password.length<6){
		document.getElementById("msg").innerHTML = "Password is to short to be verified";
		document.getElementById("msg").style.color = "red";
		form['password'].value = "";
		return;
	}
	
	let user = {
			username: username,
			password: password,
	};
	
	fetch(path+"/verifyLoginCredentials",{
		
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
		body: JSON.stringify(user),
		
	})
	.then((response)=> response.json())
	.then((fId)=> {
		console.log("success: "+fId);
		
		if(fId!=0){
			
			fId = 15;
			var a = document.createElement("a");
			a.href = "/markAttendance?fId="+fId;
			a.click();
			
		}else{
			
			document.getElementById("msg").innerHTML = "Invalid Credentials!!<br>Kindly check your username/password";
			document.getElementById("msg").style.color = "red";
			form['password'].value = "";
		}
		
	})
	.catch((error)=> {
		console.log("Error: "+error);
	});
	
*/	
	
}



function verifyAdmin(username, password){
	
	var admin = {
		username: username,
		password: password
	};
	
	fetch(path+"/admin/verifyCredentials/",{
			
		 method: "POST", 
		 headers: {
		      "Content-Type": "application/json",
		    },
		 body: JSON.stringify(admin),
		
		})
		.then((response)=> response.json())
		.then((adminId)=>{
			if(adminId!=-1){
				valid();
				var a = document.createElement("a");
				a.href = path+"/login-process-admin?adminId="+adminId;
				a.click();
			}else{
				verifyFaculty(username, password);
			}
			
		})
		.then((error)=>{
			console.log("Error: ",error);
		});
		
	return undefined;
}


function verifyFaculty(username, password){
	
	var login = {
		username: username,
		password: password
	};
	
	fetch(path+"/login/verifyLoginCredentials/",{
			
		 method: "POST", 
		 headers: {
		      "Content-Type": "application/json",
		    },
		 body: JSON.stringify(login),
		
		})
		.then((response)=> response.json())
		.then((facultyId)=>{
			if(facultyId!=-1){
				valid();
				var a = document.createElement("a");
				a.href = path+"/login-process-faculty?facultyId="+facultyId;
				a.click();
			}else{
				invalid();
			}
			
		})
		.then((error)=>{
			console.log("Error: ",error);
		});
		
	return undefined;
}

function valid(){
	document.getElementById("msg").innerHTML = "Successfully Login!!";
	
	document.getElementById("msg").style.color = "chartreuse";
	
}

function invalid(){
	document.getElementById("msg").innerHTML = "Invalid Credentials!!<br>Kindly check your username/password";
	document.getElementById("msg").style.color = "red";
	document.getElementById("loginform")['password'].value = "";
}

function isSuperAdmin(username, password){
	
	var login = {
		username: username,
		password: password
	};
	
	fetch(path+"/admin/isSuperAdmin/",{
			
		 method: "POST", 
		 headers: {
		      "Content-Type": "application/json",
		    },
		 body: JSON.stringify(login),
		
		})
		.then((response)=> response.json())
		.then((isSuperAdmin)=>{
			if(isSuperAdmin){
				valid();
				var a = document.createElement("a");
				a.href = path+"/superAdminProcess";
				a.click();
			}else{
				verifyAdmin(username, password);
			}
			
		})
		.then((error)=>{
			console.log("Error: ",error);
		});
		
	return undefined;
}