requirejs.config({
	"shim" : {
		"tjq/vendor/carousel/jquery.jcarousel": ["jquery"]
	}
});

define(["t5/core/dom", "t5/core/zone", "t5/core/events", "tjq/vendor/carousel/jquery.jcarousel"], function(dom, z, events) {

	dom.onDocument("click", "img[data-update-zone]", function() {
	    var zone;
	    zone = z.findZone(this);
	    if (zone) {
	      zone.trigger(events.zone.refresh, {
	        url: this.attribute("data-update-zone-url")
	      });
	    }
	    return false;
	  });
	var loadCarousel = function(spec){
		function mycarousel_itemLoadCallback(carousel, state){
	        if (state != 'init') {
				return;
			}
			jQuery.get(spec.loadCallbackUrl, function(data) {
	        	mycarousel_itemAddCallback(carousel, carousel.first, carousel.last, data, spec);
	   		});
		};
		return mycarousel_itemLoadCallback;
	};
	
	function mycarousel_itemAddCallback(carousel, first, last, data, spec){
		jQuery.each(data, function(i, d){
	        carousel.add(i, mycarousel_getItemHTML(d, spec));
		});
	    carousel.size(data.length);
	}
	
	function mycarousel_getItemHTML(url, spec){
		var w=spec.width ? spec.width : "75px";
		var h=spec.height ? spec.height : "75px";
	    return '<img src="' + url + '" width="'+w+'" height="'+h+'" alt="" />';
	}

	init = function(specs) {
	    if(specs.params.loadCallbackUrl != undefined){
    		specs.params.itemLoadCallback = loadCarousel(specs.params); 
    	}
        jQuery("#" + specs.id).jcarousel(specs.params);
	};
  	
  	return exports = init;
});

