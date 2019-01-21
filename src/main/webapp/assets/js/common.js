function showGritter(title,message){
	$.gritter.add({
        // (string | mandatory) the heading of the notification
        title: title,
        // (string | mandatory) the text inside the notification
        text: message,
        // (string | optional) the image to display on the left
    
        // (bool | optional) if you want it to fade out on its own or just sit there
        sticky: true,
        // (int | optional) the time you want it to be alive for before fading out
        time: '',
        // (string | optional) the class name you want to apply to that specific message
        class_name: 'my-sticky-class'
    });
	
}

function showSuccessfullPopup(title ,txt){
  	  $.gritter.add({
            title: title,
            text: txt,
            time: 1000,
            fade:true,
            class_name :'gritter-light'
       });
  	 
   }
	
	function showErrorPopup(title ,txt){
	   	  $.gritter.add({
	             title: title,
	             text: txt,
	             time: 3000,
	             fade:true
	        });
	   	 
	    }