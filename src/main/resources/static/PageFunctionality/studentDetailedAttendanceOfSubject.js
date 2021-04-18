//http://localhost:8080/lectureattendance/getStudentDetailedAttendanceOfSubject/44/7

//var path = "http://localhost:8080";

function onLoadPopulate(){
	
	var sId = document.getElementById("sId").value;
	var subId = document.getElementById("subId").value;
	if(sId!=undefined && subId!=undefined){
		console.log(sId);
		console.log(subId);
		getStudentDetailedAttendanceOfSubjectWithAPI(sId, subId);
	}
	
}


function getStudentDetailedAttendanceOfSubjectWithAPI(sId, subId){
	
	console.log("getStudentDetailedAttendanceOfSubject/"+sId+"/"+subId);
	
	fetch(path+"/lectureattendance/getStudentDetailedAttendanceOfSubject/"+sId+"/"+subId,{
		method: 'GET',
		headers: {
			"Content-Type": "application/json",
		},
	})
	.then((res)=>res.json())
	.then((subjectAttendance)=>{
		console.log("successfully fetched all data", subjectAttendance);
		if(subjectAttendance){
			populateSubjectAttendanceInTable(subjectAttendance);
		}
	})
	.then((err)=>{
		//console.log(err);
		return;
	});
	
}

function populateSubjectAttendanceInTable(subjectAttendance){
	
	var tableBody = document.getElementById("subjectAttendance");
	
	for(var index = 0; index < subjectAttendance.length; index++){
		
		tableBody.appendChild( 
			createRowSubjectAttendanceTable(index, subjectAttendance[index])
			);
	}
	
	document.getElementById("subjectDetailedReport").classList.add("data-table");

}

function createRowSubjectAttendanceTable(index, subjectAtt){
	
	var tr = document.createElement("tr");
	
	var tdSrNo = document.createElement("td");
	tdSrNo.innerHTML = index+1;
	tr.appendChild(tdSrNo);
	
	var tdClassNo = document.createElement("td");
	tdClassNo.innerHTML = subjectAtt.classNo;
	tr.appendChild(tdClassNo);
	
	var tdIsPresent = document.createElement("td");
	if(subjectAtt.isPresent)
		tdIsPresent.innerHTML = "YES";
	else
		tdIsPresent.innerHTML = "NO";
	tr.appendChild(tdIsPresent);
	
	var tdFacultyName = document.createElement("td");
	tdFacultyName.innerHTML = subjectAtt.facultyName;
	tr.appendChild(tdFacultyName);
	
	var tdDate = document.createElement("td");
	tdDate.innerHTML = subjectAtt.date;
	tr.appendChild(tdDate);
	
	var tdTime = document.createElement("td");
	tdTime.innerHTML = subjectAtt.time;
	tr.appendChild(tdTime);
	
	
	return tr;
}

function saveTable(tableId){
	exportToDocument(tableId);
}
