(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(spec) {
			 $("#" + spec.id).mask(spec.format);
		}
		
		return {
			mask : init
		}
	});
	
}) ( jQuery );