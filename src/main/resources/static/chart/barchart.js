function populateChart() {
	var chart = new CanvasJS.Chart("chartContainer", {
		title:{
			text: "My First Chart in CanvasJS"              
		},
		data: [              
		{
			// Change type to "doughnut", "line", "splineArea", etc.
			type: "column",
			dataPoints: [
				{ label: "apple",  y: 10  },
				{ label: "orange", y: 15  },
				{ label: "banana", y: 25  },
				{ label: "mango",  y: 30  },
				{ label: "grape",  y: 28  }
			]
		}
		]
	});
	chart.render();
	
    var chart1 = new CanvasJS.Chart("chartContainer1", {

      theme: "light2",
            
      title:{
        text: "Fruits sold in First & Second Quarter"              
      },

      data: [  //array of dataSeries     
      { //dataSeries - first quarter
 /*** Change type "column" to "bar", "area", "line" or "pie"***/        
       type: "column",
       name: "First Quarter",
       showInLegend: true,
       dataPoints: [
       { label: "banana", y: 58 },
       { label: "orange", y: 69 },
       { label: "apple", y: 80 },                                    
       { label: "mango", y: 74 },
       { label: "grape", y: 64 }
       ]
     },

     { //dataSeries - second quarter

      type: "column",
      name: "Second Quarter", 
      showInLegend: true,               
      dataPoints: [
      { label: "banana", y: 63 },
      { label: "orange", y: 73 },
      { label: "apple", y: 88 },                                    
      { label: "mango", y: 77 },
      { label: "grape", y: 60 }
      ]
    }
    ],
 /** Set axisY properties here*/
    axisY:{
      prefix: "$",
      suffix: "K"
    }    
  });

chart1.render();

	
}

function monthlyAttendanceChart(monthlyAttendanceData){

	var monthlyAttended = new Array();
	var monthlyTaken =  new Array();
	var monthlyAverage =  new Array();

	for(var index = 0; index<monthlyAttendanceData.length; index++){
		
		
		var data = monthlyAttendanceData[index];
		
		monthlyAttended.push(
			{
				label: 	data.month+"-"+data.year,
				y: parseInt( data.totalLecturesAttended)
			}
		);
		
		monthlyTaken.push(
			{
				label: 	data.month+"-"+data.year,
				y: parseInt(data.totalLecturesTaken)
			}
		);
		
		monthlyAverage.push(
			{
				label: data.month+"-"+data.year,
				y: parseInt( data.averageAttendance)
			}
		);
	}
	console.log("monthlyTaken", monthlyTaken);
	console.log("monthlyAttended", monthlyAttended);
	console.log("monhtlyAverage", monthlyAverage);
	comaprisonChart(monthlyAttended, monthlyTaken);
	averageAttendanceChart(monthlyAverage);		
	
}

function averageAttendanceChart(monthlyAverageData){
	var monthlyAverage = new CanvasJS.Chart("monthlyAverage", {
		title:{
			text: "Average Attendance Student Per Month"              
		},
		data: [              
		{
			// Change type to "doughnut", "line", "splineArea", etc.
			type: "column",
			dataPoints: monthlyAverageData
		}
		]
	});
	monthlyAverage.render();
}

function comaprisonChart(monthlyAttendedData, monthlyTakenData){

	var comaprisonChart = new CanvasJS.Chart("comaprisonChart", {

      theme: "light2",
            
      title:{
        text: "Lectures Attended Student & Lectures Taken Faculty"              
      },

      data: [  //array of dataSeries     
      { //dataSeries - first quarter
 /*** Change type "column" to "bar", "area", "line" or "pie"***/        
       type: "column",
       name: "Lectures Attended",
       showInLegend: true,
       dataPoints: monthlyAttendedData
     },

     { //dataSeries - second quarter

      type: "column",
      name: "Lectures Taken", 
      showInLegend: true,               
      dataPoints: monthlyTakenData
    }
    ],
 /** Set axisY properties here
    axisY:{
      prefix: "$",
      suffix: "K"
    }    */
  });

	comaprisonChart.render();
}