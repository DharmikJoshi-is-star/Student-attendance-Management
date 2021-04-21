//var path = "http://localhost:8080";

function onLoadPopulate(){

	var dId = document.getElementById("dId").value;	
	
	if(dId!=undefined){
		
		getDepartmentDetails(dId);
	
		getSubjectsDepartmentDetails(dId)
	
		getFacultiesDepartmentDetails(dId);
		
		getAllNonAssignedRFIDs(dId, "rfids");
		
		getOneWeekData(dId);
	}
	
}


function getOneWeekData(dId){

	fetch(path+"/lectureattendance/getOneWeekAttedance/"+dId,{
		method: 'GET',
		headers: {
			"Content-Type": "application/json",
		},
	})
	.then((res)=>res.json())
	.then((oneWeekData)=>{
		console.log("successfully fetched all data", oneWeekData);
		if(oneWeekData!=undefined){
			populateOneWeekChart(oneWeekData);
		}
	})
	.then((err)=>{
		//console.log(err);
		return;
	});
	
}

function getDepartmentDetails(dId){
	
	fetch(path+"/department/getDepartmentDetails/"+dId,{
		method: 'GET',
		headers: {
			"Content-Type": "application/json",
		},
	})
	.then((res)=>res.json())
	.then((departmentDetails)=>{
		console.log("successfully fetched all data", departmentDetails);
		if(departmentDetails!=null || departmentDetails!=undefined){
			
			setDepartmentDetails(departmentDetails);
		}
	})
	.then((err)=>{
		//console.log(err);
		return;
	});
	
}


function goToTimeTableDepartment(){
	var dId = document.getElementById("dId").value;	
	
	if(dId!=undefined){
		var a = document.createElement("a");
		a.href = path+"/timeTableDepartment?dId="+dId;
		a.click();
	}
	
}

function goToClass(){
	var dId = document.getElementById("dId").value;	
	
	if(dId!=undefined){
		var a = document.createElement("a");
		a.href = path+"/classes?dId="+dId;
		a.click();
	}
}

function goToRfidPage(){
	var dId = document.getElementById("dId").value;	
	
	if(dId!=undefined){
		var a = document.createElement("a");
		a.href = path+"/rfid?dId="+dId;
		a.click();
	}
}

function goToshowAllDepartmentStudents(){
	var dId = document.getElementById("dId").value;	
	
	if(dId!=undefined){
	var a = document.createElement("a");
	a.href = path+"/showAllDepartmentStudents?dId="+dId;
	a.click();
	}
}

function goTofindSubjectAttendanceByDay(){
	var dId = document.getElementById("dId").value;	
	
	if(dId!=undefined){
	var a = document.createElement("a");
	a.href = path+"/findSubjectAttendanceByDay?dId="+dId;
	a.click();
	}
}

function clearAllBufferAttendance(){
	
	var dId = document.getElementById("dId").value;	
	
	if(dId!=undefined){
		fetch(path+"/lectureattendance/deleteAllBufferedAttendance/"+dId,{
			method: 'DELETE',
			headers: {
				"Content-Type": "application/json",
			},
		})
		.then((res)=>res.json())
		.then((res)=>{
			console.log("Successfully deleted?", res);
			if(res==true){
				document.getElementById("bufferLabel").classList.add("label");
				document.getElementById("bufferLabel").classList.add("label-success");
				document.getElementById("bufferLabel").innerHTML = "Deleted Successfully!";
				
			}
			
		})
		.then((err)=>{
			//console.log(err);
			return;
		});
	}
}

