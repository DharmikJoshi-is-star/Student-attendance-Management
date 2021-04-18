//var path = "http://localhost:8080";

function onLoadPopulate(){
	var dId = document.getElementById("dId").value;	
	console.log(dId);
	if(dId!=undefined){
		getAllRFIDs(dId);
	}
}

function addRFID(){
	
	var rfidForm = document.getElementById("rfidForm");
	
	var rfidValue = rfidForm["rfid"].value;

	if(rfidValue!=""){
		var rfid = {
			token_id: rfidValue,
		};
		postRFID(rfid);
	}

}

function postRFID(rfid){
	
	var dId = document.getElementById("dId").value;	
	
	if(dId!=undefined){
		
		fetch(path+"/rfid/addRFIDToken/"+dId,{
			
		 method: "POST", 
		 headers: {
		      "Content-Type": "application/json",
		    },
		 body: JSON.stringify(rfid),
		
		})
		.then((response)=> response.json())
		.then((response)=>{
			var a = document.createElement("a");
			a.href = path+"/rfid?dId="+dId;
			a.click();
		})
		.then((error)=>{
			console.log("Error: ",error);
		});
		
	}
	
}

function getAllRFIDs(dId){
	fetch(path+"/rfid/getAllTokens/"+dId,{
		method: 'GET',
		headers: {
			"Content-Type": "application/json",
		},
	})
	.then((res)=>res.json())
	.then((rfids)=>{
		console.log("successfully fetched all data", rfids);
		if(rfids!=undefined){
			populateAllRFIDInTable(rfids);
		}
	})
	.then((err)=>{
		//console.log(err);
		return;
	});
}

function populateAllRFIDInTable(rfids){
	var tableBody = document.getElementById("rfids");
	
	for(var index = 0; index < rfids.length; index++){
		
		tableBody.appendChild( 
			createRowForRFIDTable(index, rfids[index])
			);
	}
}



function createRowForRFIDTable(index, rfid){
	
	var tr = document.createElement("tr");
	
	var tdSrNo = document.createElement("td");
	tdSrNo.innerHTML = index+1;
	tr.appendChild(tdSrNo);
	
	var tdTokenValue = document.createElement("td");
	tdTokenValue.innerHTML = rfid.token_id;
	tr.appendChild(tdTokenValue);
	
	var tdAssigned = document.createElement("td");
	
	if(rfid.student==null && rfid.faculty==null){
		tdAssigned.innerHTML = "UNASSIGNED";
	}else if(rfid.student!=null){
		tdAssigned.innerHTML = "ASSIGNED TO STUDENT";
	}else if(rfid.faculty!=null){
		tdAssigned.innerHTML = "ASSIGNED TO FACULTY";
	}
	
	tr.appendChild(tdAssigned);
		
	var tdOpr = document.createElement("td");
	var deleteOpr = document.createElement("button");
	deleteOpr.classList.add("btn");
	deleteOpr.classList.add("btn-danger");
	deleteOpr.classList.add("btn-small");
	deleteOpr.innerHTML = "DELETE";
	
	if(rfid.student==null && rfid.faculty==null){
		deleteOpr.innerHTML = "DELETE";
	}else{
		deleteOpr.disabled = true;
	}
	
	deleteOpr.href = "#";
	tdOpr.appendChild(deleteOpr);
	
	tr.appendChild(tdOpr);
	
	return tr;
}





