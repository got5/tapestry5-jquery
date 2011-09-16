(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(specs) {
			$("#" + specs.id).accordion(specs.params);
		}
		
		return {
			accordion : init
		}
	});
	
}) ( jQuery );