function setDepartmentDetails(departmentDetails){

	document.getElementById("deptName").innerHTML = departmentDetails.name;
	
	//document.getElementById("name").innerHTML = departmentDetails.name;

	
	document.getElementById("studentCount").innerHTML = departmentDetails.studentCount;
	
	document.getElementById("subjectCount").innerHTML = departmentDetails.subjectCount;
	
	document.getElementById("facultyCount").innerHTML = departmentDetails.facultyCount;
	
	document.getElementById("currentLecture").innerHTML = departmentDetails.currentLecture;
	
	document.getElementById("studentsPresentInLecture").innerHTML = departmentDetails.studentsPresentInLecture;
	
	document.getElementById("lectureInClass").innerHTML = departmentDetails.lectureInClass;
	
	document.getElementById("nfcCount").innerHTML = departmentDetails.nfcCount;
	
	document.getElementById("classCount").innerHTML = departmentDetails.classCount;

	



	
}


function getFacultiesDepartmentDetails(dId){

	fetch(path+"/department/getFacultiesDepartmentDetails/"+dId,{
		method: 'GET',
		headers: {
			"Content-Type": "application/json",
		},
	})
	.then((res)=>res.json())
	.then((faculties)=>{
		console.log("successfully fetched all data", faculties);
		if(faculties){
			populateFacultyTable(faculties);
		}
	})
	.then((err)=>{
		//console.log(err);
		return;
	});
	
	
}


function populateFacultyTable(faculties){
  
	var tableBody = document.getElementById("faculty");
	
	for(var index = 0; index < faculties.length; index++){
		console.log(faculties[index]);
		tableBody.appendChild( 
			createRowForFacultyTable(tableBody.childNodes.length, faculties[index])
			);
	}

}




function createRowForFacultyTable(srno, faculty){

	var tr = document.createElement("tr");
	
	var tdSrno = document.createElement("td");
	tdSrno.innerHTML = srno;
	tr.appendChild(tdSrno);
	
	var tdName = document.createElement("td");
	tdName.innerHTML = faculty.name;
	tr.appendChild(tdName);
	
	var tdRfid = document.createElement("td");
	tdRfid.innerHTML = faculty.rfid;
	tr.appendChild(tdRfid);
	
	
	var tdCredentials = document.createElement("td");
	var tdView = document.createElement("a");
	tdView.classList.add("btn");
	tdView.classList.add("btn-info");
	tdView.classList.add("btn-small");
	tdView.href = "#credentialsFormModal";
	tdView.setAttribute("data-dismiss", "modal");
	tdView.innerHTML = "<i class=' icon-eye-open'> </i>";
	tdView.addEventListener("click",()=>{
		console.log("VIEW CREDENTIALS CLICKED");
		populateCredentialForm(faculty);
		document.getElementById("buttonCredentialsForm").click();
	});
	
	tdCredentials.appendChild(tdView);
	
	tr.appendChild(tdCredentials);
	
	var tdOpr = document.createElement("td");
	
	var tdEdit = document.createElement("button");
	tdEdit.classList.add("btn");
	tdEdit.classList.add("btn-info");
	tdEdit.classList.add("btn-small");
	tdEdit.innerHTML = "<i class='icon-edit'> </i>";
	tdEdit.addEventListener("click", ()=>{
		console.log("EDIT FACULTY ");
		editFaculty(faculty);
	});
	
	
	tdOpr.appendChild(tdEdit);
	
	var tdSpace = document.createElement("i");
	tdSpace.innerHTML = "  ";
	tdOpr.appendChild(tdSpace);
	
	var tdDelete = document.createElement("button");
	tdDelete.classList.add("btn");
	tdDelete.classList.add("btn-danger");
	tdDelete.classList.add("btn-small");
	tdDelete.innerHTML = "<i class='icon-trash'> </i>";
	tdDelete.addEventListener("click", ()=>{
		console.log("DELETE FACULTY ");
		deleteFaculty(faculty.id);
	});
	
	
	
	tdOpr.appendChild(tdDelete);
	
	tr.appendChild(tdOpr);
	
	return tr;
}


