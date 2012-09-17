(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(spec) {
           	$("#" + spec.id).datepick(spec.params);
		}
		
		return {
			enhdatepick : init
		}
	});
	
}) ( jQuery );
