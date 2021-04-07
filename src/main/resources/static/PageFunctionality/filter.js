function filter(text, tableId ) {
  // Declare variables
  var input, filter, table, tr, td, i, txtValue;
  input = document.getElementById(text);
  filter = input.value.toUpperCase();
  table = document.getElementById(tableId);
  tr = table.getElementsByTagName("tr");

  // Loop through all table rows, and hide those who don't match the search query
  for (i = 0; i < tr.length; i++) {

	var flag = 0;

	if(tr[i].getElementsByTagName("th").length!=0)
		flag = 1;

	for(j=0; j<tr[i].getElementsByTagName("td").length; j++){
		
		td = tr[i].getElementsByTagName("td")[j];
		if (td) {
	      txtValue = td.textContent || td.innerText;
		
	  	  if (txtValue.toUpperCase().indexOf(filter) > -1) {
	        	flag=1;//tr[i].style.display = "";
				
				break;
	      } else {
	        	flag=0;//tr[i].style.display = "none";
	      }
	
		}	
    }
	
	if(flag==0){
			tr[i].style.display = "none";
	}else{
			tr[i].style.display = "";
	}
		

  }
}