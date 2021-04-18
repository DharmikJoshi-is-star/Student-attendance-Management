
//var path = "http://localhost:8080";
var monthValue = "";
var departmentName = "";
var yearValue = "";


function onLoadPopulate(){
	var dId = document.getElementById("dId").value;	
	
	if(dId!=undefined){
		getAverageAttendanceOfStudents(dId);
		
	}
	
}


function getAverageAttendanceOfStudents(dId){
	
	fetch(path+"/lectureattendance/getAverageAttendanceOfStudents/"+dId,{
		method: 'GET',
		headers: {
			"Content-Type": "application/json",
		},
	})
	.then((res)=>res.json())
	.then((students)=>{
		console.log("successfully fetched all data", students);
		if(students){
			populateAllStudentInTable(students);
		}
	})
	.then((err)=>{
		console.log(err);
		return;
	});
	
}

function populateAllStudentInTable(students){
	var tableBody = document.getElementById("students");
	
	for(var index = 0; index < students.length; index++){
		
		tableBody.appendChild( 
			createRowForStudentTable(index, students[index])
			);
	}

}

function createRowForStudentTable(index, student){
	
	departmentName = student.deptName;
	
	var tr = document.createElement("tr");
	tr.classList.add("gradeX");
	if(index%2==1){
		tr.classList.add("odd");
	}else tr.classList.add("even");
	
	var tdSrNo = document.createElement("td");
	tdSrNo.classList.add("sorting_1");
	tdSrNo.innerHTML = index+1;
	tr.appendChild(tdSrNo);
	
	var tdRollNo = document.createElement("td");
	tdRollNo.innerHTML = student.rollno;
	tr.appendChild(tdRollNo);
	
	var tdName = document.createElement("td");
	tdName.innerHTML = student.name;
	tr.appendChild(tdName);
	
	var tdLecturePresent = document.createElement("td");
	tdLecturePresent.innerHTML = student.totalLecturesPresent;
	tr.appendChild(tdLecturePresent);
	
	var tdLectureTaken = document.createElement("td");
	tdLectureTaken.innerHTML = student.totalLecturesTaken;
	tr.appendChild(tdLectureTaken);
	
	var tdAverageAttendance = document.createElement("td");
	tdAverageAttendance.innerHTML = student.averageAttendance+"%";
	
	tr.appendChild(tdAverageAttendance);
	
	return tr;
}


function saveTable(tableId){
	
	var fileName = departmentName+"-"+monthValue+"-"+yearValue;

	exportToDocument(tableId, fileName);
}


function findAverageAttendanceOfMonth(){
	var month = document.getElementById("filterForm")["month"].value;
	var year = document.getElementById("filterForm")["year"].value;
	var dId = document.getElementById("dId").value;	
	
	if(month!="" && dId!=undefined && year!=""){
		monthValue = month;
		yearValue = year;
		getAverageAttendanceOfStudentsPerMonth(dId, month, year);
	}else{
		monthValue = "";
		yearValue = "";
		getAverageAttendanceOfStudents(dId);
	}
}


function getAverageAttendanceOfStudentsPerMonth(dId, month, year){
	
	fetch(path+"/lectureattendance/getAverageAttendanceOfStudentsPerMonth/"+dId+"/"+month+"/"+year,{
		method: 'GET',
		headers: {
			"Content-Type": "application/json",
		},
	})
	.then((res)=>res.json())
	.then((students)=>{
		console.log("successfully fetched all data", students);
		if(students){
			
			var tableBody = document.getElementById("students");
			while(tableBody.firstChild)
				tableBody.removeChild(tableBody.firstChild);
			
			populateAllStudentInTable(students);
		}
	})
	.then((err)=>{
		console.log(err);
		return;
	});
	
}


function sendAttendance(){
	
	var dId = document.getElementById("dId").value;	
	if(dId!=undefined){
		//if month and year is set than send Average Attendance of that month and year
		if(monthValue!="" && yearValue!=""){
			console.log("SENDING AVERAGE ATTENDANCE MAIL TO GUARDIAN FOR PERTICULAR MONTH AND YEAR");
			var api = "/sendMonthlyAverageAttendanceGuardian/"+dId+"/"+monthValue+"/"+yearValue;
			sendMail(api);
		}else{
			console.log("SENDING AVERAGE ATTENDANCE MAIL TO GUARDIAN FOR");
			var api = "/sendAverageAttendanceGuardian/"+dId;
			sendMail(api);
		}
		
	}
}

function sendAlert(){
	
	var dId = document.getElementById("dId").value;	
	var minAttendance = document.getElementById("mailForm")["minAttendance"].value;
	
	if(dId!=undefined){
		
		if(monthValue!="" && yearValue!="" && minAttendance!=""){
			console.log("SENDING ALERT ATTENDANCE MAIL TO GUARDIAN FOR PERTICULAR MONTH AND YEAR");
			var api = "/sendMonthlyAverageAttendanceGuardian/"+dId+"/"+monthValue+"/"+yearValue+"/"+minAttendance;
			sendMail(api);
		}else{
			console.log("SENDING ALERT ATTENDANCE MAIL TO GUARDIAN FOR");
			var api = "/sendAlertMailToGuardian/"+dId+"/"+minAttendance;
			sendMail(api);
		}
		
	}
	
}

function sendMail(api){
	setLoader();
	if(api!=undefined && api!=""){
		
		fetch(path+"/lectureattendance"+api,{
			method: 'GET',
			headers: {
				"Content-Type": "application/json",
			},
		})
		.then((res)=> res.json())
		.then((response)=>{
			console.log(response);
			removeLoader();
		})
		.then((err)=>{
			//console.log(err);
			return;
		});
		
	}
	
}

/*
function sendAlert(){
	var dId = document.getElementById("dId").value;	
	
	var minAttendance = document.getElementById("mailForm")["minAttendance"].value;
	
	if(dId!=undefined || minAttendance!="0" || minAttendance!=undefined){
		
		fetch(path+"/lectureattendance/sendAlertMailToGuardian/"+dId+"/"+minAttendance,{
			method: 'GET',
			headers: {
				"Content-Type": "application/json",
			},
		})
		.then((res))
		.then((response)=>{
			console.log(response);
		})
		.then((err)=>{
			//console.log(err);
			return;
		});
		
	}
	
}*/