function getSubjectsDepartmentDetails(dId){
	
	fetch(path+"/department/getSubjectsDepartmentDetails/"+dId,{
		method: 'GET',
		headers: {
			"Content-Type": "application/json",
		},
	})
	.then((res)=>res.json())
	.then((subjects)=>{
		console.log("successfully fetched all data", subjects);
		if(subjects){
			populateSubjectTable(subjects);
		}
	})
	.then((err)=>{
		//console.log(err);
		return;
	});
	
}

function populateSubjectTable(subjects){
  
	var tableBody = document.getElementById("subject");
	
	for(var index = 0; index < subjects.length; index++){
		
		tableBody.appendChild( 
			createRowForSubjectTable(tableBody.childNodes.length, subjects[index])
			);
	}

}

function createRowForSubjectTable(srno, subject){

	var tr = document.createElement("tr");
	
	
	var tdSrno = document.createElement("td");
	tdSrno.innerHTML = srno;
	tr.appendChild(tdSrno);
	
	var tdName = document.createElement("td");
	tdName.innerHTML = subject.name;
	tr.appendChild(tdName);
	
	var tdLectureCount = document.createElement("td");
	tdLectureCount.innerHTML = subject.lectureCount;
	tr.appendChild(tdLectureCount);
	
	
	var tdOpr = document.createElement("td");
	
	var tdEdit = document.createElement("button");
	tdEdit.classList.add("btn");
	tdEdit.classList.add("btn-info");
	tdEdit.classList.add("btn-small");
	tdEdit.innerHTML = "<i class='icon-edit'> </i>";
	tdEdit.addEventListener("click",()=>{
		console.log("EDIT CLICKED");
		editSubject(subject);
	});
	
	
	tdOpr.appendChild(tdEdit);
	
	var tdSpace = document.createElement("i");
	tdSpace.innerHTML = "  ";
	tdOpr.appendChild(tdSpace);
	
	var tdDelete = document.createElement("button");
	tdDelete.classList.add("btn");
	tdDelete.classList.add("btn-danger");
	tdDelete.classList.add("btn-small");
	tdDelete.innerHTML = "<i class='icon-trash'> </i>";
	tdDelete.addEventListener("click",()=>{
		console.log("DELETE CLICKED");
		deleteSubject(subject.id);
		
	});
	
	tdOpr.appendChild(tdDelete);
	
	tr.appendChild(tdOpr);
	
	
	return tr;
}

function editSubject(subject){
	
	console.log("Hello");
	
	var subjectForm = document.getElementById("subjectForm");
	subjectForm["name"].value = subject.name;
	subjectForm["id"].value = subject.id;
	
	console.log(subject);
	
	document.getElementById("subjectModal").click();
	
}

function saveFacultyCredential(){
	var dId = document.getElementById("dId").value;	
	
	var credentialForm = document.getElementById("credentialForm");
	var username = credentialForm["username"].value;
	var password = credentialForm["password"].value;
	var facultyId = credentialForm["facultyId"].value;
	
	if(facultyId!=-1 && username!="" && password!="" && dId!=undefined){
		
		var login = {
			username: username,
			password: password
		};
		
		fetch(path+"/faculty/saveCredentials/"+facultyId,{
			
		 method: "POST", 
		 headers: {
		      "Content-Type": "application/json",
		    },
		 body: JSON.stringify(login),
		
		})
		.then((response)=> response.json())
		.then((response)=>{
			var a = document.createElement("a");
			a.href = path+"/departmentDashboard?dId="+dId;
			a.click();
		})
		.then((error)=>{
			//console.log("Error: ",error);
		});
		
	}
	
}

function populateCredentialForm(faculty){
	
	console.log(faculty);
	
	var credentialForm = document.getElementById("credentialForm");
	credentialForm["facultyId"].value = faculty.id;
	
	if(faculty.login==undefined){
		credentialForm["username"].value = "";
		credentialForm["password"].value = "";
		return ;
	}

	credentialForm["username"].value = faculty.login.username;
	credentialForm["password"].value = faculty.login.password;
}