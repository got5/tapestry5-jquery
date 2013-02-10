define(["t5/core/dom", "t5/core/zone", "t5/core/events", "vendor/jqueryui"], 
function(dom, zone, events) {
	init = function(specs) {
		if(!specs.displayTextField) jQuery("#" + specs.textFieldId).css("display", "none");
			var options={
				slide:function(e,u){
					jQuery("#" + specs.textFieldId).val(u.value);
				}, 
				change:function(e,u){
					if(specs.url) {
						var sep = (specs.url.indexOf("?") >= 0) ? "&" : "?";
						var z = dom.wrap(specs.zoneId); 
						z.trigger(events.zone.refresh, {
							url: specs.url + sep + "slider="+u.value
						});
					}
				}
			};
	        jQuery("#" + specs.sliderId).slider(specs.params).slider("option", options);
	  };
  	
  	return exports = init;
});