requirejs.config({
    "shim" : {
        "tjq/vendor/mixins/contextmenu/jquery.contextMenu": ["tjq/vendor/ui/custom"]
    }
});
define(["t5/core/dom", "t5/core/events", "t5/core/ajax", "tjq/vendor/mixins/contextmenu/jquery.contextMenu"], function(dom, events, ajax ) {
    return function(specs) {

        var items, nbKeys, key, index;
        
        if (!specs.id || !specs.keys || !specs.items || !specs.items) {
            return;
        }

        items = {};
        nbKeys = specs.keys.length;
        for (index = 0; index < nbKeys; index++) {
            key = specs.keys[index];
            items[key] = specs.items[key];
        }

        jQuery.contextMenu({
            selector: '#' + specs.id,
		        callback: function(key, options) {
		            var zoneId = specs.zone;
	                if ( zoneId ) {
                      var z = dom.wrap(zoneId);
                      if (z) {
                        z.trigger(events.zone.refresh, {
                            url : items[key].url
                        });
                      }
                    } else {
                      $.ajax({
                        type: "POST",
                        url: items[key].url});
                    }
            },
            items: items,
            trigger: specs.trigger,
            delay: specs.delay,
            autoHide: specs.autoHide,
            zIndex: specs.zIndex
        });
    };
});