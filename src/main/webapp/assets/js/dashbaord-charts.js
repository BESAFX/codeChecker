var temperature;
var presets = window.chartColors;
var utils = Samples.utils;
var inputs = {
	min: 20,
	max: 80,
	count: 8,
	decimals: 2,
	continuity: 1
};
function get(name){
	   if(name=(new RegExp('[?&]'+encodeURIComponent(name)+'=([^&]*)')).exec(location.search))
	      return decodeURIComponent(name[1]);
	}
//var ip = "10.50.50.63:9090";
 //var ip = "localhost:9090";
 var ip = $(apiUrl).val();
var urlConnection="http://"+ip+"/api/dashboard/connection";
var urlAlarm="http://"+ip+"/api/dashboard/alarms/"+ $(usernamevalue).val();
var urlRssi="http://"+ip+"/api/dashboard/rssi";
var urlBattery="http://"+ip+"/api/dashboard/battery";
var urlTemp="http://"+ip+"/api/dashboard/temp/"+ $(usernamevalue).val();
var urlConnectionTime = "http://"+ip+"/api/dashboard/rssitime";
var urlArea = "http://"+ip+"/api/dashboard/tempLine";
function updateCharts(code) {
	
	    var urlSummary = "http://"+ip+"/api/ShipmentRTSummaryFromTo/"+ $(usernamevalue).val();
	    var shipmentCode = $(".shipmentsMenu").val();
	    var snzorSN = $(".snzorsMenu").val();
	    if(shipmentCode == ""){
    		showGritter("Shipment code required","Please select the shipment code");
	    	return;
		}
	    if(snzorSN == ""){
	    	snzorSN=null;
	    }
	    var dateFrom =  $(".shipmentsdtp1").val();// $().val();
	    if(dateFrom == ""){
    		showGritter("From date required","Please select the from date");
	    	return;
		}
	    dateFrom = moment(dateFrom).format('YYYY-MM-DD hh:mm:ss');
	    var dateTo =  $(".shipmentsdtp2").val();//$(shipmentsdtp2).val();
	    if(dateTo == ""){
    		showGritter("To date required","Please select the to date");
	    	return;
		}
	    $('#testDiv').css("display", "block");
	    dateTo = moment(dateTo).format('YYYY-MM-DD hh:mm:ss');
	    $('#total-snzorsLoading').css("visibility", "visible");;
		$('#battaryLoading').css("visibility", "visible");;
		$('#tempLoading').css("visibility", "visible");;
		$('#alarmLoading').css("visibility", "visible");;
		$('#connectionLoading').css("visibility", "visible");;
		$('#signalLoading').css("visibility", "visible");;
		$('#areaLoading').css("visibility", "visible");;
		
		
		//totalSnzor
		$.ajax({url: urlConnection, 
			type: "POST",
			data: JSON.stringify({ 
				shipmentId: shipmentCode, 
				fromDate: dateFrom, 
				snzorId: snzorSN,
				toDate: dateTo
			  }) ,
			cache: false, 
			contentType:  'application/json; charset=utf-8',
			complete: function(xhr,status) {
					//$("#myModal").modal('hide');
					//alert(xhr.status);
			},
			success: function(result){
				ajaxTotalSnzor(result);	
			},
			error: function(errorThrown){
				$('#total-snzorsLoading').css("visibility", "hidden");
				
				showGritter("Could not connect to Connection API!","Please refer to the administrator");

			} 
		});

		
		//alarm
		$.ajax({url: urlAlarm, 
			type: "POST",
			data: JSON.stringify({ 
				shipmentId: shipmentCode, 
				fromDate: dateFrom, 
				snzorId: snzorSN,
				toDate: dateTo
			  }) ,
			cache: false, 
			contentType:  'application/json; charset=utf-8',
			complete: function(xhr,status) {
					//$("#myModal").modal('hide');
					//alert(xhr.status);
			},
			success: function(result){
				ajaxAlarm(result);	
			},
			error: function(errorThrown){
				$('#alarmLoading').css("visibility", "hidden");
				
				showGritter("Could not connect to Alarm API!","Please refer to the administrator");

			} 
		});
		////////RSSI
		$.ajax({url: urlRssi, 
			type: "POST",
			data: JSON.stringify({ 
				shipmentId: shipmentCode, 
				fromDate: dateFrom, 
				snzorId: snzorSN,
				toDate: dateTo
			  }) ,
			cache: false, 
			contentType:  'application/json; charset=utf-8',
			complete: function(xhr,status) {
					//$("#myModal").modal('hide');
					//alert(xhr.status);
			},
			success: function(result){
				ajaxRssi(result);	
			},
			error: function(errorThrown){
				$('#signalLoading').css("visibility", "hidden");
				
				showGritter("Could not connect to Signal RSSI API!","Please refer to the administrator");

			} 
		});
		///////battery
		$.ajax({url: urlBattery, 
			type: "POST",
			data: JSON.stringify({ 
				shipmentId: shipmentCode, 
				fromDate: dateFrom, 
				snzorId: snzorSN,
				toDate: dateTo
			  }) ,
			cache: false, 
			contentType:  'application/json; charset=utf-8',
			complete: function(xhr,status) {
					//$("#myModal").modal('hide');
					//alert(xhr.status);
			},
			success: function(result){
				ajaxBattery(result);	
			},
			error: function(errorThrown){
				$('#battaryLoading').css("visibility", "hidden");
				
				showGritter("Could not connect to Battery API!","Please refer to the administrator");

			} 
		});
		
		////Temp
		$.ajax({url: urlTemp, 
			type: "POST",
			data: JSON.stringify({ 
				shipmentId: shipmentCode, 
				fromDate: dateFrom, 
				snzorId: snzorSN,
				toDate: dateTo
			  }) ,
			cache: false, 
			contentType:  'application/json; charset=utf-8',
			complete: function(xhr,status) {
					//$("#myModal").modal('hide');
					//alert(xhr.status);
			},
			success: function(result){
				var tempUnitSrting =$(tempUnit).val();
				if(tempUnitSrting &&tempUnitSrting!="C"){
					result.min=celsiusToFahrenheit(result.min);
					result.max=celsiusToFahrenheit(result.max);
					result.avg=celsiusToFahrenheit(result.avg);
				 }
				ajaxTemp(result);	
			},
			error: function(errorThrown){
				$('#tempLoading').css("visibility", "hidden");
				
				showGritter("Could not connect to Temprature API!","Please refer to the administrator");

			} 
		});
		
		//connection/time
		$.ajax({url: urlConnectionTime, 
			type: "POST",
			data: JSON.stringify({ 
				shipmentId: shipmentCode, 
				fromDate: dateFrom, 
				snzorId: snzorSN,
				toDate: dateTo
			  }) ,
			cache: false, 
			contentType:  'application/json; charset=utf-8',
				complete: function(xhr,status) {
				},
				success: function(result){
					var timezoneSrting =$(timeZone).val();
					if(timezoneSrting && timezoneSrting !="UTC" &&result.length>0){
						for(var i=0;i<result[0].readings.length;i++){
							result[0].readings[i].utcTime=toTimeZone(moment(result[0].readings[i].utcTime,"YYYY-MM-DD HH:mm:ss"),timezoneSrting);
						}
					}
					ajaxSuccessConnection(result,dateFrom,dateTo);	
				},
				error: function(errorThrown){
				
					$('#connectionLoading').css("visibility", "hidden");
					showGritter("Could not connect to  Connection time API!","Please refer to the administrator");
				} 
		});
            
		//temp vs time
		$.ajax({url: urlArea, 
			type: "POST",
			data: JSON.stringify({ 
				shipmentId: shipmentCode, 
				fromDate: dateFrom, 
				snzorId: snzorSN,
				toDate: dateTo
			  }) ,
			cache: false, 
			contentType:  'application/json; charset=utf-8',
				complete: function(xhr,status) {
				},
				success: function(result){
					var data=result.readings;
					var tempUnitSrting =$(tempUnit).val();
					if(tempUnitSrting &&tempUnitSrting!="C"){
						result.min=celsiusToFahrenheit(result.min);
						result.max=celsiusToFahrenheit(result.max);
						
						for(var i=0;i<data.length;i++){
							data[i].min=celsiusToFahrenheit(data[i].min);
							data[i].avg=celsiusToFahrenheit(data[i].avg);
							data[i].max=celsiusToFahrenheit(data[i].max);
						}
					}
					var timezoneSrting =$(timeZone).val();
					if(timezoneSrting && timezoneSrting !="UTC"){
						for(var i=0;i<data.length;i++){
							data[i].utcTime=toTimeZone(moment(data[i].utcTime,"YYYY-MM-DD HH:mm:ss"), timezoneSrting); 
							//toTimeZone(moment(result[0].readings[i].utcTime,"YYYY-MM-DD HH:mm:ss"),timezoneSrting)
						}
					}
					ajaxSuccessArea(result);	
				},
				error: function(errorThrown){
				
					$('#areaLoading').css("visibility", "hidden");
					showGritter("Could not connect to Area API!","Please refer to the administrator");
				} 
		});
               
        }

	function ajaxTotalSnzor(connection){
	
	var non=connection.total - connection.connected;
	
	var doughnutData = [
						{
							value: connection.connected,
							color:"#68dff0"
						},
						{
							value : non,
							color : "#444c57"
						}
					];
	if(connection.status){
		 document.getElementById('rowClassConnected').style.display="block";
		 document.getElementById('rowClassNotConnected').style.display="none";
	 }else{
		 document.getElementById('rowClassConnected').style.display="none";
		 document.getElementById('rowClassNotConnected').style.display="block"; 
	 }
	
	$('#total-snzorsLoading').css("visibility", "hidden");
	 Morris.Donut({
	        element: 'total-snzors',
	        data: [
	          {label: 'Connected', value: connection.connected },
	          {label: 'Non-Connected', value: non }
	        ],
	          colors: ['#68dff0', '#444c57'],
	        formatter: function (y) { return y + "/" + connection.total }
	      });

	}
	function ajaxAlarm(alarm){	
		
		var values=[];
		var label=[];
		var dict=alarm.severities;
		for (var key in dict){
			values.push(dict[key]);
			label.push(key);
		}
	
		
		
	    var config = {
	        type: 'pie',
	        data: {
	            datasets: [{
	                data:values,
	                backgroundColor: [
window.chartColors.red,
window.chartColors.blue,
window.chartColors.green,
window.chartColors.yellow
	                ],
	                label: 'Dataset 1'
	            }],
	            labels:label
	        },
	        options: {
	            responsive: true
	        }
	    }; 
	    $('#alarmLoading').css("visibility", "hidden");
	    var ctx = document.getElementById("myChart").getContext("2d");
	    if(window.myPie){
	    	  window.myPie.destroy();
	    }
	    window.myPie = new Chart(ctx, config);
	 
	  
	}
	
	function ajaxRssi(rssi){
		var canvas = document.getElementById('signalChart');
		if(rssi.min == null){
			rssi.min=-120;
		}
		if(rssi.avg == null){
			rssi.avg=-120;
		}
		if(rssi.max == null){
			rssi.max=-120;
		}
	    var dataSet = {
	        labels: ["", "", "", "", "","", "", "", "", "","", "", "", "", ""],
	        datasets: [
	            {
	                label: "Min",
	                backgroundColor: getColorArray(rssi.min,0),
	                borderColor: window.chartColors.grey,
	                formatter: function (y) { return rssi.min },
	                
	                data: [ 10, 20, 30, 40,50,0,0,0,0,0,0,0,0,0,0]
	            },
	            {
	                label: "Avg",
	                backgroundColor: getColorArray(rssi.avg,5),
	                borderColor: window.chartColors.grey,
	               
	                
	                data: [ 0,0,0,0,0,10, 20, 30, 40,50,0,0,0,0,0]
	            },
	            {
	                label: "Max",
	                backgroundColor: getColorArray(rssi.max,10),
	                borderColor: window.chartColors.grey,
	               
	                
	                data: [0,0,0,0,0,0,0,0,0,0,10, 20, 30, 40,50]
	            }
	        ]
	    };
	    var option = { legend: {
	                       display: true
	                    },
	    scales : { xAxes: [{
	                            
	                            display: false,
	                            scaleLabel: {
	                                display: false,
	                                
	                            }
	                        }] ,
	                    	 yAxes: [{
	                             display: false,
	                             ticks: {
	                                 beginAtZero: true
	                                 
	                             }
	                         }]
	                    }

	    };

	    $('#signalLoading').css("visibility", "hidden");
	    var mySignalChart = Chart.Bar(canvas,{
	    	data:dataSet,
	      options:option
	    });
	}
	function ajaxBattery(battery){
	    var color = Chart.helpers.color;
	    var barChartData = {
	        labels: ["Battery"],
	        datasets: [{
	            label: 'Min battery',
	            backgroundColor: window.chartColors.red,
	            borderColor: window.chartColors.red,
	            borderWidth: 1,
	            data: [
	                   battery.min
	            ]
	        },{
	            label: 'Avg battery',
	            backgroundColor: window.chartColors.blue,
	            borderColor: window.chartColors.blue,
	            borderWidth: 1,
	            data: [
	                   battery.avg
	            ]
	        },{
	            label: 'Max battery',
	            backgroundColor: window.chartColors.orange,
	            borderColor: window.chartColors.orange,
	            borderWidth: 1,
	            data: [
	                   battery.max
	            ]
	        }]

	    };

	    $('#battaryLoading').css("visibility", "hidden");
	     var ctx = document.getElementById("battary").getContext("2d");
	        window.myBar = new Chart(ctx, {
	            type: 'bar',
	            data: barChartData,
	            options: {
	            	 scaleBeginAtZero:false,
	            
	                responsive: true,
	                legend: {
	                    position: 'top',
	                },
	                scales : {
	                	 yAxes: [{
	                         display: true,
	                         ticks: {
	                             beginAtZero: true,
	                             max: 100
	                         }
	                     }]
	                }
	        
	            }
	        });
	}
	
	function ajaxTemp(temperature){
		$('#tempLoading').css("visibility", "hidden");
	    document.getElementById('divCheckbox1').style.display="block";
	    google.charts.load('current', {'packages':['gauge']});
	 
	    google.charts.setOnLoadCallback(function() { drawChart(temperature); });
	    
	    drawMap(false);
	}
