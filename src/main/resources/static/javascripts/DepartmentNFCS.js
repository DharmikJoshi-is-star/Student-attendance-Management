//var path = "http://localhost:8080";

var nfcAssigned = 0;
var nfcUnassigned = 0;
var pageSize;
var currentPage; 
var dId;

function onLoadPopulate(){
	dId = document.getElementById("dId").value;	
	pageSize = document.getElementById("pageSize").value;	
	currentPage = document.getElementById("currentPage").value;	
	console.log(dId);
	if(dId!=undefined){
		getAllRFIDs(dId);
		
		getAllRFIDsByPage(dId, currentPage, pageSize);
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
		.then((nfcResponse)=>{
			if(nfcResponse!=undefined || nfcResponse!=null){
				
				var nfcData = [nfcResponse];
				populateAllRFIDInTable(nfcData);
				//var a = document.createElement("a");
				//a.href = path+"/rfid?dId="+dId;
				//a.click();
				
				success();
			}else{
				error();
			}
			
		})
		.then((error)=>{
			console.log("Error: ",error);
		});
		
	}
	
}

function getAllRFIDsByPage(dId, currentPage, pageSize){

    var tableData = document.getElementById("rfids");
	console.log(tableData.hasChildNodes());
	tableData.innerHTML = "";
		
	console.log("/rfid/getAllTokens/"+dId+"/"+currentPage+"/"+pageSize);
	
	fetch(path+"/rfid/getAllTokens/"+dId+"/"+currentPage+"/"+pageSize,{
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

function getAllRFIDs(dId){
	console.log("/rfid/getAllTokens/");
	
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
			//populateAllRFIDInTable(rfids);
			for(var index = 0; index < rfids.length; index++){
		
				var rfid = rfids[index];
			
				if(rfid.student==null && rfid.faculty==null){
					console.log(nfcUnassigned);
					nfcUnassigned++;
					
				}else if(rfid.student!=null){
					console.log(nfcAssigned);
					nfcAssigned++;
					
				}else if(rfid.faculty!=null){
					console.log(nfcAssigned);
					nfcAssigned++;
					
				}
	
				
			}
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
			createRowForRFIDTable(tableBody.childNodes.length, rfids[index])
			);
	}
	
	document.getElementById("nfcAssigned").innerHTML = nfcAssigned;
	document.getElementById("nfcUnassigned").innerHTML = nfcUnassigned;
	setPrevNextButton();
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
		//nfcUnassigned++;
		tdAssigned.innerHTML = "UNASSIGNED";
	}else if(rfid.student!=null){
		//nfcAssigned++;
		tdAssigned.innerHTML = "ASSIGNED TO STUDENT";
	}else if(rfid.faculty!=null){
		//nfcAssigned++;
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


function setPrevNextButton(){
	
	console.log("setPrevNextButton");
	
	console.log(currentPage);
	
	if(parseInt(currentPage)<=1){
		console.log("prev is disabled");
		
		document.getElementById("prev").disabled = true ;
		
	}else{
		
		console.log("prev is active");
		document.getElementById("prev").disabled = false ;
		
	}
	
	var tableBody = document.getElementById("rfids");
	
	console.log("tableBody.childNodes.length", tableBody);
	console.log(tableBody.qu);
	console.log(tableBody.childNodes.length);
	console.log(parseInt(pageSize));
	
	
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
	getAllRFIDsByPage(dId, currentPage, pageSize);
	setPrevNextButton();
}

function prevPage(){
	currentPage--;
	getAllRFIDsByPage(dId, currentPage, pageSize);
	setPrevNextButton();
}



