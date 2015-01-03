define([ "t5/core/dom", "t5/core/zone", "t5/core/events" ], function(dom, zone, events) {
    return function(spec) {

        var element = dom.wrap(spec.elementId),
            event,
            z,
            updateZone;

        element.attr("data-update-zone", spec.zone);

        z = zone.findZone(element);

        updateZone = function() {
            var params = {};
            if (element.value()) {
                params.param = element.value();
            }

            z.trigger(events.zone.refresh, {
                url : spec.url,
                parameters : params
            });
        }

        if (spec.event) {
            event = spec.event;
            element.on(event, updateZone);
        }
    };
});