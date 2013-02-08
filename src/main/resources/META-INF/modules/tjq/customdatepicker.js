define(function() {
	init = function(spec) {
	
		jQuery(spec.selector).data('cutomDatepicker',spec);	
	};
  	
  	return exports = init;
});