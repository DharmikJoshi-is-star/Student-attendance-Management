//var path = "http://localhost:8080";

function onLoadPopulate(){
	var dId = document.getElementById("dId").value;	
	
	if(dId!=undefined){
		getAverageAttendanceOfStudents(dId);
		getAllNonAssignedRFIDs(dId, "rfids");
	}
	
}

function goToAverageAttendancePage(){
	var dId = document.getElementById("dId").value;	
	
	if(dId!=undefined){
		var a = document.createElement("a");
		a.href = path+"/studentsAverageAttendance?dId="+dId;
		a.click();
	}
}

function getAverageAttendanceOfStudents(dId){
	
	fetch(path+"/student/getAllStudents/"+dId,{
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
	var tr = document.createElement("tr");
	//tr.classList.add("gradeX");
	//if(index%2==1){
	//	tr.classList.add("odd");
	//}else tr.classList.add("even");
	
	var tdSrNo = document.createElement("td");
	//tdSrNo.classList.add("sorting_1");
	tdSrNo.innerHTML = index+1;
	tr.appendChild(tdSrNo);
	
	var tdRollNo = document.createElement("td");
	tdRollNo.innerHTML = student.rollno;
	tr.appendChild(tdRollNo);
	
	var tdName = document.createElement("td");
	tdName.innerHTML = student.name;
	tr.appendChild(tdName);
	
	var tdDeptName = document.createElement("td");
	tdDeptName.innerHTML = student.deptName;
	tr.appendChild(tdDeptName);
	
	var tdGender = document.createElement("td");
	tdGender.innerHTML = student.gender;
	tr.appendChild(tdGender);
	
	var tdCurrentStatus = document.createElement("td");
	var tdCurrentStatusText = document.createElement("p");
	tdCurrentStatusText.innerText = student.currentStatus;
	//tdCurrentStatusText.classList.add("alert");
	if(student.currentStatus == "IN"){
		tdCurrentStatusText.classList.add("alert-success");
	}
	if(student.currentStatus == "OUT"){
		tdCurrentStatusText.classList.add("alert-error");
	}
	
	tdCurrentStatus.appendChild(tdCurrentStatusText);
	
	tr.appendChild(tdCurrentStatus);
	
	var tdDetails = document.createElement("td");
	var tdDetailsExpand = document.createElement("a");
	tdDetailsExpand.classList.add("btn");
	tdDetailsExpand.classList.add("btn-success");
	tdDetailsExpand.classList.add("btn-small");
	tdDetailsExpand.innerHTML = "Expand";
	tdDetailsExpand.href = "/departmentStudentDetailsProcess?sId="+student.id;
	tdDetails.appendChild(tdDetailsExpand);
	
	tr.appendChild(tdDetails);
	
	var tdOpr = document.createElement("td");
	
	var tdEdit = document.createElement("button");
	tdEdit.classList.add("btn");
	tdEdit.classList.add("btn-info");
	tdEdit.classList.add("btn-small");
	tdEdit.innerHTML = "Edit";
	tdEdit.addEventListener("click", ()=>{
		console.log("EDIT Student ");
		editStudentForm(student);
	});
	
	
	tdOpr.appendChild(tdEdit);
	
	var tdSpace = document.createElement("i");
	tdSpace.innerHTML = "  ";
	tdOpr.appendChild(tdSpace);
	
	var tdDelete = document.createElement("button");
	tdDelete.classList.add("btn");
	tdDelete.classList.add("btn-danger");
	tdDelete.classList.add("btn-small");
	tdDelete.innerHTML = "Delete";
	tdDelete.addEventListener("click", ()=>{
		console.log("DELETE FACULTY ");
		deleteStudent(student.id);
	});
	
	tdOpr.appendChild(tdDelete);
	
	tr.appendChild(tdOpr);
	
	return tr;
}

/*
document.getElementById("search").addEventListener("change", ()=>{
	
	filter("search", "studentTable");
	
});

*/