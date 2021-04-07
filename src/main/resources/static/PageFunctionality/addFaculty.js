var path = "http://localhost:8080";

function addFaculty(){
	
	var facultyForm = document.getElementById("facultyForm");
	
	var id = facultyForm["id"].value;
	var name = facultyForm["name"].value;
	var rfid = facultyForm["rfid"].value;
	
	console.log(facultyForm);
	if(id=="-1"){
		
		if(name!="" && rfid!=""){
			var faculty = {
				name: name,
				rfid: rfid,
				
			};
			console.log(faculty);
			postFaculty(faculty)
			facultyForm.reset();
		}else{
			//show an error
		}
	}else{
		
		if(name!="" && rfid!=""){
			var faculty = {
				id: id,
				name: name,
				rfid: rfid,
				
			};
			console.log(faculty);
			editFacultyAPI(faculty);
			facultyForm.reset();
		}else{
			//show an error
		}
		
	}
	
	
}

function editFaculty(faculty){
	
	var facultyForm = document.getElementById("facultyForm");
	
	facultyForm["id"].value = faculty.id;
	facultyForm["name"].value = faculty.name;
	document.getElementById("assignedToken").innerHTML = faculty.rfid;
	facultyForm["rfid"].value = faculty.rfid;
	
	document.getElementById("buttonFacultyForm").click();
}

function deleteFaculty(facultyId){
	
	
	var dId = document.getElementById("dId").value;	
	
	console.log("dId=", dId);
	console.log("sId=", facultyId);
	

	if(dId!=undefined && facultyId!=undefined){
		
		fetch(path+"/faculty/deleteFaculty/"+facultyId,{
			
		 method: "DELETE", 
		 headers: {
		      "Content-Type": "application/json",
		    },
		})
		.then((response)=> response)
		.then((response)=>{
			var a = document.createElement("a");
			a.href = path+"/departmentDetails?dId="+dId;
			a.click();
		})
		.then((error)=>{
			console.log("Error: ",error);
		});
		
	}
	
}


function postFaculty(faculty){
	////write an API for Saving faculty
	var dId = document.getElementById("dId").value;	
	
	if(dId!=undefined){
		
		fetch(path+"/faculty/addFaculty/"+dId,{
			
		 method: "POST", 
		 headers: {
		      "Content-Type": "application/json",
		    },
		 body: JSON.stringify(faculty),
		
		})
		.then((response)=> response.json())
		.then((response)=>{
			var a = document.createElement("a");
			a.href = path+"/departmentDetails?dId="+dId;
			a.click();
		})
		.then((error)=>{
			console.log("Error: ",error);
		});
		
	}
}

function editFacultyAPI(faculty){
	
	var dId = document.getElementById("dId").value;	
	
	if(dId!=undefined && faculty.id!=undefined){
		
		fetch(path+"/faculty/editFaculty/"+faculty.id,{
			
		 method: "POST", 
		 headers: {
		      "Content-Type": "application/json",
		    },
		 body: JSON.stringify(faculty),
		
		})
		.then((response)=> response.json())
		.then((response)=>{
			var a = document.createElement("a");
			a.href = path+"/departmentDetails?dId="+dId;
			a.click();
		})
		.then((error)=>{
			console.log("Error: ",error);
		});
		
	}
	
}