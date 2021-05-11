//var path = "http://localhost:8080";

subjects = [];
faculties = [];
classes=[];
function onLoadCreateForms(){
	var dId = document.getElementById("dId").value;	
	console.log(dId);
	if(dId!=undefined){
		getFacultiesWithAPI(dId);
	}
	

}

function getFacultiesWithAPI(dId){
	
	fetch(path+"/department/getFacultiesDepartmentDetails/"+dId,{
		method: 'GET',
		headers: {
			"Content-Type": "application/json",
		},
	})
	.then((res)=>res.json())
	.then((res)=>{
		console.log("successfully fetched all data", res);
		if(res){
			faculties = res;
			getSubjectWithAPI(dId);
		}
	})
	.then((err)=>{
		//console.log(err);
		return;
	});
	
	
}

function getSubjectWithAPI(dId){

	fetch(path+"/department/getSubjectsDepartmentDetails/"+dId,{
		method: 'GET',
		headers: {
			"Content-Type": "application/json",
		},
	})
	.then((res)=>res.json())
	.then((res)=>{
		console.log("successfully fetched all data", res);
		if(res!=undefined){
			subjects = res;
			getAllClassesAPI(dId);
		}
	})
	.then((err)=>{
		//console.log(err);
		return;
	});
	
}

function getAllClassesAPI(dId){

	fetch(path+"/class/getAllClasses/"+dId,{
		method: 'GET',
		headers: {
			"Content-Type": "application/json",
		},
	})
	.then((res)=>res.json())
	.then((res)=>{
		console.log("successfully fetched all data", res);
		if(res!=undefined){
			classes = res;
			openLectureForm();
		}
	})
	.then((err)=>{
		//console.log(err);
		return;
	});
	
}




function openLectureForm(){
	
	createLectureForm("MONDAY");
	createLectureForm("TUESDAY");
	createLectureForm("WEDNESDAY");
	createLectureForm("THURSDAY");
	createLectureForm("FRIDAY");
	createLectureForm("SATURDAY");
	createLectureForm("SUNDAY");
	
}

