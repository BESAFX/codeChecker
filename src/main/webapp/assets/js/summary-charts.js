	$( document ).ready(function() {
	var apiUrl = "localhost:9090";
	var ip = "localhost:9090";
	var urlJiraBugs="http://"+ip+"/api/jira/bugs/";
	//alarm
	$.ajax({url: urlJiraBugs, 
		type: "GET",
		cache: false, 
		contentType:  'application/json; charset=utf-8',
		complete: function(xhr,status) {
				//$("#myModal").modal('hide');
				//alert(xhr.status);
		},
		success: function(result){
			ajaxBugs(result);	
		},
		error: function(errorThrown){
			//$('#alarmLoading').css("visibility", "hidden");
			
			showGritter("Could not connect to Jira API!","Please refer to the administrator");

		} 
	});
	 });
	
function ajaxBugs(bugs){	
    var config = {
        type: 'pie',
        data: {
            datasets: [{
                data: [ /*1,
                        2,
                        3*/
                    bugs.issues.length,
                    
                   
                ],
                
                label: 'Dataset 1'
            }],
            labels: [
                "Handled",
                "Not Handled"
            ]
        },
        options: {
            responsive: true
        }
    }; 
    $('#alarmLoading').css("visibility", "hidden");
    var ctx = document.getElementById("JiraBugsChart").getContext("2d");
    window.myPie = new Chart(ctx, config);

}