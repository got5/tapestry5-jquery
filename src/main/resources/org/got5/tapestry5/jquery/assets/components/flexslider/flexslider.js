(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(spec) {
			$("#" + spec.id).flexslider(spec.params);
		}
		
		return {
			flexslider : init
		}
	});
	
}) ( jQuery );
