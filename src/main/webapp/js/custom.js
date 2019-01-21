$(document).ready(function () {
    initAnimation();
    initMainMenu();


    $('.toggle-fullscreen').click(function () {
        $(document).toggleFullScreen();
    });



    $(window).scroll(function () {
        var scroll = $(window).scrollTop();

        //>=, not <=
        if (scroll >= 100) {
            //clearHeader, not clearheader - caps H
            $(".sticky").addClass("fixed-nav");
        }

        else { $(".sticky").removeClass("fixed-nav"); }



    });


    $('.owl-carsoul-four').owlCarousel({
        loop: true,
        margin: 10,
        nav: true,

        autoplayTimeout: 5000,
        autoplayHoverPause: true,
        responsive: {
            0: {
                items: 1
            },
            600: {
                items: 3
            },
            1000: {
                items: 4
            }
        }
    })

    $(".benefits .next").click(function () {
        $(".benefits .owl-prev").click();
    })
    $(".benefits .prev").click(function () {
        $(".benefits .owl-next").click();
    })



    $('.owl-carousel-five').owlCarousel({
        loop: true,
        margin: 10,
        nav: true,
        autoplay: true,
        autoplayTimeout: 5000,
        autoplayHoverPause: true,
        responsive: {
            0: {
                items: 1
            },
            600: {
                items: 3
            },
            1000: {
                items: 6
            }
        }
    })

    $(".languages .next").click(function () {
        $(".languages .owl-prev").click();
    })
    $(".languages .prev").click(function () {
        $(".languages .owl-next").click();
    })

    //accordion

    $(function () {
        var Accordion = function (el, multiple) {
            this.el = el || {};
            this.multiple = multiple || false;

            // Variables privadas
            var links = this.el.find('.link');
            // Evento
            links.on('click', { el: this.el, multiple: this.multiple }, this.dropdown)
        }

        Accordion.prototype.dropdown = function (e) {
            var $el = e.data.el;
            $this = $(this),
                $next = $this.next();

            $next.slideToggle();
            $this.parent().toggleClass('open');

            if (!e.data.multiple) {
                $el.find('.submenu').not($next).slideUp().parent().removeClass('open');
            };
        }

        var accordion = new Accordion($('#accordion'), false);
    });



});


$(window).on('load', function () {

        $('.preloader img').fadeOut();
        $('.preloader').delay(350).fadeOut('slow', function () {

        });

    });

 function initAnimation() {

        new WOW().init();

    } // initAnimation

 function initMainMenu() {

        //Submenu Dropdown Toggle
        if ($('.main-menu li.dropdown ul').length) {

            $('.main-menu li.dropdown').append('<div class="dropdown-btn"></div>');

            //Dropdown Button
            $('.main-menu li.dropdown .dropdown-btn').on('click', function () {
                $(this).prev('ul').slideToggle(300);
                $('.main-menu li.dropdown').toggleClass("active");
                $(this).closest('.dropdown-btn').toggleClass("down");
            });

        }

    } // initMainMenu


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

