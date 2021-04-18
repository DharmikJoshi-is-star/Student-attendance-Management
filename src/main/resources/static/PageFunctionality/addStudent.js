//var path = "http://localhost:8080";

function addStudent(){
		
	var studentForm = document.getElementById("studentForm");
	
	var id = studentForm["id"].value;
	var name = studentForm["name"].value;
	var rollno = studentForm["rollno"].value;
	var gender = studentForm["gender"].value;
	var rfid = studentForm["rfid"].value;
	
	
	//rollno gender rfid
	if(id!=undefined && name!=undefined && rollno!=undefined && gender!=undefined && rfid!=undefined){
	
		var student = {
			id: id,
			name: name,
			rollno: rollno,
			gender:gender,
			rfid:rfid,
			
		};
		console.log(student);
		
		if(student.id=='-1'){
			postStudent(student);
		}else{
			editStudent(student);
		}
	
		studentForm.reset();
	}else{
		//show an error
	}
	
}


function postStudent(student){
	////write an API for Saving subject
	
	var dId = document.getElementById("dId").value;	
	
	if(dId!=undefined){
		
		fetch(path+"/student/addStudent/"+dId,{
			
		 method: "POST", 
		 headers: {
		      "Content-Type": "application/json",
		    },
		 body: JSON.stringify(student),
		
		})
		.then((response)=> response.json())
		.then((studentResponse)=>{
			if(studentResponse!=undefined || studentResponse!=null){
				var studentData = [studentResponse];
				populateAllStudentInTable(studentData)
				//var a = document.createElement("a");
				//a.href = path+"/showAllDepartmentStudents?dId="+dId;
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

function editStudent(student){
	
	var sId = student.id;
	var dId = document.getElementById("dId").value;	
	
	if(sId!=undefined && dId!=undefined){
		
		fetch(path+"/student/editStudent/"+sId,{
			
		 method: "POST", 
		 headers: {
		      "Content-Type": "application/json",
		    },
		 body: JSON.stringify(student),
		
		})
		.then((response)=> response.json())
		.then((response)=>{
			var a = document.createElement("a");
			a.href = path+"/showAllDepartmentStudents?dId="+dId;
			a.click();
		})
		.then((error)=>{
			console.log("Error: ",error);
		});
		
	}
}

function editStudentForm(student){
	var studentForm = document.getElementById("studentForm");
	
	studentForm["id"].value = student.id;
	studentForm["name"].value = student.name;
	studentForm["rollno"].value = student.rollno;
	studentForm["gender"].value =student.gender;
	studentForm["rfid"].value = student.rfid;
	
	document.getElementById("assignedToken").innerHTML = student.rfid;
	
	document.getElementById("buttonStudentForm").click();
}

function deleteStudent(studentId){
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
		deleteStudentAPI(studentId);
		return true;
    } else {
      swal("Cancelled", "Your imaginary file is safe :)", "error");
		return false;
    }
	});
} 

function deleteStudentAPI(studentId){
	
	var dId = document.getElementById("dId").value;	
	
	console.log("dId=", dId);
	console.log("sId=", studentId);
	

	if(dId!=undefined && studentId!=undefined){
		
		fetch(path+"/student/deleteStudent/"+studentId,{
			
		 method: "DELETE", 
		 headers: {
		      "Content-Type": "application/json",
		    },
		})
		.then((response)=> response)
		.then((response)=>{
			var a = document.createElement("a");
			a.href = path+"/departmentStudents";
			a.click();
		})
		.then((error)=>{
			console.log("Error: ",error);
		});
		
	}
}