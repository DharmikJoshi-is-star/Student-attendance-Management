//var path = "http://localhost:8080";

function onLoadPopulate(){
var dId = document.getElementById("dId").value;	
	
	if(dId!=undefined){	
	getDepartmentTimeTable(dId);
	}
}

function getDepartmentTimeTable(dId){
	
	fetch(path+"/department/getLecturesTimeTable/"+dId,{
		method: 'GET',
		headers: {
			"Content-Type": "application/json",
		},
	})
	.then((res)=>res.json())
	.then((timetable)=>{
		console.log("successfully fetched all data", timetable);
		if(timetable){
			setTimeTable(timetable);
		}
	})
	.then((err)=>{
		//console.log(err);
		return;
	});
	
}

function setTimeTable(timetable){
	
	var days = ["monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"];
	
	for(var day = 0; day < timetable.length; day++){
		
		setLecturesOnTable(timetable[day], days[day]);
	}
}

function setLecturesOnTable(lectures, tableId){
	
	var tableBody = document.getElementById(tableId);
	
	for(var index = 0; index < lectures.length; index++){
		
		tableBody.appendChild( 
			createRowForLectureTable(index, lectures[index])
			);
	}
	
}


function setLecturesOnTable(lectures, tableId){
	
	var tableBody = document.getElementById(tableId);
	
	for(var index = 0; index < lectures.length; index++){
		
		tableBody.appendChild( 
			createRowForLectureTable(lectures[index])
			);
	}
	
}


function createRowForLectureTable(lecture){
	
	console.log(lecture);
	
	var tr = document.createElement("tr");
	
	var tdLectureNumber = document.createElement("td");
	tdLectureNumber.innerHTML = lecture.lectureNumber;
	tr.appendChild(tdLectureNumber);
	
	
	var tdTiming = document.createElement("td");
	tdTiming.innerHTML = lecture.startTime+"-"+lecture.endTime;
	tr.appendChild(tdTiming);
	
	var tdLec = document.createElement("td");
	tdLec.innerHTML = lecture.subject;
	tr.appendChild(tdLec);
	
	var tdFaculty = document.createElement("td");
	tdFaculty.innerHTML = lecture.faculty;
	tr.appendChild(tdFaculty);
	
	var tdOpr = document.createElement("td");
	var deleteAnchor = document.createElement("a");
	var deleteIcon = document.createElement("i");
	deleteIcon.classList.add("icon-remove");
	deleteIcon.style.color = "red";
	deleteAnchor.href = "#";
	deleteAnchor.addEventListener("click",()=>{
		console.log("DELETE CLICKED");
		deleteLecture(lecture.id);
	})
	
	deleteAnchor.appendChild(deleteIcon);
	tdOpr.appendChild(deleteAnchor);
	tr.appendChild(tdOpr);
	
	
	return tr;
}
