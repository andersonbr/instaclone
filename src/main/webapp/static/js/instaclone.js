window.like = function(id) {};
window.dislike = function(id) {};

(function($) {
  "use strict"; // Start of use strict

  // Toggle the side navigation
  $("#sidebarToggle").on('click', function(e) {
    e.preventDefault();
    $("body").toggleClass("sidebar-toggled");
    $(".sidebar").toggleClass("toggled");
  });

  // Prevent the content wrapper from scrolling when the fixed side navigation hovered over
  $('body.fixed-nav .sidebar').on('mousewheel DOMMouseScroll wheel', function(e) {
    if ($(window).width() > 768) {
      var e0 = e.originalEvent,
        delta = e0.wheelDelta || -e0.detail;
      this.scrollTop += (delta < 0 ? 1 : -1) * 30;
      e.preventDefault();
    }
  });

  // Scroll to top button appear
  $(document).on('scroll', function() {
    var scrollDistance = $(this).scrollTop();
    if (scrollDistance > 100) {
      $('.scroll-to-top').fadeIn();
    } else {
      $('.scroll-to-top').fadeOut();
    }
  });

  // Smooth scrolling using jQuery easing
  $(document).on('click', 'a.scroll-to-top', function(event) {
    var $anchor = $(this);
    $('html, body').stop().animate({
      scrollTop: ($($anchor.attr('href')).offset().top)
    }, 1000, 'easeInOutExpo');
    event.preventDefault();
  });

  window.like = function(id) {
	  console.log(id);
	  var alreadyClicked = false;
	  if ($(".userPost[data-postid=" + id + "] .postButtons button.btlike.disabled").length == 0) {
		  alreadyClicked = true;
	  }
	  $(".userPost[data-postid=" + id + "] .postButtons button").addClass("disabled");
	  if (!alreadyClicked) {
		  // curtir
		  $(".userPost[data-postid=" + id + "] .postButtons button.btlike").removeClass("disabled");
		  $.post("/rest/curtir/set/" + id + "/t", {}, (res) => { console.log(res); })
	  } else {
		  // cancelar
		  $.post("/rest/curtir/set/" + id + "/n", {}, (res) => { console.log(res); })
	  }
  };
  window.dislike = function(id) {
	  console.log(id);
	  var alreadyClicked = false;
	  if ($(".userPost[data-postid=" + id + "] .postButtons button.btdislike.disabled").length == 0) {
		  alreadyClicked = true;
	  }
	  $(".userPost[data-postid=" + id + "] .postButtons button").addClass("disabled");
	  if (!alreadyClicked) {
		  // descurtir
		  $(".userPost[data-postid=" + id + "] .postButtons button.btdislike").removeClass("disabled");
		  $.post("/rest/curtir/set/" + id + "/f", {}, (res) => { console.log(res); })
	  } else {
		  // cancelar
		  $.post("/rest/curtir/set/" + id + "/n", {}, (res) => { console.log(res); })
	  }
  };
  $(".userPost").each((i,e) => {
  	var id = ($(e).data("postid"));
  	$.get("/rest/curtir/status/" + id, (res) => {
  		if(res.curtir === true) {
  			$(".userPost[data-postid=" + res.foto + "] .btlike").removeClass("disabled");
  		} else if(res.curtir === false) {
  			$(".userPost[data-postid=" + res.foto + "] .btdislike").removeClass("disabled");
  		}
  	});
  });

})(jQuery); // End of use strict
