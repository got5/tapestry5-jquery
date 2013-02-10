define(["t5/core/dom", "t5/core/zone", "t5/core/events", "vendor/jqueryui"], 
function(dom, zone, events) {
	init = function(specs) {
		if(!specs.displayTextField) {
				jQuery("#" + specs.idMinField).css("display", "none");
				jQuery("#" + specs.idMaxField).css("display", "none");
			}
			var options={
				slide:function(e,u){
					jQuery("#" + specs.idMinField).val(u.values[0]);
					jQuery("#" + specs.idMaxField).val(u.values[1]);
				}, 
				change:function(e,u){
					if(specs.url) {
						var sep = (specs.url.indexOf("?") >= 0) ? "&" : "?";
						var z = dom.wrap(specs.zoneId);
						z.trigger(events.zone.refresh, {
							url: specs.url + sep + "min="+u.values[0]+"&max="+u.values[1]
						});
					}
				}
			};
	        jQuery("#" + specs.sliderId).slider(specs.params).slider("option", options);
	  };
  	
  	return exports = init;
});