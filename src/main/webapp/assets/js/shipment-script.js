function showShipmentsOptionModal(uId){
	 if(uId.status=="success"){
		 $("#shipmentOptionModal").modal({
	      	    backdrop: 'static',
	      	    
	      	}); 
		
	 }
}

function showShipmentsModal(uId){ 
    	 if(uId.status=="success"){
    		 $("#shipmentModal").modal({
    	      	    backdrop: 'static',
    	      	    
    	      	}); 
    		 $('.selectpickerItem').selectpicker(); 
    		/* if($('.selectpickerItem').val()){
	    		 $('.selectpickerItem').attr('disabled',true);
	    		 $('.selectpickerItem').find('.dropdown-toggle').attr('disabled',true);
    	 	}*/
    		 
    	         $('.snzorsList').multiselect();
    	         $('.usersList').multiselect();
    	    	 $('#datetimepicker_from').datetimepicker({format:"YYYY-MM-DD HH:mm:ss"});
    	         $('#datetimepicker_to').datetimepicker({format:"YYYY-MM-DD HH:mm:ss"});
    	         $('.selectpicker').multiselect();
    	         fillMap();
    	         
    	 }
        
    }

function showOptionModal(uId){ 
	 if(uId.status=="success"){
		 fillMap();
		 
	
	 }
}
     function showModal(uId){ 
    	 if(uId.status=="success"){
    		 fillMap();
    		 
    		 $('.selectpickerItem').selectpicker(); 
    		/* if($('.selectpickerItem').val()){
	    		 $('.selectpickerItem').attr('disabled',true);
	    		 $('.selectpickerItem').find('.dropdown-toggle').attr('disabled',true);
    	 	}*/
         $('.snzorsList').multiselect();
         $('.usersList').multiselect();
    	 $('#datetimepicker_from').datetimepicker({format:"YYYY-MM-DD HH:mm:ss"});
         $('#datetimepicker_to').datetimepicker({format:"YYYY-MM-DD HH:mm:ss"});  
         $('.selectpicker').multiselect();
    	 }
    }
     var map;
     function fillMap(){
    		var ip = $(apiUrl).val();
    		
    		
    		if(typeof shipId != 'undefined'){
    			var shipmentID = $(shipId).val();
    			var urlSummary = "http://"+ip+"/api/shipment/locations/"+ shipmentID;
        		$.ajax({url: urlSummary, 
        			type: "GET",
        			contentType:  'application/json; charset=utf-8',
        			cache: false, 			
        			
        			success: function(result){		
        				drawMap(result);
        				
        			},
        			error: function(errorThrown){
        				showGritter("Could not connect to API!","Please refer to the administrator");

        			} 
        	});	 
    		}else{
    			
    			drawMap([]);
    		}
    		
     }
     function drawMap(data){
    	 if (map) {
    	        map.remove();
    	    }
    	    map = L.map('mapid').setView([0, 0], 14);
    	    mapLink = '<a href="http://openstreetmap.org">OpenStreetMap</a>';
    	    L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    	        attribution: '&copy; ' + mapLink + ' Contributors',
    	        maxZoom: 20,
    	        loadingControl: true
    	    }).addTo(map);
    	    
    	    if (data.length > 0) {

                initMarkers(data, true);
            }
     }
     
     function initMarkers(data, showAll) {
    	    if (data) {
    	       
    	        var startMark = true;
    	        var setview = true;
    	        var array = [];
    	        var poly = [];
    	        var z, y, date;
    	        for (var i = 0; i <= data.length; i += 1) {
    	            if (typeof data[i] == 'undefined') continue;
    	            x = data[i].x || null;
    	            y = data[i].y || null;
    	            date = data[i].date || '';
    	            var axisStr = x + "_" + y;
    	            if (x == null || x == 0 || y == null || y == 0)
    	                continue;


    	            var axis = new Array(x, y);
    	            if (i < data.length) {// has next

    	                if (startMark) {// start point
    	                    if (setview) {
    	                        map.setView([x, y], 3, { animation: true });
    	                        setview = false;
    	                    }
    	                    addMark(x, y, "start", date);
    	                    startMark = false;
    	                    array.push(axisStr);
    	                    poly.push(axis);

    	                } else {

    	                    if (array.indexOf(x + "_" + y) == -1) {
    	                        array.push(axisStr);

    	                        if (showAll == true) {
    	                            addMark(x, y, null, date);
    	                            poly.push(axis);
    	                        }

    	                    }

    	                }

    	            }
    	        }

    	        // maxMarkers--;
    	    }
    	    // end point
    	    addMark(x, y, "end", date);
    	    poly.push(axis);
    	    DrawPoly(poly);


    	}
    	function addMark(x, y, type, time) {
    	    if (type == "start") {
    	        startPop = new L.Popup();
    	        var poploc = new L.LatLng(x, y);
    	        var marker = L.marker([x, y], {
    	            icon: greenIcon
    	        }).addTo(map);
    	        startPop.setLatLng(poploc);
    	        startPop.setContent("<b>Start  <br>" + time + "</br>");
    	        marker.bindPopup(startPop);
    	        // marker.addTo(map);
    	        map.addLayer(startPop);

    	    } else if (type == "end") {
    	        var marker = L.marker([x, y], {
    	            icon: redIcon
    	        }).addTo(map);
    	        map.addLayer(marker);
    	        marker.bindPopup("<b>End <br>" + time + ":</b>").openPopup();
    	    } else {
    	        var marker = L.marker([x, y]).addTo(map);
    	        marker.bindPopup("<b>" + time + "</br>");
    	    }

    	    console.log("marker added");

    	}

    	function DrawPoly(dataArr) {
    	    var polyline = L.polyline(dataArr, {
    	        color: 'red',
    	        weight: 5,
    	        opacity: .7,
    	        dashArray: '10,10',
    	        lineJoin: 'round'
    	    }).addTo(map);
    	    console.log("lines added");
    	}
    	
    	 function viewError(data){
        	 if(data.type=="error"){
        		 handleAjaxError(data);
        		 var msg="Please refer to the administrator";
        		 var code=200;
        		 if(data.errorMessage.indexOf("400")!=-1){
        			 var code=400;
        			 msg="Shipment Code Already Exist, Please refer to the administrator for any issues";
        		 }else if(data.errorMessage.indexOf("404")!=-1 ){
        			 var code=404;
        			 msg="Shipment Not Found, Please refer to the administrator for any issues"; 
        		 }else if(data.errorMessage.indexOf("403")!=-1 ){
        			 var code=403;
        			 msg="Can not change the status of the shipment, Please refer to the administrator for any issues"; 
        		 }
        		 showGritter("Error",msg);
        	 } 
         }