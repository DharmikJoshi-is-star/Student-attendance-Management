function addDepartment(){
	
	var departmentForm = document.getElementById("departmentForm");
	
	var name = departmentForm["name"].value;
	
	if(name!=""){
		var department = {
			name: name
		};
		console.log(department);
		postDepartment(department)
		departmentForm.reset();
	}else{
		error();
	}
		
}

function postDepartment(department){
	////write an API for Saving faculty
	
	var aId = document.getElementById("adminId").value;	
	
	if(aId!=undefined){
		
		fetch(path+"/department/addDepartment/"+aId,{
			
		 method: "POST", 
		 headers: {
		      "Content-Type": "application/json",
		    },
		 body: JSON.stringify(department),
		
		})
		.then((response)=> response.json())
		.then((departmentData)=>{
			
			if(departmentData!=undefined || departmentData!=null){
				success();
				
				var a = document.createElement("a");
				a.href = path+"/createDepartmentProcess?dId="+departmentData.id;
				a.click();
				
			}else{
				error();
			}			
		})
		.then((error)=>{
			console.log("Error: ",error);
		});
		
	}
}
