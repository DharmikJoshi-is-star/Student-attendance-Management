//var path = "http://localhost:8080";

function onLoadPopulateSideMenuBar(){
	getAllDepartmentWithAPI();
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
			populateSideMenuBar(departments);
		}
	})
	.then((err)=>{
		console.log(err);
		return;
	});
	
}

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

