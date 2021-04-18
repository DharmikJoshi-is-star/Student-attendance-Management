//var path = "http://localhost:8080";

function onLoadPopulate(){
	
	var sId = document.getElementById("sId").value;
	
	if(sId!=undefined){
		getAllStudentWithIdAPI(sId);
		averageAttendanceInAllSubjects(sId);
	}
	
	
}

function goToStudentMonthlyReport(){
	var sId = document.getElementById("sId").value;
	
	if(sId!=undefined){
	var a = document.createElement("a");
	a.href = "/studentMonthlyReportsProcess?sId="+sId;
	a.click();
	}
}


function getAllStudentWithIdAPI(sId){
	
	fetch(path+"/student/getStudent/"+sId,{
		method: 'GET',
		headers: {
			"Content-Type": "application/json",
		},
	})
	.then((res)=>res.json())
	.then((student)=>{
		console.log("successfully fetched all data", student);
		if(student){
			populateStudentDetails(student);
		}
	})
	.then((err)=>{
		console.log(err);
		return;
	});
}


function populateStudentDetails(student){
	
	/**
	averageAttendance: 100
currentClass: "301"
currentLogDate: "2021-03-05"
currentLogTime: "13:43:22"
currentStatus: "OUT"
deptName: "Computer Engineering"
gender: "MALE"
id: 44
name: "Dharmik Joshi"
rfid: "2900940E95"
rollno: 1
status: null
totalLecturesPresent: 2
	 */
	
	document.getElementById("studentName").innerHTML = student.name;
	
	document.getElementById("studentRollNo").innerHTML = "Rollno: "+student.rollno;
	
	document.getElementById("studentGender").innerHTML = "Gender: "+student.gender;
	
	document.getElementById("studentDepartmentName").innerHTML = "Department Name: "+student.deptName;
	
	document.getElementById("studentAverageAttendance").innerHTML = "Average Attendace: "+student.averageAttendance + " %";
	
	document.getElementById("studentTotalLecturesPresent").innerHTML = "Total Lectures Attended: "+student.totalLecturesPresent;
	
	document.getElementById("studentRFID").innerHTML = student.rfid;
	
	document.getElementById("studentCurrentStatus").innerHTML = student.currentStatus;
	
	document.getElementById("studentCurrentClass").innerHTML = student.currentClass;//"Average Attendace: "+averageAttendanceOfStudent(student.id);
	
	document.getElementById("studentCurrentLogDate").innerHTML = student.currentLogDate;//"Average Attendace: "+averageAttendanceOfStudent(student.id);
	
	document.getElementById("studentCurrentLogTime").innerHTML = student.currentLogTime;//"Average Attendace: "+averageAttendanceOfStudent(student.id);
	
	
	/*
	studentName
	studentRollNo
	studentGender
	studentDepartmentName
	studentTotalLecturesPresent
	studentAverageAttendance
	studentRFID
	studentCurrentStatus
	studentCurrentClass
	studentCurrentLogDate
	studentCurrentLogTime
	*/
	
}

function averageAttendanceInAllSubjects(sId){
	
	fetch(path+"/lectureattendance/averageAttedndanceInAllSubjects/"+sId,{
		method: 'GET',
		headers: {
			"Content-Type": "application/json",
		},
	})
	.then((res)=>res.json())
	.then((subjectAttendances)=>{
		console.log("successfully fetched all data", subjectAttendances);
		if(subjectAttendances){
			populateSubjectTable(subjectAttendances, sId);
		}
	})
	.then((err)=>{
		//console.log(err);
		return;
	});
	
}


function populateSubjectTable(subjectAttendances, sId){
	var tableBody = document.getElementById("subjectAttendance");
	
	for(var index = 0; index < subjectAttendances.length; index++){
		
		tableBody.appendChild( 
			createRowForSubjectTable(index+1, subjectAttendances[index], sId)
			);
	}
}



function createRowForSubjectTable(srno, subject, sId){

	var tr = document.createElement("tr");
	
	var tdSrno = document.createElement("td");
	tdSrno.innerHTML = srno;
	tr.appendChild(tdSrno);
	
	var tdName = document.createElement("td");
	tdName.innerHTML = subject.name;
	tr.appendChild(tdName);
	
	var tdLectureAttended = document.createElement("td");
	tdLectureAttended.innerHTML = subject.lecturesAttended;
	tr.appendChild(tdLectureAttended);
	
	var tdLectureCount = document.createElement("td");
	tdLectureCount.innerHTML = subject.lectureCount;
	tr.appendChild(tdLectureCount);
	
	var tdAvgAttendance = document.createElement("td");
	tdAvgAttendance.innerHTML = subject.averageAttended+"%";
	tr.appendChild(tdAvgAttendance);
	
	//lecturesAttended
	//averageAttended
	//lectureCount
	
	var tdOpr = document.createElement("td");
	
	var tdExpand = document.createElement("a");
	tdExpand.classList.add("btn");
	tdExpand.classList.add("btn-info");
	tdExpand.classList.add("btn-small");
	tdExpand.innerHTML = "Expand";
	tdExpand.href = "/subjectDetailReportProcess?sId="+sId+"&subId="+subject.id;
	
	tdOpr.appendChild(tdExpand);
	
	tr.appendChild(tdOpr);
	
	
	return tr;
}




function saveReport(){
	var fileName = document.getElementById("studentName").innerHTML;
	exportToPdf('studentDetails', fileName);
}
