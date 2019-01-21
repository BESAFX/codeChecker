
var listUrl="http://localhost:9090/api/snzors";
var addUrl="http://localhost:9090/api/snzor";
var updateUrl="http://localhost:9090/api/snzor";
var deleteUrl="http://localhost:9090/api/snzor";
var selectedId=-1;
$(document).ready(function(){
  
  // On page load: datatable
  var table_companies = $('#table_snzors').dataTable({
	  ajax:{
	  type: "GET",
	  url: listUrl ,  
	  dataSrc: ''
		  },
  
    "columns": [
      { "data": "id" },
      { "data": "firmware" },
      { "data": "hardwareModel",   "sClass": "company_name" },
      { "data": "macAddress" },
      { "data": "serialNumber",        "sClass": "integer" },
      { "data": "superAccount",    "sClass": "integer" },
      { "data": null,       	  "sClass": "functions",
        defaultContent: '<div class=\"function_buttons\"><ul><li class=\"function_edit\"><a data-id=\"3066\" data-name=\"wwwww\"><span>Edit<\/span><\/a><\/li><li class=\"function_delete\"><a data-id=\"3066\" data-name=\"wwwww\"><span>Delete<\/span><\/a><\/li><\/ul><\/div>'
      }
      
      
    ],
    "aoColumnDefs": [
      { "bSortable": false, "aTargets": [-1] }
    ],
    "lengthMenu": [[10, 25, 50, 50, -1],
                   [10, 25, 50, 100, "All"]],
    "oLanguage": {
      "oPaginate": {
        "sFirst":       " ",
        "sPrevious":    " ",
        "sNext":        " ",
        "sLast":        " ",
      },
      "sLengthMenu":    "Records per page: _MENU_",
      "sInfo":          "Total of _TOTAL_ records (showing _START_ to _END_)",
      "sInfoFiltered":  "(filtered from _MAX_ total records)"
    }
  });
  
  // On page load: form validation
  jQuery.validator.setDefaults({
    success: 'valid',
    rules: {
     
    },
    errorPlacement: function(error, element){
      error.insertBefore(element);
    },
    highlight: function(element){
      $(element).parent('.field_container').removeClass('valid').addClass('error');
    },
    unhighlight: function(element){
      $(element).parent('.field_container').addClass('valid').removeClass('error');
    }
  });
  var form_snzors = $('#form_snzors');
  form_snzors.validate();

  // Show message
  function show_message(message_text, message_type){
    $('#message').html('<p>' + message_text + '</p>').attr('class', message_type);
    $('#message_container').show();
    if (typeof timeout_message !== 'undefined'){
      window.clearTimeout(timeout_message);
    }
    timeout_message = setTimeout(function(){
      hide_message();
    }, 8000);
  }
  // Hide message
  function hide_message(){
    $('#message').html('').attr('class', '');
    $('#message_container').hide();
  }

  // Show loading message
  function show_loading_message(){
    $('#loading_container').show();
  }
  // Hide loading message
  function hide_loading_message(){
    $('#loading_container').hide();
  }

  // Show lightbox
  function show_lightbox(){
    $('.lightbox_bg').show();
    $('.lightbox_container').show();
  }
  // Hide lightbox
  function hide_lightbox(){
    $('.lightbox_bg').hide();
    $('.lightbox_container').hide();
  }
  // Lightbox background
  $(document).on('click', '.lightbox_bg', function(){
    hide_lightbox();
  });
  // Lightbox close button
  $(document).on('click', '.lightbox_close', function(){
    hide_lightbox();
  });
  // Escape keyboard key
  $(document).keyup(function(e){
    if (e.keyCode == 27){
      hide_lightbox();
    }
  });
  
  // Hide iPad keyboard
  function hide_ipad_keyboard(){
    document.activeElement.blur();
    $('input').blur();
  }

  // Add snzors dialog
  $(document).on('click', '#add_company', function(e){
    e.preventDefault();
  
    $('.lightbox_content h2').text('Add Snzors');
    $('#form_snzors button').text('Add Snzors');
    $('#form_snzors').attr('class', 'form add');
    $('#form_snzors').attr('data-id', '');
    $('#form_snzors .field_container label.error').hide();
    $('#form_snzors .field_container').removeClass('valid').removeClass('error');
    $('#form_snzors #id_div').hide();
    $('#form_snzors #firmware').val('');
    $('#form_snzors #hardwaremodel').val('');
    $('#form_snzors #macaddress').val('');
    $('#form_snzors #serialNumber').val('');
     
    show_lightbox();
  });

  // Add snzor submit form
  $(document).on('submit', '#form_snzors.add', function(e){
    e.preventDefault();
    // Validate form
    if (form_snzors.valid() == true){
      // Send company information to database
      hide_ipad_keyboard();
      hide_lightbox();
      show_loading_message();
      var data = {};
      var form_data = $('#form_snzors').serializeArray().map(function(x){data[x.name] = x.value;}); 
      var request   = $.ajax({
        url:          addUrl,
        cache:        false,
        data:         JSON.stringify(data),   
        contentType:  'application/json; charset=utf-8',
        type:         'POST'
      });
      request.done(function(output){
        if (output){
          // Reload datable
          table_companies.api().ajax.reload(function(){
        	 
            hide_loading_message();
            var serial = $('#serialNumber').val();
            show_message("Snzor '" + serial + "' added successfully.", 'success');
          }, true);
        } else {
          hide_loading_message();
          show_message('Add request failed', 'error');
        }
      });
      request.fail(function(jqXHR, textStatus){
        hide_loading_message();
        show_message('Add request failed: ' + textStatus, 'error');
      });
    }
  });

  
  

  // populate edit form
  $(document).on('click', '.function_edit a', function(e){
    e.preventDefault();
    // Get company information from database
    show_loading_message();
    var id      =  $(this).parent('li').parent('ul').parent('div').parent('td').parent('tr').find("td:first").html();//$(this).data('id');
    var request = $.ajax({
      url:          addUrl+"/"+id,
      cache:        false,
      dataType:     'json',
      contentType:  'application/json; charset=utf-8',
      type:         'Get'
    });
    request.done(function(output){
    	var res=output;
      if (res){
  	 
        $('.lightbox_content h2').text('Edit snzor');
        $('#form_snzors button').text('Edit snzor');
        $('#form_snzors').attr('class', 'form edit');
        $('#form_snzors').attr('data-id', id);
        $('#form_snzors .field_container label.error').hide();
        $('#form_snzors .field_container').removeClass('valid').removeClass('error');
        $('#form_snzors #id').val(res.id);
        $('#form_snzors #firmware').val(res.firmware);
        $('#form_snzors #hardwaremodel').val(res.hardwareModel);
        $('#form_snzors #macaddress').val(res.macAddress);
        $('#form_snzors #serialNumber').val(res.serialNumber);
        $('#form_snzors #superAccount').val(res.superAccount);
        $('#form_snzors #configid').val(res.configid);
        hide_loading_message();
        show_lightbox();
      } else {
        hide_loading_message();
        show_message('Information request failed', 'error');
      }
    });
    request.fail(function(jqXHR, textStatus){
      hide_loading_message();
      show_message('Information request failed: ' + textStatus, 'error');
    });
  });
  
  // Edit snzors submit form
  $(document).on('submit', '#form_snzors.edit', function(e){
    e.preventDefault();
    // Validate form
    if (form_snzors.valid() == true){
      // Send snzors information to database
      hide_ipad_keyboard();
      hide_lightbox();
      show_loading_message();
      var id        = $('#form_snzors').attr('data-id');
      var data = {};
      var jsondata=$('form#form_snzors').serializeJSON();
      console.log("---json edit---"+jsondata);
      debugger;
      var form_data = $('#form_snzors').serializeArray().map(function(x){data[x.name] = x.value;}); 
      var request   = $.ajax({
        url:          updateUrl,
        cache:        false,
        data:         jsondata,
        dataType:     'json',
        contentType:  'application/json; charset=utf-8',
        type:         'PUT'
      });
      request.done(function(output){
    	  var res=output;
        if (res){
          // Reload datable
          table_companies.api().ajax.reload(function(){
            hide_loading_message();
            var serial_number = $('#firmware').val();
            show_message("Snzor '" + serial_number + "' edited successfully.", 'success');
          }, true);
        } else {
          hide_loading_message();
          show_message('Edit request failed', 'error');
        }
      });
      request.fail(function(jqXHR, textStatus){
        hide_loading_message();
        show_message('Edit request failed: ' + textStatus, 'error');
      });
    }
  });
  
  // Delete snzor
  $(document).on('click', '.function_delete a', function(e){
    e.preventDefault();
    var id = $(this).data('name');
    if (confirm("Are you sure you want to delete Snzor '" + id + "'?")){
      show_loading_message();
      var id     =  $(this).parent('li').parent('ul').parent('div').parent('td').parent('tr').find("td:first").html();//$(this).data('id');
      var request = $.ajax({
        url:          deleteUrl+'/' + id,
        cache:        false,
        dataType:     'json',
        contentType:  'application/json; charset=utf-8',
        type:         'delete'
      });
      request.done(function(output){
    	  var res=output;
        if (res){
          // Reload datable
          table_companies.api().ajax.reload(function(){
            hide_loading_message();
            show_message("Company '" + company_name + "' deleted successfully.", 'success');
          }, true);
        } else {
          hide_loading_message();
          show_message('Delete request failed', 'error');
        }
      });
      request.fail(function(jqXHR, textStatus){
        hide_loading_message();
        show_message('Delete request failed: ' + textStatus, 'error');
      });
    }
  });

});