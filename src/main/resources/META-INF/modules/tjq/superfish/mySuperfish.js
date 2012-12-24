requirejs.config({
	"shim" : {
		"tjq/superfish/superfish": ["jquery"], 
		"tjq/superfish/hoverIntent": ["jquery"], 
		"tjq/superfish/jquery.bgiframe.min": ["jquery"], 
		"tjq/superfish/supersubs": ["jquery"]
	}
});

define(["tjq/superfish/superfish", "tjq/superfish/hoverIntent", "tjq/superfish/jquery.bgiframe.min", "tjq/superfish/supersubs"], function() {
	init = function(spec) {
	    var field;
	    field = jQuery("#"+spec.id);
	    if(spec.supersubs)
	    {
	    	field.supersubs(spec.supersubsParams);
	    }
	    		
	    field.superfish(spec.params);
	  };
  	
  	return exports = init;
});