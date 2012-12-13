(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(spec) {
			 $("#" + spec.id).jScrollPane(spec.params);
		}
		
		return {
			jscrollpane : init
		}
	});
	
}) ( jQuery );

