(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(specs) {
			 $("#" + specs.id).dialog(specs.params);
		}
		
		return {
			dialog : init
		}
	});
	
}) ( jQuery );