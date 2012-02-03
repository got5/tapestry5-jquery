(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(spec) {
			$('#' + spec.triggerId).click(function(e) {

			    e.preventDefault();
	            jQuery('#' + spec.dialogId).dialog('open');
				
				return false;
	        });
		}
		
		return {
			dialogLink : init
		};
	});
	
}) ( jQuery );
