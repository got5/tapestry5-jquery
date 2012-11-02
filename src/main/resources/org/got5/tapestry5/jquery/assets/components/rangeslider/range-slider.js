(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(specs) {
			if(!specs.displayTextField) {
				$("#" + specs.idMinField).css("display", "none");
				$("#" + specs.idMaxField).css("display", "none");
			}
			var options={
				slide:function(e,u){
					$("#" + specs.idMinField).val(u.values[0]);
					$("#" + specs.idMaxField).val(u.values[1]);
				}, 
				change:function(e,u){
					if(specs.url) {
						var sep = (specs.url.indexOf("?") >= 0) ? "&" : "?";
						$("#" + specs.zoneId).tapestryZone("update", {
							url: specs.url + sep + "min="+u.values[0]+"&max="+u.values[1]
						});
					}
				}
			};
	        $("#" + specs.sliderId).slider(specs.params).slider("option", options);	
		}
		
		return {
			rangeSlider : init
		}
	});
	
}) ( jQuery );