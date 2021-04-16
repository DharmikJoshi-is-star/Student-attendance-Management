function confirm(){
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
		
		return true;
    } else {
      swal("Cancelled", "Your imaginary file is safe :)", "error");
		return false;
    }
	});
}

function success(){
	swal("Success!", "Value Added Successfully! :)", "success");
}

function error(){
	swal("Error", "Error While Inserting Value to System! :(", "error");
}

function noresponse(){
	
}