//var path = "http://localhost:8080";

function addSubject(){
		
	var subjectForm = document.getElementById("subjectForm");
	
	var id = subjectForm["id"].value;
	var name = subjectForm["name"].value;
	
	if(name!=undefined){
		
		//document.getElementById("subjectNameError").classList.remove("error");
		
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
		.then((subjectResponse)=>{
			if(subjectResponse!=undefined || subjectResponse!=null){
				var subjectData = [subjectResponse];
				populateSubjectTable(subjectData);
				//var a = document.createElement("a");
				//a.href = path+"/departmentDetails?dId="+dId;
				//a.click();
				success();
			}else{
				error();
			}
			
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
	
	swal({
		title: "Are you sure?",
		text: "You will not be able to recover this Data!",
		type: "warning",
		showCancelButton: true,
		confirmButtonColor: '#DD6B55',
		confirmButtonText: 'Yes, delete it!',
		cancelButtonText: "No, cancel plx!",
		closeOnConfirm: false,
		closeOnCancel: false
	},
	
	function(isConfirm){
    if (isConfirm){
		swal("Deleted!", "Your file has been deleted!", "success");
		deleteSubjectAPI(sId);
		return true;
    } else {
      swal("Cancelled", "Your imaginary file is safe :)", "error");
		return false;
    }
	});
	
}


function deleteSubjectAPI(sId){
	
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
			a.href = path+"/departmentDashboard";
			a.click();
		})
		.then((error)=>{
			console.log("Error: ",error);
		});
		
	}
}