function createLectureForm(day){
	
	console.log(subjects);
	console.log(typeof subjects);
	
	var modal = document.createElement("div");
	modal.classList.add("modal");
	modal.classList.add("hide");
	modal.id = day+"Form";
	
	var modalHeader = document.createElement("div");
	modalHeader.classList.add("modal-header");
	
	var modalCloseButton = document.createElement("button");
	modalCloseButton.classList.add("close");
	modalCloseButton.innerHTML = "x";
	modalCloseButton.setAttribute("data-dismiss", "modal");
	
	modalHeader.append(modalCloseButton);
	
	var h3 = document.createElement("h3");
	h3.innerHTML = "Lecture Form";
	modalHeader.append(h3);

	modal.append(modalHeader);
	
	var modalBody = document.createElement("div");
	modalBody.classList.add("modal-body");
	
	var widgetTitle = document.createElement("div");
	widgetTitle.classList.add("widget-title");
	
	var span = document.createElement("span");
	span.classList.add("icon");
	
	var icon = document.createElement("icon-align-justify");
	
	span.append(icon);
	widgetTitle.append(span);
	
	var h5 = document.createElement("h5");
	h5.innerHTML = day+" Lecture-info";
	
	widgetTitle.append(h5);

	modalBody.append(widgetTitle);
	
	
	var widgetContent = document.createElement("div");
	widgetContent.classList.add("widget-content");
	widgetContent.classList.add("nopadding");
	 
	var form = document.createElement("form");
	form.id = day+"LectureForm";
	form.method = "get";
	form.classList.add("form-horizontal");
	
	
	var lectureNumberInputControlGroup = document.createElement("div");
	lectureNumberInputControlGroup.classList.add("control-group");
	
	var labelLecture = document.createElement("label");
	labelLecture.classList.add("control-label");
	labelLecture.innerHTML = "Lecture Number";
	
	lectureNumberInputControlGroup.append(labelLecture);
	
	var lectureNumberControl = document.createElement("div");
	lectureNumberControl.classList.add("controls");
	
	var lectureInput = document.createElement("input");
	lectureInput.type = "text";
	lectureInput.id = "lectureNumber";
	lectureInput.name = "lectureNumber";
	lectureInput.pattern = "[0-9]{1,2}";
	
	lectureNumberControl.append(lectureInput);
	
	lectureNumberInputControlGroup.append(lectureNumberControl);
	
	form.append(lectureNumberInputControlGroup);
	
	
	//subject select - input start
	var selectSubjectControlGroup = document.createElement("div");
	selectSubjectControlGroup.classList.add("control-group");
	
	var labelSubject = document.createElement("label");
	labelSubject.classList.add("control-label");
	labelSubject.innerHTML = "Select Subject";
	
	selectSubjectControlGroup.append(labelSubject);
	
	//For select subject start
	var subjectControl = document.createElement("div");
	subjectControl.classList.add("controls");
	
	var selectSubject = document.createElement("select");
	selectSubject.id = "subject";
	selectSubject.name = "subject";
	
	for(var idx=0; idx<subjects.length; idx++){
		console.log(subjects[idx]);
		var option = document.createElement("option");
		option.innerHTML = subjects[idx].name;
		option.value = subjects[idx].id;
		selectSubject.append(option);
		
	}
	
	subjectControl.append(selectSubject);
	
	selectSubjectControlGroup.append(subjectControl);
	
	form.append(selectSubjectControlGroup);
	
	//For select subject End
	//subject select input end
	
	
	//faculty select - input start
	var selectFacultyControlGroup = document.createElement("div");
	selectFacultyControlGroup.classList.add("control-group");
	
	var labelFaculty = document.createElement("label");
	labelFaculty.classList.add("control-label");
	labelFaculty.innerHTML = "Select Faculty";
	
	selectFacultyControlGroup.append(labelFaculty);
	
	//For select faculty start
	var facultyControl = document.createElement("div");
	facultyControl.classList.add("controls");
	
	var selectFaculty = document.createElement("select");
	selectFaculty.id = "faculty";
	selectFaculty.name = "faculty";
	
	for(var idx=0; idx<faculties.length; idx++){
		
		var option = document.createElement("option");
		option.innerHTML = faculties[idx].name;
		option.value = faculties[idx].id;
		selectFaculty.append(option);
		
	}
	
	facultyControl.append(selectFaculty);
	
	selectFacultyControlGroup.append(facultyControl);
	
	form.append(selectFacultyControlGroup);
	
	//For select faculty End
	//faculty select input end
	
	
	//Class select - input start
	var selectClassControlGroup = document.createElement("div");
	selectClassControlGroup.classList.add("control-group");
	
	var labelClass = document.createElement("label");
	labelClass.classList.add("control-label");
	labelClass.innerHTML = "Select Class";
	
	selectClassControlGroup.append(labelClass);
	
	//For select Class start
	var classControl = document.createElement("div");
	classControl.classList.add("controls");
	
	var selectClass = document.createElement("select");
	selectClass.id = "class";
	selectClass.name = "class";
	
	for(var idx=0; idx<classes.length; idx++){
		
		var option = document.createElement("option");
		option.innerHTML = classes[idx].classNo;
		option.value = classes[idx].id;
		selectClass.append(option);
		
	}
	
	classControl.append(selectClass);
	
	selectClassControlGroup.append(classControl);
	
	form.append(selectClassControlGroup);
	
	//For select Class  End
	//Class select input end
	
	
	
	//Start time input - start
	var inputStartTimeControlGroup = document.createElement("div");
	inputStartTimeControlGroup.classList.add("control-group");
	
	var labelStartTime = document.createElement("label");
	labelStartTime.classList.add("control-label");
	labelStartTime.innerHTML = "Select Start-Time";
	
	inputStartTimeControlGroup.append(labelStartTime);
	
	var startTimeControl = document.createElement("div");
	startTimeControl.classList.add("controls");
	
	var inputStartTime = document.createElement("input");
	inputStartTime.id = "startTime";
	inputStartTime.name = "startTime";
	inputStartTime.type = "time";
	inputStartTime.required = true;
	inputStartTime.min="09:00";
	inputStartTime.max="18:00"
	
	startTimeControl.append(inputStartTime);
	
	inputStartTimeControlGroup.append(startTimeControl);
	
	form.append(inputStartTimeControlGroup);
	
	//Start time input - end
	
	
	//End time input - start
	var inputEndTimeControlGroup = document.createElement("div");
	inputEndTimeControlGroup.classList.add("control-group");
	
	var labelEndTime = document.createElement("label");
	labelEndTime.classList.add("control-label");
	labelEndTime.innerHTML = "Select End-Time";
	
	inputEndTimeControlGroup.append(labelEndTime);
	
	var endTimeControl = document.createElement("div");
	endTimeControl.classList.add("controls");
	
	var inputEndTime = document.createElement("input");
	inputEndTime.id = "endTime";
	inputEndTime.name = "endTime";
	inputEndTime.type = "time";
	inputEndTime.required = true;
	inputEndTime.min="09:00";
	inputEndTime.max="18:00"
	
	endTimeControl.append(inputEndTime);
	
	inputEndTimeControlGroup.append(endTimeControl);
	
	form.append(inputEndTimeControlGroup);
	
	//End time input - end
	
	widgetContent.append(form);

	modalBody.append(widgetContent);
	
	modal.append(modalBody);
	
	var modalFooter = document.createElement("div");
	modalFooter.classList.add("modal-footer");


	var modalAddButton = document.createElement("a");
	modalAddButton.classList.add("btn");
	modalAddButton.classList.add("btn-primary");
	modalAddButton.innerHTML = "Add";
	modalAddButton.setAttribute("data-dismiss", "modal");
	
	modalAddButton.addEventListener("click",()=>{
		console.log("ADD CLICKED");
		addLecture(day);
	});
	modalFooter.append(modalAddButton);
	
	var modalCancelButton = document.createElement("a");
	modalCancelButton.classList.add("btn");
	modalCancelButton.innerHTML = "Cancel";
	modalCancelButton.setAttribute("data-dismiss", "modal");
	modalFooter.append(modalCancelButton);
	
	modal.append(modalFooter);
	
	console.log(modal);
	
	document.getElementById("widgetMonday").append(modal);
	
	/*
	var modalAnchor = document.createElement("a");
	modalAnchor.href = "#"+day+"Form";
	modalAnchor.setAttribute("data-toggle", "modal");
	modalAnchor.click();
	*/
}


