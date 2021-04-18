//http://localhost:8080/lectureattendance/getStudentMonthlyReport/45

//var path = "http://localhost:8080";

function onLoadPopulate(){
	getAllStudentsMonthlyReportWithAPI();
}


function getAllStudentsMonthlyReportWithAPI(){
	
	fetch(path+"/lectureattendance/getStudentMonthlyReport/44",{
		method: 'GET',
		headers: {
			"Content-Type": "application/json",
		},
	})
	.then((res)=>res.json())
	.then((monthlyReports)=>{
		console.log("successfully fetched all data", monthlyReports);
		if(monthlyReports){
			populateAllMonthlyReportsInTable(monthlyReports);
			monthlyAttendanceChart(monthlyReports);
		}
	})
	.then((err)=>{
		//console.log(err);
		return;
	});
	
}

function populateAllMonthlyReportsInTable(monthlyReports){
	
	var tableBody = document.getElementById("monthlyReport");
	
	for(var index = 0; index < monthlyReports.length; index++){
		
		tableBody.appendChild( 
			createRowMonthlyReportTable(index, monthlyReports[index])
			);
	}

}

function createRowMonthlyReportTable(index, monthReport){
	
	var tr = document.createElement("tr");
	
	var tdSrNo = document.createElement("td");
	tdSrNo.innerHTML = index+1;
	tr.appendChild(tdSrNo);
	
	var tdMonth = document.createElement("td");
	tdMonth.innerHTML = monthReport.month;
	tr.appendChild(tdMonth);
	
	var tdYear = document.createElement("td");
	tdYear.innerHTML = monthReport.year;
	tr.appendChild(tdYear);
	
	var tdLectureAttended = document.createElement("td");
	tdLectureAttended.innerHTML = monthReport.totalLecturesAttended;
	tr.appendChild(tdLectureAttended);
	
	var tdLecturesTaken = document.createElement("td");
	tdLecturesTaken.innerHTML = monthReport.totalLecturesTaken;
	tr.appendChild(tdLecturesTaken);
	
	var tdAverageAttendance = document.createElement("td");
	tdAverageAttendance.innerHTML = monthReport.averageAttendance+"%";
	tr.appendChild(tdAverageAttendance);
	
	return tr;
}


function saveTable(tableId){
	exportToDocument(tableId);
}


