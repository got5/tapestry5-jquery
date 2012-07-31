(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(spec) {
			$("#" + spec.id).Jcrop(spec.params); ;
		}
		
		return {
			imageCropper : init
		}
	});
	
}) ( jQuery );
