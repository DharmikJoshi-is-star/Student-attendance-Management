//var path = "http://localhost:8080";

function onLoadPopulate(){
	
	getAllDepartmentWithAPI();
}


function populateAllDepartmentsInTable(departments){
  
	var tableBody = document.getElementById("departments");
	
	for(var index = 0; index < departments.length; index++){
		
		tableBody.appendChild( 
			createRowForDepartmentTable(index, departments[index])
			);
	}

}


function getAllDepartmentWithAPI(){
	
	fetch(path+"/department/getAllDepartments",{
		method: 'GET',
		headers: {
			"Content-Type": "application/json",
		},
	})
	.then((res)=>res.json())
	.then((departments)=>{
		console.log("successfully fetched all data", departments);
		if(departments){
			populateAllDepartmentsInTable(departments);
			//populateSideMenuBar(departments);
		}
	})
	.then((err)=>{
		console.log(err);
		return;
	});
	
}

/*

function populateSideMenuBar(departments){
	
	var sideMenuBar = document.getElementById("sideMenuBar");
	
	for(var index = 0; index < departments.length; index++){
		
		sideMenuBar.appendChild( 
			createSingleDepartment(departments[index])
			);
	}
	
}


function createSingleDepartment(department){
	
	var li = document.createElement("li");
	
	var a = document.createElement("a");
	a.href = "/departmentDetails?dId="+department.id;
	
	var i = document.createElement("i");
	i.classList.add("icon");
	i.classList.add("icon-inbox");
	
	a.appendChild(i);
	
	var span = document.createElement("span");
	span.innerHTML = department.name;
	
	a.appendChild(span);
	
	li.appendChild(a);
	
	return li;
}

*/
function createRowForDepartmentTable(index, department){


	var tr = document.createElement("tr");
	
	var tdSrNo = document.createElement("td");
	tdSrNo.innerHTML = index+1;
	tr.appendChild(tdSrNo);
	
	var tdName = document.createElement("td");
	tdName.innerHTML = department.name;
	tr.appendChild(tdName);
	
	var tdFacultyCount = document.createElement("td");
	tdFacultyCount.innerHTML = department.facultyCount;
	tr.appendChild(tdFacultyCount);
	
	var tdSubjectCount = document.createElement("td");
	tdSubjectCount.innerHTML = department.subjectCount;
	tr.appendChild(tdSubjectCount);

    var tdStudentCount = document.createElement("td");
	tdStudentCount.innerHTML = department.studentCount;
	tr.appendChild(tdStudentCount);
	
	var tdDetails = document.createElement("td");
	var tdDetailsExpand = document.createElement("a");
	tdDetailsExpand.classList.add("btn");
	tdDetailsExpand.classList.add("btn-success");
	tdDetailsExpand.classList.add("btn-small");
	tdDetailsExpand.innerHTML = "Expand";
	tdDetailsExpand.href = "/departmentDetails?dId="+department.id;
	tdDetails.appendChild(tdDetailsExpand);
	
	tr.appendChild(tdDetails);
	
	return tr;
}



