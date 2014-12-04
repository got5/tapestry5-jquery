(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(spec) {
           	$("#" + spec.id).timeEntry(spec.params);
		}
		
		return {
			tentry : init
		}
	});
	
}) ( jQuery );
