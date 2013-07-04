(function( $ ) {

	T5.extendInitializers(function(){
		
		function init(specs) {
			var el = $("#" + specs.sliderId);
			
			if(!specs.displayTextField) $("#" + specs.textFieldId).css("display", "none");
			var options={
				value: $("#" + specs.textFieldId).val(), 
				slide:function(e,u){
					$("#" + specs.textFieldId).val(u.value);
				}, 
				change:function(e,u){
					if(specs.url) {
						var zoneElement = specs.zoneId === '^' ? $(el).closest('.t-zone')
							: $("#" + specs.zoneId);
							
						var sep = (specs.url.indexOf("?") >= 0) ? "&" : "?";
						zoneElement.tapestryZone("update", {
							url: specs.url + sep + "slider="+u.value
						});
					}
				}
			};
	        el.slider(specs.params).slider("option", options);
		}
		
		return {
			slider : init
		}
	});
	
}) ( jQuery );