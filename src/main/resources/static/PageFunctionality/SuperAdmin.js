
//var path = "http://localhost:8080";


function onLoadPopulate(){
	
	getDepartmentAdmins();
	
}

function getDepartmentAdmins(){
	
	fetch(path+"/admin/getAllAdmins/",{
		method: 'GET',
		headers: {
			"Content-Type": "application/json",
		},
	})
	.then((res)=>res.json())
	.then((admins)=>{
		console.log("successfully fetched all data", admins);
		if(admins!=undefined){
			populateAdminTable(admins);
		}
	})
	.then((err)=>{
		//console.log(err);
		return;
	});
	
}

function populateAdminTable(admins){
	
	var tableBody = document.getElementById("admins");
	
	for(var index = 0; index < admins.length; index++){
		console.log(admins[index]);
		tableBody.appendChild( 
			createRowAdminTable(index+1, admins[index])
			);
	}
	
}


function createRowAdminTable(srno, admin){

	var tr = document.createElement("tr");
	
	var tdSrno = document.createElement("td");
	tdSrno.innerHTML = srno;
	tr.appendChild(tdSrno);
	
	var tdDepartment = document.createElement("td");
	
	if(admin.department==undefined || admin.department=="" || admin.department==null)
		tdDepartment.innerHTML = "No Department";
	else
		tdDepartment.innerHTML = admin.department.name;
		
	tr.appendChild(tdDepartment);
	
	
	var tdEmail = document.createElement("td");
	if(admin.email!=null) tdEmail.innerHTML = admin.email;
	else tdEmail.innerHTML = "test@gmail.com";
	tr.appendChild(tdEmail);
	
	
	var tdUsername = document.createElement("td");
	tdUsername.innerHTML = admin.username;
	tr.appendChild(tdUsername);
	
	var tdOpr = document.createElement("td");
	
	var tdDelete = document.createElement("button");
	tdDelete.classList.add("btn");
	tdDelete.classList.add("btn-danger");
	tdDelete.classList.add("btn-small");
	tdDelete.innerHTML = "Delete";
	tdDelete.addEventListener("click", ()=>{
		//console.log("DELETE FACULTY ");
		deleteAdmin(admin);
	});
	if(admin.department!=null)
		tdDelete.disabled = true;
	
	
	
	tdOpr.appendChild(tdDelete);
	
	tr.appendChild(tdOpr);
	
	return tr;
}

	
function addAdmin(){
	
	var adminForm = document.getElementById("adminForm");
	
	var username = adminForm["username"].value;
	var password = adminForm["password"].value;
	var email = adminForm["email"].value;
	
	
	console.log(username);
	
	console.log(password);
	if(username!="" && password!=""){
		
		var admin = {
			username: username,
			password: password,
			email: email
		};
		
		saveAdmin(admin);
	}
	
}

function saveAdmin(admin){
	console.log(admin);
	fetch(path+"/admin/addAdmin",{
			
		 method: "POST", 
		 headers: {
		      "Content-Type": "application/json",
		    },
		 body: JSON.stringify(admin),
		
		})
		.then((response)=> response.json())
		.then((response)=>{
			var a = document.createElement("a");
			a.href = path+"/superAdmin";
			a.click();
		})
		.then((error)=>{
			console.log("Error: ",error);
		});
	
}

function deleteAdmin(admin){
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
		console.log(admin);
		console.log(admin.id);
		deleteAdminAPI(admin.id);
		return true;
    } else {
      swal("Cancelled", "Your imaginary file is safe :)", "error");
		return false;
    }
	});
} 



function deleteAdminAPI(adminId){
	
	console.log(adminId);
	
	if(adminId!=undefined){
		
		fetch(path+"/admin/deleteAdmin/"+adminId,{
			
		 method: "DELETE", 
		 headers: {
		      "Content-Type": "application/json",
		    },
		})
		.then((response)=> response)
		.then((response)=>{
			
			var a = document.createElement("a");
			a.href = path+"/superAdmin";
			a.click();
			
		})
		.then((error)=>{
			console.log("Error: ",error);
		});
		
	}
	
}