var map;
     function showSnzorModal(uId){ 
    	 if(uId.status=="success"){
    		 
    		 $("#snzorModal").modal({
    	      	    backdrop: 'static'
    	      	    
    	      	}); 
    		 $('.switch')['bootstrapSwitch']();
    		 $('.selectpickerItem').selectpicker(); 
    		 var x=$(".lang").val();
        	 var y=$(".lat").val();
        	 $('#divHid').css("visibility", "hidden");
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
     	    
     	   map.setView([0,0], 3, { animation: true });
     	   if(x!="" || y!=""){
     		  var marker = L.marker([x, y]).addTo(map);
   		    marker.bindPopup("Last Location.");
     	   }
      	  
    			
    	 }
    	 
        
    }
     function showModal(uId){ 
    	 var x=$("#lang").value;
    	 var y=$("#lat").value;
    	 if(uId.status=="success"){
    		 $('#divHid').css("visibility", "hidden");
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
      	    
      	  map.setView([0,0], 3, { animation: true });
    	   if(x!="" || y!=""){
    		  var marker = L.marker([x, y]).addTo(map);
  		    marker.bindPopup("Last Location.");
    	   }
     	  
    	 $('.switch')['bootstrapSwitch']();
    	 $('.selectpickerItem').selectpicker(); 
    	 }
    }
     function showLabelModal(uId){
	if(uId.status=="success"){
    		 
    		 $("#snzorLabelModal").modal({
    	      	    backdrop: 'static'
    	      	    
    	      	}); 
    		 $('.selectpicker').selectpicker(); 
	}
     }
     function viewError(data){
    	 if(data.type=="error"){
    		 
    		 handleAjaxError(data);
    		 var msg="Please refer to the administrator";
    		 var code=500;
    		 if(data.errorMessage.indexOf("400")!=-1){
    			 code=400;
    			 msg="Snzor Serial Already Exist, Please refer to the administrator for any issues";
    		 }else if(data.errorMessage.indexOf("404")!=-1 ){
    			 code=404;
    			 msg="Snzor Not Found, Please refer to the administrator for any issues"; 
    		 }else if(data.errorMessage.indexOf("423")!=-1 ){
    			 code=423;
    			 msg="Exceed Number Of user for that account, Please refer to the administrator for any issues"; 
    		 }else if(data.errorMessage.indexOf("423")!=-1 ){
    			 code=423;
    			 msg="Exceed Number Of user for that account, Please refer to the administrator for any issues"; 
    		 }else if(data.errorMessage.indexOf("405")!=-1 ){
    			 code=405;
    			 msg="Snzor Assigned to Shipment not Shipped yet, Please refer to the administrator for any issues"; 
    		 }
    		 showGritter("Error",msg);
    	 } 
     }
     
     $(document).on('click', '.browse', function(){
    	  var file = $(this).parent().parent().parent().find('.file');
    	  file.trigger('click');
    	});
    	$(document).on('change', '.file', function(){
    	  $(this).parent().find('.input-lg').val($(this).val().replace(/C:\\fakepath\\/i, ''));
    	});
    	
    	 $(document).on('click',".bootstrap-select > .dropdown-toggle", function(){
    	    $(this).parent().addClass("open");
        });
    