function addLecture(day){
	
	var dId = document.getElementById("dId").value;	
	
	var lectureForm = document.getElementById(day+"LectureForm");
	
	var lectureNumber = lectureForm["lectureNumber"].value ;
	var subject = lectureForm["subject"].value ;
	var faculty = lectureForm["faculty"].value ;
	var classObj = lectureForm["class"].value ;
	var startTime = lectureForm["startTime"].value ;
	var endTime = lectureForm["endTime"].value ;
	
	
	if(lectureNumber!="" && subject!="" && faculty!="" && classObj!="" && startTime!="" && endTime!=""){
		console.log(lectureNumber);
		console.log(subject);
		console.log(faculty);
		console.log(classObj);
		console.log(startTime);
		console.log(endTime);
		
		var lecture = {
			day: day,
			lectureNumber: lectureNumber,
			startTime: startTime,
			endTime: endTime
		};
	
		saveLecture(dId, lecture, classObj, dId, faculty, subject );
		
	}
	
}


function saveLecture(dId, lecture, classId, departmentId, facultyId, subjectId ){
	
	if(dId!=undefined){
	
		fetch(path+"/lecture/addLecture/"+classId+"/"+departmentId+"/"+facultyId+"/"+subjectId,{
			
		 method: "POST", 
		 headers: {
		      "Content-Type": "application/json",
		    },
		 body: JSON.stringify(lecture),
		
		})
		.then((response)=> response.json())
		.then((response)=>{
			var a = document.createElement("a");
			a.href = path+"/departmentTimeTable";
			a.click();
		})
		.then((error)=>{
			//console.log("Error: ",error);
		});
		
	}
	
}

function deleteLecture(lectureId){
	swal({
		title: "Are you sure?",
		text: "You will not be able to recover this Data!",
		type: "warning",
		showCancelButton: true,
		confirmButtonColor: '#DD6B55',
		confirmButtonText: 'Yes, delete it!',
		cancelButtonText: "No, cancel plx!",
		closeOnConfirm: false,
		closeOnCancel: false
	},
	
	function(isConfirm){
    if (isConfirm){
		swal("Deleted!", "Your file has been deleted!", "success");
		deleteLectureAPI(lectureId);
		return true;
    } else {
      swal("Cancelled", "Your imaginary file is safe :)", "error");
		return false;
    }
	});
}

function deleteLectureAPI(lectureId){
	
	var dId = document.getElementById("dId").value;	
	

	
	if(dId!=undefined && lectureId!=undefined){
		
		fetch(path+"/lecture/deleteLecture/"+lectureId,{
			
		 method: "DELETE", 
		 headers: {
		      "Content-Type": "application/json",
		    },
		})
		.then((response)=> response)
		.then((response)=>{
			var a = document.createElement("a");
			a.href = path+"/timeTableDepartment?dId="+dId;
			a.click();
		})
		.then((error)=>{
			console.log("Error: ",error);
		});
		
	}
}
