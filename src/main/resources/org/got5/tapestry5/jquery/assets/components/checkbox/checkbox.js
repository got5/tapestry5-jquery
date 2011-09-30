(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(specs) {
			 $("#" + specs.id).checkbox();
		}
		
		return {
			checkbox : init
		}
	});
	
}) ( jQuery );