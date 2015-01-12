define([ "t5/core/dom", "t5/core/events" ], function(dom, events) {
    return function(spec) {
        var element = spec.element;
        var zoneId = spec.zoneId;
        var dialogId = spec.dialogId;
        var url = spec.url;

        var dialog = jQuery('#' + dialogId), zone = jQuery("#" + zoneId);

        jQuery("#" + element).click(function(e) {

            e.preventDefault();

            var z = dom.wrap(zoneId);
            if (z) {

                z.on(events.zone.didUpdate, function() {
                    dialog.dialog('open');
                });
                z.trigger(events.zone.refresh, {
                    url : url

                });
            }	

               /* zone.tapestryZone("update", {
                    url: url,
                    callback: function() {
                        dialog.dialog('open');
                    }
                });*/

            return false;
        });
    };
});