//http://localhost:8080/lectureattendance/getStudentDetailedAttendanceOfSubject/44/7

//var path = "http://localhost:8080";
var sId;
var subId;
var currentPage;
var pageSize;

function onLoadPopulate(){
	
	sId = document.getElementById("sId").value;
	subId = document.getElementById("subId").value;
	currentPage = document.getElementById("currentPage").value;
	pageSize = document.getElementById("pageSize").value;
	if(sId!=undefined && subId!=undefined && currentPage!=undefined && pageSize!=undefined){
		console.log(sId);
		console.log(subId);
		subjectDetailReport(sId, subId, currentPage, pageSize);
		
	}
	
	//disabled-active
	 //setPrevNextButton();
	
}

function setPrevNextButton(){
	console.log(currentPage);
	
	if(parseInt(currentPage)<=1){
		console.log("prev is disabled");
		
		document.getElementById("prev").disabled = true ;
		
	}else{
		
		console.log("prev is active");
		document.getElementById("prev").disabled = false ;
		
	}
	
	var tableBody = document.getElementById("subjectAttendance");
	
	console.log("tableBody.childNodes.length", tableBody.childNodes.length);
	
	if(tableBody.childNodes.length < parseInt(pageSize)){
		console.log("next is disabled");
		document.getElementById("next").disabled = true ;
		
	}else{
		console.log("next is active");
		document.getElementById("next").disabled = false ;
		
	}
	
}

function nextPage(){
	currentPage++;
	subjectDetailReport(sId, subId, currentPage, pageSize);
	setPrevNextButton();
}

function prevPage(){
	currentPage--;
	subjectDetailReport(sId, subId, currentPage, pageSize);
	setPrevNextButton();
}

function subjectDetailReport(sId, subId, currentPage, pageSize){
	
	var tableBody = document.getElementById("subjectAttendance");
	console.log("tableBody.hasChildNodes() ",tableBody.hasChildNodes());
	console.log(tableBody);
	console.log(tableBody.childNodes.length);
	while(tableBody!=undefined && tableBody.hasChildNodes() && tableBody.childNodes.length>1){
		console.log("tableBody.hasChildNodes() ",tableBody.hasChildNodes());
		tableBody.remove();
	}
		
	
	console.log("getSubjectDetailedAttendance/"+sId+"/"+subId+"/"+currentPage+"/"+pageSize);
	
	fetch(path+"/lectureattendance/getSubjectDetailedAttendance/"+sId+"/"+subId+"/"+currentPage+"/"+pageSize,{
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
setPrevNextButton();
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
