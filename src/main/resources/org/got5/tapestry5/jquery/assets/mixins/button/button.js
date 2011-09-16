(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(spec) {
			if(spec.type=="buttonset")
           	 $("#" + spec.id).buttonset(spec.params);
           else
           	$("#" + spec.id).button(spec.params);
		}
		
		return {
			button : init
		}
	});
	
}) ( jQuery );

