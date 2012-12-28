(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(spec) {
			$(spec.selector).colorbox(spec);
		}
		
		return {
			gallery : init
		}
	});
	
}) ( jQuery );
