(function( $ ) {
	T5.extendInitializers(function(){
		
		function init(specs) {
			 $("#" + specs.id).dataTable(specs.params);
		}
		
		return {
			dataTable : init
		};
	});

}) ( jQuery );