function ajaxSuccessConnection(result,dateFrom,dateTo)
{
	var dataset = result; 
	var arrayOfData=[];

	var newseries = {
	        name: '',
	        data: []
	    };
	var list=[];
	for(var k =0; k < dataset.length; k++)
	{
		arrayOfData=[];
		newseries = {
		        name: '',
		        data: []
		    };
		//arrayOfData.push([new Date(dateFrom).getTime(),  parseLong(dataset[k].sn)]);
		for(var i=0;i<dataset[k].readings.length;i++){
			var str=dataset[k].readings[i].utcTime.split(" ");
			var dt=str[0].split("-");
			var time=str[1].split(":");
			var date = new Date(dt[0],dt[1],dt[2],time[0],time[1],time[2]);
			/// test new Date(2012, 11, 2, 19, 30, 0)
			arrayOfData.push([date.getTime(), parseInt(dataset[k].sn)]);
			//			arrayOfData.push([(moment(dataset[k].readings[i].utcTime).format('YYYY-MM-DD h:mm:SS')), parseInt(dataset[k].sn)]);

		}
	//	arrayOfData.push([new Date(dateTo).getTime(),  parseInt(dataset[k].sn)]);
		
		newseries.name= dataset[k].sn;
		newseries.data= arrayOfData;
		list.push(newseries);
	}
	$('#connectionLoading').css("visibility", "hidden");


	
	var chart=Highcharts.chart('connection', {
		title: {
		    text: ''
		},
		xAxis: {
			type: 'datetime',
			 title: {
		            text: 'Connection Time'
		        },
	        labels: {
	            align: 'right',
	            rotation: -30,
	            dateTimeLabelFormats: {
	                	day: '%b %e',
	                    week: '%b %e'
	                    	
	            }
	        }
	},
		chart: {
		        type: 'line',
		        
		        zoomType: 'x',
		        panning: true,
		        panKey: 'shift'
		    },
		    
		    yAxis: {
		        title: {
		            text: 'Snzor'
		        }
		    },
		    legend: {
		        layout: 'vertical',
		        align: 'right',
		        verticalAlign: 'middle'
		    },

		    plotOptions: {
		        series: {
		            marker: {
		                enabled: true,
		                symbol: 'square',
		                radius: 7
		            }
		        }
		    },
		    series:list

		});
	
	/*var config = {
            type: 'line',
           
            data: {
            	
            	yLabels:yLabel,
                datasets: dataSet
            },
			legend: {
				display: false
				},
            options: {
                responsive: true,
                legend: {
    				display: false
    				},
                elements: {
                    rectangle: {
                        borderWidth: 2,
                    },point: {
                    	
                        pointStyle: "rect",
                        fillColor: window.chartColors.green
                    }
                },
                zoom: {
                    enabled: true
                },
                scales: {
                    xAxes: [{
                        type: "time",
                        time: {
                            displayFormats: {
                              'day': 'MMM DD'
                              
                            }
                          },
                        display: true,
                        scaleLabel: {
                            display: true,
                            labelString: 'Date/Time'
                        }
                    }] ,yAxes: [{
                        type: 'category',
                        position: 'left',
                        display: true,
                        scaleLabel: {
                            display: true,
                            labelString: 'Snzor'
                        },
                        ticks: {
                            reverse: true
                        }
                    }]
                }
            }
        };
	$('#connectionLoading').css("visibility", "hidden");
	 var ctx = document.getElementById("connection").getContext("2d");
     window.myLine = new Chart(ctx, config);

     window.myLine.update();*/
	
}


