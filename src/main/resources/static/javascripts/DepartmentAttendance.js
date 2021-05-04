//var path = "http://localhost:8080";

function onLoadPopulate(){
	
	var dId = document.getElementById("departmentId").value;
	
	if(dId!=undefined)
	
	getAllDepartmentSubjectWithAPI(dId);
}


function getAllDepartmentSubjectWithAPI(dId){
	
	fetch(path+"/department/getAllDepartmentsSubjects/"+dId,{
		method: 'GET',
		headers: {
			"Content-Type": "application/json",
		},
	})
	.then((res)=>res.json())
	.then((subjects)=>{
		console.log("successfully fetched all data", subjects);
		if(subjects){
			populateSubjectIntoList(subjects);
		}
	})
	.then((err)=>{
		//console.log(err);
		return;
	});
	
}


function populateSubjectIntoList(subjects){
	
	var selectSubject = document.getElementById("subjects");
	
	
	for(var index = 0; index < subjects.length; index++){
		
		var subject = subjects[index];
		var opt = document.createElement("option");
		opt.innerHTML = subject.name;
		opt.value = subject.id;
		selectSubject.appendChild(opt);
	
	}
	
}


function showAttendance(){
	
	var form = document.getElementById("filterForm");
	console.log(form);
	console.log(form["subject"].value);
	console.log(form["date"].value);
	
	if(form["subject"].value!=null && form["date"].value!=null){
		var subject = {
		"id":	form["subject"].value,
		"date": form["date"].value
		};
		setAttendanceOfSubjectOnDate(subject);
	}

}



function setAttendanceOfSubjectOnDate(subject){
	
	var tableHeadRow = document.getElementById("tableHeadRow");

	
	tableHeadRow.innerHTML = "";
	
	fetch(path+"/lectureattendance/getAttendnaceOfSubjectOnDate/"+subject.id+"/"+subject.date,{
		method: 'GET',
		headers: {
			"Content-Type": "application/json",
		},
	})
	.then((res)=>res.json())
	.then((attendances)=>{
		console.log("successfully fetched all data", attendances);
		if(attendances){
			populateAttendanceTable(attendances);
		}else{
			
		}
	})
	.then((err)=>{
		//console.log(err);
		return;
	});
}





function populateAttendanceTable(attendances){
  
	var tableHeadRow = document.getElementById("tableHeadRow");
	
	var rollNo = document.createElement("th");
	rollNo.innerHTML = "Roll No.";
	tableHeadRow.appendChild(rollNo);
	
	var studentName = document.createElement("th");
	studentName.innerHTML = "Student Name";
	tableHeadRow.appendChild(studentName);
	
	if( attendances.length!=0 ){
		console.log(1);
		for(var idx=0;idx<attendances[0].isPresentLectures.length; idx++){
			console.log(2);
			var lec = document.createElement("th");
			console.log(3);
			lec.innerHTML = "Lecture "+(idx+1);
			console.log(4);
			tableHeadRow.appendChild(lec);
			console.log(5);
		}
	}
	
	var tableBody = document.getElementById("attendance");
	
	tableBody.innerHTML = "";
	
	for(var index = 0; index < attendances.length; index++){
		console.log(attendances[index]);
		tableBody.appendChild( 
			createRowForAttendanceTable(attendances[index])
			);
	}

}


function createRowForAttendanceTable(attendance){
	console.log(1);

	var tr = document.createElement("tr");
	
	var tdRollNo = document.createElement("td");
	tdRollNo.innerHTML = attendance.rollNno;
	tr.appendChild(tdRollNo);
	
	var tdName = document.createElement("td");
	tdName.innerHTML = attendance.studentName;
	tr.appendChild(tdName);
	console.log(2);
	for(var idx=0; idx<attendance.isPresentLectures.length; idx++){
		var tdIsPresent = document.createElement("td");
		var checkIcon = document.createElement("i");
		if(attendance.isPresentLectures[idx]){
			checkIcon.classList.add("icon-ok");
		}else{
			checkIcon.classList.add("icon-remove");
		}
	
		tdIsPresent.appendChild(checkIcon);
		
		tr.appendChild(tdIsPresent);
	}
	console.log(3);
	
	return tr;
}

function saveTable(tableId){
	exportToDocument(tableId);
}

