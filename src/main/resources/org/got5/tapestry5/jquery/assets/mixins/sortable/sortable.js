(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(specs) {
			
			var sep = (specs.url.indexOf("?") >= 0) ? "&" : "?";

			if(!specs.params.update)
				specs.params.update=function(a,b){
					ajaxRequest = {
	                        url: specs.url + sep + "list="+ $("#"+specs.id).sortable("toArray").toString()
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