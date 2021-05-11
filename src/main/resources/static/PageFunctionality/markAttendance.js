//var path = "http://localhost:8080";

function onLoadPopulate(){
	
	var fId = document.getElementById("fId").value;
	if(fId!=undefined){
		getFacultyDetailsForMarkAtendance(fId);
	}

}

function getFacultyDetailsForMarkAtendance(fId){
	
	fetch(path+"/lectureattendance/facultyDetailsForMarkAttendance/"+fId,{
		method: 'GET',
		headers: {
			"Content-Type": "application/json",
		},
	})
	.then((res)=>res.json())
	.then((faculty)=>{
		console.log("successfully fetched all data", faculty);
		if(faculty){
			populateFacultyDetails(faculty);
		}
	})
	.then((err)=>{
		//console.log(err);
		return;
	});
	
}


function populateFacultyDetails(faculty){

	document.getElementById("facultyName").innerHTML = faculty.facultyName;
	document.getElementById("subjectName").innerHTML = faculty.subjectName;
	document.getElementById("classRoom").innerHTML = faculty.classRoom;
	document.getElementById("classRoomCode").innerHTML = faculty.classRoom;
	document.getElementById("date").innerHTML = faculty.date;
	document.getElementById("time").innerHTML = faculty.time;
	
}

function onMarkAttendanceClick(){
	var fId = document.getElementById("fId").value;
	if(fId!=undefined){
		console.log(fId, "available");
	callMarkAttendanceAPI(fId);
	}
}

function callMarkAttendanceAPI(fId){
	
	var tableBody = document.getElementById("attendance");
	
	tableBody.innerHTML = "";
	
	fetch(path+"/lectureattendance/markAttendance/"+fId,{
		method: 'GET',
		headers: {
			"Content-Type": "application/json",
		},
	})
	.then((res)=>res.json())
	.then((studentAttendances)=>{
		console.log("successfully fetched all data", studentAttendances);
		if(studentAttendances){
			populateStudentAttendance(studentAttendances);
		}
	})
	.then((err)=>{
		//console.log(err);
		return;
	});
	
}

function populateStudentAttendance(attendances){
	
var tableBody = document.getElementById("attendance");
	
	for(var index = 0; index < attendances.length; index++){
		
		tableBody.appendChild( 
			createRowAttendanceTable(index, attendances[index])
			);
	}	
	
}


function createRowAttendanceTable(index, attendance){
	
	/**
	private Long lectureAttendanceId;
	private Integer rollNo;
	private String studentName;
	private String departmentName;
	private String gender;
	private Boolean isPresent; */
	
	var tr = document.createElement("tr");
	
	var tdSrNo = document.createElement("td");
	tdSrNo.innerHTML = index+1;
	tr.appendChild(tdSrNo);
	
	var tdRollNo = document.createElement("td");
	tdRollNo.innerHTML = attendance.rollNo;
	tr.appendChild(tdRollNo);
	
	var tdStudentName = document.createElement("td");
	tdStudentName.innerHTML = attendance.studentName;
	tr.appendChild(tdStudentName);
	
	var tdDepartmentName = document.createElement("td");
	tdDepartmentName.innerHTML = attendance.departmentName;
	tr.appendChild(tdDepartmentName);
	
	var tdGender = document.createElement("td");
	tdGender.innerHTML = attendance.gender;
	tr.appendChild(tdGender);
	
	var tdIsPresent = document.createElement("td");
	tdIsPresent.id = "isPresentId"+attendance.lectureAttendanceId;
	if(attendance.isPresent)
		tdIsPresent.innerHTML = "Present";
	else
		tdIsPresent.innerHTML = "Absent";
	tr.appendChild(tdIsPresent);
	
	var tdOperation = document.createElement("td");
	
	var tdDelete = document.createElement("button");
	tdDelete.id = "attendanceId"+attendance.lectureAttendanceId;
	tdDelete.classList.add("btn");
	tdDelete.classList.add("btn-danger");
	tdDelete.classList.add("btn-small");
	tdDelete.innerHTML = "Delete";
	tdDelete.addEventListener("click", function(){
		deleteBufferedAttendanceOfStudent(attendance.lectureAttendanceId);
	});
	tdOperation.appendChild(tdDelete);
	
	tr.appendChild(tdOperation);
	
	
	
	return tr;
}


function deleteBufferedAttendanceOfStudent(attendanceId ){
	
	fetch(path+"/lectureattendance/deleteBufferedAttendanceOfStudent/"+attendanceId,{
		method: 'DELETE',
		headers: {
			"Content-Type": "application/json",
		},
	})
	.then((res)=>res.json())
	.then((response)=>{
		console.log("successfully fetched all data", response);
		if(response){
			document.getElementById("attendanceId"+attendanceId).disabled = true;
			document.getElementById("attendanceId"+attendanceId).onclick = true;
			document.getElementById("attendanceId"+attendanceId).innerHTML = "Deleted";
			document.getElementById("isPresentId"+attendanceId).innerHTML = "Absent";
		}
	})
	.then((err)=>{
		//console.log(err);
		return;
	});
	
}

function saveTable(tableId){
	exportToDocument(tableId);
}
