//var path = "http://localhost:8080";


function onLoadPopulateStudentInfo(){
	
	
	var sId = document.getElementById("sId").value;	
	
	if(sId!=undefined){
	
		fetch(path+"/student/getStudentInfo/"+sId,{
			method: 'GET',
			headers: {
				"Content-Type": "application/json",
			},
		})
		.then((res)=>res.json())
		.then((studentInfo)=>{
			console.log("successfully fetched all data", studentInfo);
			if(studentInfo!=undefined){
				populateStudentInfoForm(studentInfo);
			}
		})
		.then((err)=>{
			//console.log(err);
			return;
		});
	
		
		
	}

}


function populateStudentInfoForm(studentInfo){
	
	var studentInfoForm = document.getElementById("studentInfoForm");
		
	studentInfoForm["address"].value = studentInfo.address;
	studentInfoForm["contactNumber"].value = studentInfo.contactNumber;
	studentInfoForm["mailAddress"].value = studentInfo.mailAddress;
	studentInfoForm["age"].value = studentInfo.age;
	
}

function addStudentInfo(){
		
	var studentInfoForm = document.getElementById("studentInfoForm");
	
	var address = studentInfoForm["address"].value;
	var contactNumber = studentInfoForm["contactNumber"].value;
	var mailAddress = studentInfoForm["mailAddress"].value;
	var age = studentInfoForm["age"].value;
	
	//rollno gender rfid
	if(address!=undefined && contactNumber!=undefined && mailAddress!=undefined){
	
		var studentInfo = {
			address: address,
			contactNumber: contactNumber,
			mailAddress: mailAddress,
			age:age
			
		};
		console.log(studentInfo);
		postStudentInfo(studentInfo);
		studentInfoForm.reset();
	}else{
		//show an error
	}
	
}

function postStudentInfo(studentInfo){
	////write an API for Saving subject
	
	var sId = document.getElementById("sId").value;	
	
	if(sId!=undefined){
		
		fetch(path+"/student/addStudentInfo/"+sId,{
			
		 method: "POST", 
		 headers: {
		      "Content-Type": "application/json",
		    },
		 body: JSON.stringify(studentInfo),
		
		})
		.then((response)=> response.json())
		.then((response)=>{
			var a = document.createElement("a");
			a.href = path+"/departmentStudentDetails"; 
			a.click();
		})
		.then((error)=>{
			console.log("Error: ",error);
		});
		
	}
	
}