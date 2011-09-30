(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(spec) {
			if(spec.supersubs)
	    	{
	    		$("#" + spec.id).supersubs(spec.supersubsParams);
	    	}
	    		
	    	$("#" + spec.id).superfish(spec.params);
		}
		
		return {
			superfish : init
		}
	});
	
}) ( jQuery );

