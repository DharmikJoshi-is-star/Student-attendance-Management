function populateAreaChart(){
	
    var chart = new CanvasJS.Chart("areaChartContainer",
    {
      title: {
        text: "Monthly Downloads"
      },
	   axisX:{
        valueFormatString: "DD MMM" ,
        //interval: 1,
        intervalType: "Date"

      },
      axisY: {
        title: "Student Count"
      },
        data: [
      {
        type: "area",
        dataPoints: [//array

        { x: new Date(2012, 00, 1), y: 2600 },
        { x: new Date(2012, 01, 2), y: 3800 },
        { x: new Date(2012, 02, 3), y: 4300 },
        { x: new Date(2012, 03, 4), y: 2900 },
        { x: new Date(2012, 04, 5), y: 4100 },
        { x: new Date(2012, 05, 6), y: 4500 },
        { x: new Date(2012, 06, 7), y: 8600 },
        { x: new Date(2012, 07, 8), y: 6400 },
        { x: new Date(2012, 08, 9), y: 5300 },
        { x: new Date(2012, 09, 10), y: 6000 }
        ]
      }
      ]
    });

    chart.render();
  
}

function populateOneWeekChart(oneWeekData){
	
	console.log(new Date(2012, 09, 1));
	var areaChartData =[];
	
	for(date in oneWeekData){
		
		areaChartData.push({
			x: new Date(date),
			y: oneWeekData[date]
		});
		
	}
	oneWeekAreaChart(areaChartData);
}

function oneWeekAreaChart(areaChartData){
	console.log(areaChartData);
	
	

    var chart = new CanvasJS.Chart("oneWeekAttendanceChart",
    {
      title: {
        text: "Last 7 days Attendance Chart"
      },
	   axisX:{
        valueFormatString: "DD MMM" ,
        intervalType: "Date"

      },
      axisY: {
        title: "Student Count"
      },
        data: [
      {
        type: "area",
        dataPoints:areaChartData
      }
      ]
    });

    chart.render();
  
}