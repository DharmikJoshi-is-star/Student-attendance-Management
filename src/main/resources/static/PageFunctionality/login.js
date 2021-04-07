var path = "http://localhost:8080";

function checkCredentials(){
	
	let form = document.getElementById("loginform");
	
	let username = form['username'].value;
	let password = form['password'].value;
	
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
	*/
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
	
	
	
}