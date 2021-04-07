// In this we are converting Table to PDF File

/* <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.22/pdfmake.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script>

*/

function exportToDocument(tableId, fileName){
	exportToCsv(tableId, fileName);
}

function exportToPdf(id, fileName){

	//Convert Table to PDF.
        html2canvas(document.getElementById(id), {
			
            onrendered: function (canvas) {
                var data = canvas.toDataURL();
                var docDefinition = {
                    content: [{
                        image: data,
                        width: 500
                    }]
                };

				if(fileName==undefined || fileName=="")
					fileName+="student";

               pdfMake.createPdf(docDefinition).download(fileName+".pdf");
 
            }
        });
	//testingForCSV(tableId);
	
}

function exportToCsv(tableId, fileName){
	
	var html = document.getElementById(tableId).outerHTML;
	if(fileName==undefined || fileName=="")
		fileName = "exportedDocument";
	export_table_to_csv(html, fileName+".csv");
}



function export_table_to_csv(html, filename) {
	var csv = [];
	var rows = document.querySelectorAll("table tr");
	
    for (var i = 0; i < rows.length; i++) {
		var row = [], cols = rows[i].querySelectorAll("td, th");
		
        for (var j = 0; j < cols.length; j++) 
            row.push(cols[j].innerText);
        
		csv.push(row.join(","));		
	}

    // Download CSV
    download_csv(csv.join("\n"), filename);
}




function download_csv(csv, filename) {
    var csvFile;
    var downloadLink;

    // CSV FILE
    csvFile = new Blob([csv], {type: "text/csv"});

    // Download link
    downloadLink = document.createElement("a");

    // File name
    downloadLink.download = filename;

    // We have to create a link to the file
    downloadLink.href = window.URL.createObjectURL(csvFile);

    // Make sure that the link is not displayed
    downloadLink.style.display = "none";

    // Add the link to your DOM
    document.body.appendChild(downloadLink);

    // Lanzamos
    downloadLink.click();
}




