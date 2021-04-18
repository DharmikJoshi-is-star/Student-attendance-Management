//var path = "http://localhost:8080";

function onLoadPopulate(){
	
	var dId = document.getElementById("dId").value;	
	console.log(dId);
	if(dId!=undefined){
		getAllClasses(dId);
	}
}

function getAllClasses(dId){
	
	fetch(path+"/class/getAllClasses/"+dId,{
		method: 'GET',
		headers: {
			"Content-Type": "application/json",
		},
	})
	.then((res)=>res.json())
	.then((classes)=>{
		console.log("successfully fetched all data", classes);
		if(classes!=undefined){
			populateAllClassesInTable(classes);
		}
	})
	.then((err)=>{
		//console.log(err);
		return;
	});
}

function populateAllClassesInTable(classes){
	
	var tableBody = document.getElementById("classes");
	
	
	for(var index = 0; index < classes.length; index++){
		
		tableBody.appendChild( 
			createRowForClassTable(index, classes[index])
			);
	}
	
}



function createRowForClassTable(index, classObj){
	
	var tr = document.createElement("tr");
	
	var tdSrNo = document.createElement("td");
	tdSrNo.innerHTML = index+1;
	tr.appendChild(tdSrNo);
	
	var tdRoomNo = document.createElement("td");
	tdRoomNo.innerHTML = classObj.classNo;
	tr.appendChild(tdRoomNo);
	
	var tdDeviceCode = document.createElement("td");
	tdDeviceCode.innerHTML = classObj.deviceCode;
	tr.appendChild(tdDeviceCode);
		
	var tdOpr = document.createElement("td");
	
	var editOpr = document.createElement("a");
	editOpr.classList.add("btn");
	editOpr.classList.add("btn-danger");
	editOpr.classList.add("btn-small");
	editOpr.innerHTML = "EDIT";
	editOpr.addEventListener("click", ()=>{
		console.log("EDIT CLICKED");
		editClassForm(classObj);
	});
	tdOpr.appendChild(editOpr);
	
	var space = document.createElement("span");
	space.innerHTML = " ";
	tdOpr.appendChild(space);
	
	var deleteOpr = document.createElement("a");
	deleteOpr.classList.add("btn");
	deleteOpr.classList.add("btn-danger");
	deleteOpr.classList.add("btn-small");
	deleteOpr.innerHTML = "DELETE";
	deleteOpr.addEventListener("click", ()=>{
		console.log("DELETE CLICKED");
		deleteClass(classObj.id);
	});
	tdOpr.appendChild(deleteOpr);
	
	tr.appendChild(tdOpr);
	
	return tr;
}

function addClass(){


	var classForm = document.getElementById("classForm");
	
	var id = classForm["id"].value;
	var roomNo = classForm["roomNo"].value;
	var deviceCode = classForm["deviceCode"].value;

	if(id!="" && roomNo!="" && deviceCode!=""){
		
		var classObj = {
			id: id,
			classNo: roomNo,
			deviceCode: deviceCode	
		};
		
		console.log(classObj);
		
		if(classObj.id=="-1"){
			postClass(classObj);
		}else{
			editClass(classObj);
		}
		
	}

}

function editClassForm(classObj){
	var classForm = document.getElementById("classForm");
	
	classForm["id"].value = classObj.id;
	classForm["roomNo"].value = classObj.classNo;
	classForm["deviceCode"].value = classObj.deviceCode;
	
	document.getElementById("buttonClassForm").click();
	
}

function postClass(classObj){
	var dId = document.getElementById("dId").value;	
	
	if(dId!=undefined){
		
		fetch(path+"/class/addClass/"+dId,{
			
		 method: "POST", 
		 headers: {
		      "Content-Type": "application/json",
		    },
		 body: JSON.stringify(classObj),
		
		})
		.then((response)=> response.json())
		.then((response)=>{
			var a = document.createElement("a");
			a.href = path+"/classes?dId="+dId;
			a.click();
		})
		.then((error)=>{
			//console.log("Error: ",error);
		});
		
	}
}

function editClass(classObj){
	
	var dId = document.getElementById("dId").value;	
	
	if(dId!=undefined && classObj.id!=undefined){
		
		fetch(path+"/class/editClass/"+classObj.id,{
			
		 method: "POST", 
		 headers: {
		      "Content-Type": "application/json",
		    },
		 body: JSON.stringify(classObj),
		
		})
		.then((response)=> response.json())
		.then((response)=>{
			var a = document.createElement("a");
			a.href = path+"/classes?dId="+dId;
			a.click();
		})
		.then((error)=>{
			//console.log("Error: ",error);
		});
		
	}
}

function deleteClass(classId){
	
		
	var dId = document.getElementById("dId").value;	
	
	console.log("dId=", dId);
	console.log("classId=", classId);
	

	if(dId!=undefined && classId!=undefined){
		
		fetch(path+"/class/deleteClass/"+classId,{
			
		 method: "DELETE", 
		 headers: {
		      "Content-Type": "application/json",
		    },
		})
		.then((response)=> response)
		.then((response)=>{
			var a = document.createElement("a");
			a.href = path+"/classes?dId="+dId;
			a.click();
		})
		.then((error)=>{
			console.log("Error: ",error);
		});
		
	}
	
}