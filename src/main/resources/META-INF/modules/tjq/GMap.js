requirejs.config({
	"shim" : {
		"tjq/vendor/gmap/gmap3": ["async!http://maps.google.com/maps/api/js?sensor=false", "jquery"]
	}, 
	paths : {
    	
    }
});

define(["tjq/vendor/gmap/gmap3"], function() {
	init = function(spec) {
		
	    jQuery("#" + spec.id).gmap3(spec.params);
	  };
  	
  	return exports = init;
});