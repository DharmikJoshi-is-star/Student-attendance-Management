var path = "http://localhost:8080";

function addSubject(){
		
	var subjectForm = document.getElementById("subjectForm");
	
	var id = subjectForm["id"].value;
	var name = subjectForm["name"].value;
	
	if(name!=undefined){
		
		if(id==-1){
			var subject = {
			
				name: name
			};
			console.log(subject);
			postSubject(subject);
		}else{
			
			var subject = {
				id: id,
				name: name
			};
			console.log(subject);
			submitEditSubject(subject);
		}
		
		
		subjectForm.reset();
	}else{
		
	}
	
}

function postSubject(subject){
	////write an API for Saving subject
	
	var dId = document.getElementById("dId").value;	
	
	if(dId!=undefined){
		
		fetch(path+"/subject/addSubject/"+dId,{
			
		 method: "POST", 
		 headers: {
		      "Content-Type": "application/json",
		    },
		 body: JSON.stringify(subject),
		
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

function submitEditSubject(subject){
	
	var dId = document.getElementById("dId").value;	
	
	if(subject.id!=undefined && dId!=undefined){
		
		fetch(path+"/subject/editSubject/"+subject.id,{
			
		 method: "POST", 
		 headers: {
		      "Content-Type": "application/json",
		    },
		 body: JSON.stringify(subject),
		
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

function deleteSubject(sId){
	
	var dId = document.getElementById("dId").value;	
	
	console.log("dId=", dId);
	console.log("sId=", sId);
	
	
	
	if(dId!=undefined && sId!=undefined){
		
		fetch(path+"/subject/deleteSubject/"+sId,{
			
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