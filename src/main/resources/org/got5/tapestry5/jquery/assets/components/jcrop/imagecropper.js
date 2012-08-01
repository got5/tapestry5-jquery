(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(spec) {
			$("#" + spec.id).Jcrop({
	            onSelect: function(c){
				if(spec.url) 
					$("#" + spec.zoneId).tapestryZone("update", {
						url: spec.url+"?x="+c.x+"&y="+c.y + "&x2="+c.x2+"&y2="+c.y2 + "&w="+c.w+"&h="+c.h
					});
				}
	            
	        }); 
		}
		
		return {
			imageCropper : init
		}
	});
	
}) ( jQuery );
