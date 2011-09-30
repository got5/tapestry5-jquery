(function( $ ) {
	T5.extendInitializers(function(){
		
		function init(specs) {
			 $("#" + specs.id).dataTable({
	        		"bProcessing": specs.params.bProcessing,
	        		"bServerSide": specs.params.bServerSide,
	        		"sAjaxSource": specs.params.sAjaxSource,
	        		"sPaginationType": "full_numbers"
	        });
		}
		
		return {
			dataTable : init
		}
	});

}) ( jQuery );

