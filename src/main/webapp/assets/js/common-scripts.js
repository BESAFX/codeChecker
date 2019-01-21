/*---LEFT BAR ACCORDION----*/

	$( document ).ready(function() {
		
		
	
		$('.modal').on('hidden.bs.modal', function () {
   		 location.reload();
   		});
		
		$('.sub-menu a').click(function() {
			
			if (  $(this).find('.arrow-change').hasClass('fa-angle-down'))
					{
					
						$(this).find('.arrow-change').removeClass('fa-angle-down');
						$(this).find('.arrow-change').addClass('fa-angle-right');
						
					
					}
					
					 else if( $(this).find('.arrow-change').hasClass('fa-angle-right'))
						{ 
						 $('.arrow-change').removeClass('fa-angle-down');
						 $('.arrow-change').addClass('fa-angle-right');
						 $(this).find('.arrow-change').removeClass('fa-angle-right');
						 $(this).find('.arrow-change').addClass('fa-angle-down');
			
		                }
					
			
				});		
				
		
		
	

	
		
	
	
	});


$(function() {
    $('#nav-accordion').dcAccordion({
        eventType: 'click',
        autoClose: true,
        saveState: true,
        disableLink: true,
        speed: 'slow',
        showCount: false,
        autoExpand: true,
//        cookie: 'dcjq-accordion-1',
        classExpand: 'dcjq-current-parent'
    });
});

var Script = function () {


//    sidebar dropdown menu auto scrolling

    jQuery('#sidebar .sub-menu > a').click(function () {
        var o = ($(this).offset());
        diff = 250 - o.top;
        if(diff>0)
            $("#sidebar").scrollTo("-="+Math.abs(diff),500);
        else
            $("#sidebar").scrollTo("+="+Math.abs(diff),500);
    });



//    sidebar toggle

    $(function() {
        function responsiveView() {
            var wSize = $(window).width();
            if (wSize <= 768) {
                $('#container').addClass('sidebar-close');
                $('#sidebar > ul').hide();
            }

            if (wSize > 768) {
                $('#container').removeClass('sidebar-close');
                $('#sidebar > ul').show();
            }
        }
        $(window).on('load', responsiveView);
        $(window).on('resize', responsiveView);
    });

    $('.fa-bars').click(function () {
        if ($('#sidebar > ul').is(":visible") === true) {
            $('#main-content').css({
                'margin-left': '0px'
            });
            $('#sidebar').css({
                'margin-left': '-240px'
            });
            $('#sidebar > ul').hide();
            $('.logo-size').hide();
            $("#container").addClass("sidebar-closed");
        } else {
            $('#main-content').css({
                'margin-left': '240px'
            });
            $('#sidebar > ul').show();
            $('#sidebar').css({
                'margin-left': '0'
            });
            $("#container").removeClass("sidebar-closed");
            $('.logo-size').show();
        }
    });

// custom scrollbar
    $("#sidebar").niceScroll({styler:"fb",cursorcolor:"#4ECDC4", cursorwidth: '3', cursorborderradius: '10px', background: '#404040', spacebarenabled:false, cursorborder: ''});

    $("html").niceScroll({styler:"fb",cursorcolor:"#4ECDC4", cursorwidth: '6', cursorborderradius: '10px', background: '#404040', spacebarenabled:false,  cursorborder: '', zindex: '1000'});

// widget tools

    jQuery('.panel .tools .fa-chevron-down').click(function () {
        var el = jQuery(this).parents(".panel").children(".panel-body");
        if (jQuery(this).hasClass("fa-chevron-down")) {
            jQuery(this).removeClass("fa-chevron-down").addClass("fa-chevron-up");
            el.slideUp(200);
        } else {
            jQuery(this).removeClass("fa-chevron-up").addClass("fa-chevron-down");
            el.slideDown(200);
        }
    });

    jQuery('.panel .tools .fa-times').click(function () {
        jQuery(this).parents(".panel").parent().remove();
    });


//    tool tips

    $('.tooltips').tooltip();

//    popovers

    $('.popovers').popover();



// custom bar chart

    if ($(".custom-bar-chart")) {
        $(".bar").each(function () {
            var i = $(this).find(".value").html();
            $(this).find(".value").html("");
            $(this).find(".value").animate({
                height: i
            }, 2000)
        })
    }


}();



$(document).ready(function(){
	
	//Check to see if the window is top if not then display button
	$(window).scroll(function(){
		if ($(this).scrollTop() > 100) {
			$('.go-top').fadeIn();
		} else {
			$('.go-top').fadeOut();
		}
	});
	
	//Click event to scroll to top
	$('.go-top').click(function(){
		$('html, body').animate({scrollTop : 0},800);
		return false;
	});
	
});

function handleAjaxError(u){
	if(u.responseText.indexOf("Http Status 901")!=-1){
		 window.location="/login.jsf"
	}
	
}
