
var listUrl="http://localhost:9090/api/user/";
var addUrl="http://localhost:9090/api/user";
var updateUrl="http://localhost:9090/api/user/";
var deleteUrl="http://localhost:9090/api/user/deactivate";

$(document).ready(function(){
  
  // On page load: datatable
  var table_companies = $('#table_users').dataTable({
	  ajax:{
	  type: "GET",
	  contentType:  'application/json; charset=utf-8',
	  url: listUrl ,  
	  dataSrc: ''
		  },
  
    "columns": [
      { "data": "id" },
      { "data": "username" },
      { "data": "userDatils.firstName" },
      { "data": "userDatils.lastName" },
      { "data": "account.accountName" },
      { "data": "accountReponsable", "sClass": "checkbox-column"},
      { "data": "userDatils.email"  },
      { "data": "userDatils.mobile" },
      { "data": "userDatils.phone"},
      { "data": "userDatils.address"},
      { "data": "userDatils.receiveNotification"},
      
      { "data": null,       	  "sClass": "functions",
        defaultContent: '<div class=\"function_buttons\"><ul><li class=\"function_edit\"><a ><span>Edit<\/span><\/a><\/li><li class=\"function_delete\"><a ><span>Delete<\/span><\/a><\/li><\/ul><\/div>'
      }
      
      
    ],
    "aoColumnDefs": [
      { "bSortable": false, "aTargets": [-1] }
    ],
    "lengthMenu": [[10, 25, 50, 50, -1], [10, 25, 50, 100, "All"]],
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
  var form_company = $('#form_user');
  form_company.validate();

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

  // Add company button
  $(document).on('click', '#add_account', function(e){
    e.preventDefault();
   
    $('.lightbox_content h2').text('Add User');
    $('#form_user button').text('Add User');
    $('#form_user').attr('class', 'form add');
    $('#form_user').attr('data-id', '');
    $('#form_user .field_container label.error').hide();
    $('#form_user .field_container').removeClass('valid').removeClass('error');
    $('#form_user #id_div').hide();
    $('#form_user #username').val('');
    $('#form_user #firstName').val('');
    $('#form_user #lastName').val('');
    $('#form_user #account').val('');
    $('#form_user #accountResponsible').val(false);
    $('#form_user #email').val('');
    $('#form_user #mobile').val('');
    $('#form_user #phone').val('');
    $('#form_user #receiveNotification').val(false);
    $('#form_user #address').val('');
   
    
    show_lightbox();
  });

  // Add company submit form
  $(document).on('submit', '#form_user.add', function(e){
    e.preventDefault();
    // Validate form
    if (form_company.valid() == true){
      // Send company information to database
      hide_ipad_keyboard();
      hide_lightbox();
      show_loading_message();
      var data = {};
      var form_data = $('#form_user').serializeArray().map(function(x){data[x.name] = x.value;}); 
      var request   = $.ajax({
        url:          listUrl,
        cache:        false,
        data:         JSON.stringify(data),
        dataType:     'json',
        contentType:  'application/json; charset=utf-8',
        type:         'POST'
      });
      request.done(function(output){
        if (output){
          // Reload datable
          table_companies.api().ajax.reload(function(){
            hide_loading_message();
            var account_name = $('#username').val();
            show_message("Account '" + accoun_name + "' added successfully.", 'success');
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

  
  

  // Edit company button
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
      type:         'get'
    });
    request.done(function(output){
      if (output){
    	  var res=output;
        $('.lightbox_content h2').text('Edit User');
        $('#form_user button').text('Edit User');
        $('#form_user').attr('class', 'form edit');
        $('#form_user').attr('data-id', id);
        $('#form_user .field_container label.error').hide();
        $('#form_user .field_container').removeClass('valid').removeClass('error');
        
        $('#form_user #id_div').hide();
        $('#form_user #id').val(res.id);
        $('#form_user #username').val(res.username);
        $('#form_user #firstName').val(res.userDatils.firstName);
        $('#form_user #lastName').val(res.userDatils.lastName);
        $('#form_user #account').val(res.account.accountName);
        $('#form_user #accountResponsible').prop("checked",res.accountReponsable);
        $('#form_user #email').val(res.userDatils.email);
        $('#form_user #mobile').val(res.userDatils.mobile);
        $('#form_user #phone').val(res.userDatils.phone);
        $('#form_user #receiveNotification').prop("checked",res.userDatils.receiveNotification);
        
        $('#form_user #address').val(res.userDatils.address);
       
        
       
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
  
  // Edit company submit form
  $(document).on('submit', '#form_user.edit', function(e){
    e.preventDefault();
    // Validate form
    if (form_company.valid() == true){
      // Send company information to database
      hide_ipad_keyboard();
      hide_lightbox();
      show_loading_message();
      var id        = $('#form_user').attr('data-id');
      var jsondata= $('form#form_user').serializeJSON();
      var request   = $.ajax({
        url:          updateUrl,
        cache:        false,
        data:         jsondata,
        dataType:     'json',
        contentType:  'application/json; charset=utf-8',
        type:         'PUT'
      });
      request.done(function(output){
        if (output){
          // Reload datable
          table_companies.api().ajax.reload(function(){
            hide_loading_message();
            var accountName = $('#username').val();
            show_message("Account: '" + accountName + "' edited successfully.", 'success');
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
  
  // Delete company
  $(document).on('click', '.function_delete a', function(e){
    e.preventDefault();
    var accountName = $(this).parent('li').parent('ul').parent('div').parent('td').parent('tr').find("td:nth-child(2)").html();
    if (confirm("Are you sure you want to delete '" + accountName + "'?")){
      show_loading_message();
      var id     =  $(this).parent('li').parent('ul').parent('div').parent('td').parent('tr').find("td:first").html();//$(this).data('id');
      var request = $.ajax({
    	  url:          deleteUrl+'/' + id,
          cache:        false,
          dataType:     'json',
          contentType:  'application/json; charset=utf-8',
          type:         'POST'
      });
      request.done(function(output){
        if (output.result == 'success'){
          // Reload datable
          table_companies.api().ajax.reload(function(){
            hide_loading_message();
            show_message("Account '" + accountName + "' deleted successfully.", 'success');
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