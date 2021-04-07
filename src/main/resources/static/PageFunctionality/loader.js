function setLoader(){
	const modal = document.getElementById("loader");
	modal.style.display = 'flex';
}

function removeLoader(){
	const modal = document.getElementById("loader");
	modal.style.display = 'none';
}


	var modalContainer = document.createElement("div");
	modalContainer.classList.add("modal-container");
	modalContainer.id="loader";
	
	var modalContent = document.createElement("div");
	modalContent.classList.add("modal-content");
	
	var h1 = document.createElement("h1");
	h1.classList.add("modal-title");
	h1.innerHTML = "Wait for response";
	modalContent.appendChild(h1);
	
	var span = document.createElement("span");
	span.classList.add("modal-loader");
	modalContent.appendChild(span);
	
	var p = document.createElement("p");
	p.classList.add("modal-msg");
	p.innerHTML = "System is processing request!!";
	modalContent.appendChild(p);
	
	modalContainer.appendChild(modalContent);
	
	document.getElementsByTagName("body")[0].appendChild(modalContainer);

	const modal = document.querySelector('.modal-container');
	modal.style.display = 'none';
