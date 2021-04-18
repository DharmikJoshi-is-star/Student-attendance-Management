//var path = "http://localhost:8080";


function getAllNonAssignedRFIDs(dId, selectInputId){
	fetch(path+"/rfid/getAllUnassignedTokens/"+dId,{
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
			populateRFID(rfids, selectInputId);
		}
	})
	.then((err)=>{
		//console.log(err);
		return;
	});
}


function populateRFID(rfids, selectInputId){
	
	var select = document.getElementById(selectInputId);
	
	for(var idx=0; idx<rfids.length; idx++){
		console.log(rfids[idx]);
		var option = document.createElement("option");
		option.innerHTML = rfids[idx].token_id;
		option.value = rfids[idx].token_id;
		select.append(option);
		
	}
	
}