function zoomChart() {
    // different zoom methods can be used - zoomToIndexes, zoomToDates, zoomToCategoryValues
    chart.zoomToIndexes(chartData.length - 40, chartData.length - 1);
}


	function ajaxSuccessArea(result){
		var data=result.readings;
	//////Area Chart
	var labels=[];
	var minTmp=[];
	var avgTmp=[];
	var maxTmp=[];
	for(var i=0;i<data.length;i++){
		labels.push(data[i].utcTime);
		minTmp.push(data[i].min);
		avgTmp.push(data[i].avg);
		maxTmp.push(data[i].max);
	}


        var data = {
    			labels: labels,
    			datasets: [{
    				backgroundColor: utils.transparentize(presets.grey),
    				borderColor: presets.grey,
    				data: minTmp,
    				label: 'Min',
    				fill: '+2'
    			}, {
    				backgroundColor: utils.transparentize(presets.purple),
    				borderColor: presets.purple,
    				data: avgTmp,
    				label: 'Avg',
    				fill: false
    			}, {
    				backgroundColor: utils.transparentize(presets.red),
    				borderColor: presets.red,
    				data: maxTmp,
    				label: 'Max',
    				fill: 8
    			}]
    		};

    		var options = {
    				responsive: true, 
    				maintainAspectRatio: false,
    			spanGaps: false,
    			elements: {
    				line: {
    					tension: 0.000001
    				}
    			}
    		};
    		$('#areaLoading').css("visibility", "hidden");
    		
    		var chart = new Chart('chartArea', {
    			type: 'line',
    			data: data,
    			options: options
    		});
    		chart.options.elements.line.tension =  0.4 ;
    		chart.update();
	}
	function getColorArray(value,num){
	var index=-1;
	if(value >= -100 && value<= -80){
		index= 0;
	}
	else if(value > -80 && value<= -60){
		index= 1;
	}
	else if(value > -60 && value<= -40){
		index= 2;
	}
	else if(value > -40 && value<= -20){
		index= 3;
	}
	else if(value > -20 ){
		index= 4;
	}
	var colors=[];
	if(num==0){
		colors=[window.chartColors.grey,window.chartColors.grey,window.chartColors.grey,window.chartColors.grey,window.chartColors.grey,window.chartColors.grey,window.chartColors.grey,window.chartColors.grey,window.chartColors.grey,window.chartColors.grey,window.chartColors.grey,window.chartColors.grey,window.chartColors.grey,window.chartColors.grey,window.chartColors.grey];
	}else if(num==5){
		colors=[window.chartColors.yellow,window.chartColors.yellow,window.chartColors.yellow,window.chartColors.yellow,window.chartColors.yellow,window.chartColors.yellow,window.chartColors.yellow,window.chartColors.yellow,window.chartColors.yellow,window.chartColors.yellow,window.chartColors.yellow,window.chartColors.yellow,window.chartColors.yellow,window.chartColors.yellow,window.chartColors.yellow];	
	}else if(num==10){
	
		colors=[window.chartColors.red,window.chartColors.red,window.chartColors.red,window.chartColors.red,window.chartColors.red,window.chartColors.red,window.chartColors.red,window.chartColors.red,window.chartColors.red,window.chartColors.red,window.chartColors.red,window.chartColors.red,window.chartColors.red,window.chartColors.red,window.chartColors.red];
	}
	for(var i=num;i<= index+num;i++){
		
		if(num==0){
			colors[i] = window.chartColors.blue; 
		}else if(num==5){
			colors[i] = window.chartColors.orange; 
		}else{
			colors[i] = window.chartColors.red1; 
		}
	 }

	return colors;
}
	function ajaxSuccessSummary(result)
	{
		//let data = JSON.parse(result);
		var data = result; 
		var connection = data.connection;
		
		
		var non=connection.total - connection.connected;
		
		var doughnutData = [
							{
								value: connection.connected,
								color:"#68dff0"
							},
							{
								value : non,
								color : "#444c57"
							}
						];
		if(connection.status){
			 document.getElementById('rowClassConnected').style.display="block";
			 document.getElementById('rowClassNotConnected').style.display="none";
		 }else{
			 document.getElementById('rowClassConnected').style.display="none";
			 document.getElementById('rowClassNotConnected').style.display="block"; 
		 }
		
		$('#total-snzorsLoading').css("visibility", "hidden");
		 Morris.Donut({
		        element: 'total-snzors',
		        data: [
		          {label: 'Connected', value: connection.connected },
		          {label: 'Non-Connected', value: non }
		        ],
		          colors: ['#68dff0', '#444c57'],
		        formatter: function (y) { return y + "/" + connection.total }
		      });

  // Alrams Chart
	
  var alarm=data.alarms;
    var config = {
        type: 'pie',
        data: {
            datasets: [{
                data: [ /*1,
                        2,
                        3*/
                    alarm.critical,
                    alarm.major,
                    alarm.minor
                ],
                backgroundColor: [
                    window.chartColors.red,
                    window.chartColors.blue,
                    window.chartColors.green
                ],
                label: 'Dataset 1'
            }],
            labels: [
                "Critical",
                "Major",
                "Minor"
            ]
        },
        options: {
            responsive: true
        }
    }; 
    $('#alarmLoading').css("visibility", "hidden");
    var ctx = document.getElementById("myChart").getContext("2d");
    window.myPie = new Chart(ctx, config);
    
    //////////////////////// bar chart signal stren
    var rssi=data.rssi;
    $('#signalLoading').css("visibility", "hidden");
    
    var canvas = document.getElementById('signalChart');
    var dataSet = {
        labels: ["", "", "", "", "","", "", "", "", "","", "", "", "", ""],
        datasets: [
            {
                label: "Min",
                backgroundColor: getColorArray(rssi.min,0),
                borderColor: window.chartColors.grey,
                formatter: function (y) { return rssi.min },
                
                data: [ 10, 20, 30, 40,50,0,0,0,0,0,0,0,0,0,0]
            },
            {
                label: "Avg",
                backgroundColor: getColorArray(rssi.avg,5),
                borderColor: window.chartColors.grey,
               
                
                data: [ 0,0,0,0,0,10, 20, 30, 40,50,0,0,0,0,0]
            },
            {
                label: "Max",
                backgroundColor: getColorArray(rssi.max,10),
                borderColor: window.chartColors.grey,
               
                
                data: [0,0,0,0,0,0,0,0,0,0,10, 20, 30, 40,50]
            }
        ]
    };
    var option = { legend: {
                       display: true
                    },
    scales : { xAxes: [{
                            
                            display: false,
                            scaleLabel: {
                                display: false,
                                
                            }
                        }] ,
                    	 yAxes: [{
                             display: false,
                             ticks: {
                                 beginAtZero: true
                                 
                             }
                         }]
                    }

    };


    var mySignalChart = Chart.Bar(canvas,{
    	data:dataSet,
      options:option
    });
    



    ////////////////////////
    //Bar chart Battery
    var battery=data.battery;
    var color = Chart.helpers.color;
    var barChartData = {
        labels: ["Battery"],
        datasets: [{
            label: 'Min battery',
            backgroundColor: window.chartColors.red,
            borderColor: window.chartColors.red,
            borderWidth: 1,
            data: [
                   battery.min
            ]
        },{
            label: 'Avg battery',
            backgroundColor: window.chartColors.blue,
            borderColor: window.chartColors.blue,
            borderWidth: 1,
            data: [
                   battery.avg
            ]
        },{
            label: 'Max battery',
            backgroundColor: window.chartColors.orange,
            borderColor: window.chartColors.orange,
            borderWidth: 1,
            data: [
                   battery.max
            ]
        }]

    };

    $('#battaryLoading').css("visibility", "hidden");
     var ctx = document.getElementById("battary").getContext("2d");
        window.myBar = new Chart(ctx, {
            type: 'bar',
            data: barChartData,
            options: {
            	 scaleBeginAtZero:false,
            
                responsive: true,
                legend: {
                    position: 'top',
                },
                scales : {
                	 yAxes: [{
                         display: true,
                         ticks: {
                             beginAtZero: true,
                             max: 100
                         }
                     }]
                }
        
            }
        });
	}
	/// ajax connection chart
	
	 function drawChart(temperature) {

	        var data = google.visualization.arrayToDataTable([
	          ['Label', 'Value'],
	          ['Avg', temperature.avg]
	        ]);

	        var options = {
	         
	          redFrom: 90, redTo: 100,
	          yellowFrom:75, yellowTo: 90,
	          minorTicks: 5
	        };
	        
	        var chart = new google.visualization.Gauge(document.getElementById('temperature'));
	        chart.draw(data, options);
	        
	        document.getElementById('minBtn1').addEventListener('click', function() {
	        	data = google.visualization.arrayToDataTable([
	        	                                	          ['Label', 'Value'],
	        	                                	          ['Min', temperature.min]
	        	                                	        ]);
	        	chart.draw(data, options);});
	        document.getElementById('avgBtn1').addEventListener('click', function() {
	        	data = google.visualization.arrayToDataTable([
	        	                                	          ['Label', 'Value'],
	        	                                	          ['Avg', temperature.avg]
	        	                                	        ]);
	        	chart.draw(data, options);});
	        document.getElementById('maxBtn1').addEventListener('click', function() {
	        	data = google.visualization.arrayToDataTable([
	        	                                	          ['Label', 'Value'],
	        	                                	          ['Max', temperature.max]
	        	                                	        ]);
	        	chart.draw(data, options);});
	        
	        
	        
	   
	    		
	    		 
	 }

		function toggleSmooth(btn) {
			var value = btn.classList.toggle('btn-on');
			chart.options.elements.line.tension = value? 0.4 : 0.000001;
			chart.update();
		}
		
		function celsiusToFahrenheit(celsius)
		{
			 var cTemp = celsius;
			 var fTemp = cTemp * 9 / 5 + 32;
			 return fTemp;
		}
		
		function toTimeZone(time, zone) {
		    var format = 'YYYY-MM-DD HH:mm:ss';
		    return moment(time, format).tz(zone).format(format);
		}
