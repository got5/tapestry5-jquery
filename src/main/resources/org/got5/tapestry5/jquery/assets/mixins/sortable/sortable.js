(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(specs) {
			
			if(!specs.params.update)
				specs.params.update=function(a,b){
					ajaxRequest = {
	                        url: specs.url + "?list="+ $("#"+specs.id).sortable("toArray").toString()
	                };
	                $.ajax(ajaxRequest);
			};
			$("#"+specs.id).sortable(specs.params);	
			$("#"+specs.id).disableSelection();
		}
		
		return {
			sortable : init
		}
	});
	
}) ( jQuery );