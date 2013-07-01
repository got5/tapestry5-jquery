(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(specs) {
		
			var el = $("#" + specs.sliderId);
			 
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
						var zoneElement = specs.zoneId === '^' ? $(el).closest('.t-zone')
							: $("#" + specs.zoneId);
						var sep = (specs.url.indexOf("?") >= 0) ? "&" : "?";
						zoneElement.tapestryZone("update", {
							url: specs.url + sep + "min="+u.values[0]+"&max="+u.values[1]
						});
					}
				}
			};
	        el.slider(specs.params).slider("option", options);	
		}
		
		return {
			rangeSlider : init
		}
	});
	
}) ( jQuery );