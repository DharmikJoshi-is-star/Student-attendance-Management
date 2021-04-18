//var path = "http://localhost:8080";

function addDepartment(){
	
	var departmentForm = document.getElementById("departmentForm");	
	
	console.log(departmentForm);
	console.log(departmentForm["name"].value);
	
	if(departmentForm["name"].value!=""){
		var department = {
			name: departmentForm["name"].value,
			todaysCompletedLectureCount: 0
		};
		postDepartment(department);
		departmentForm.reset();
	}else{
		//show Error
	}
	
}

function postDepartment(department){
	
	//write an API for Saving Department
	fetch(path+"/department/addDepartment",{
			
			 method: "POST", 
			 headers: {
			      "Content-Type": "application/json",
			    },
			 body: JSON.stringify(department),
			
		})
		.then((response)=> response.json())
		.then((response)=>{
			var a = document.createElement("a");
			a.href = path+"/dashboard";
			a.click();
		})
		.then((error)=>{
			console.log("Error: ",error);
		});
	
}
