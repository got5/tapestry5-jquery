define([ "t5/core/dom", "t5/core/zone", "t5/core/events", "jquery" ], function init(dom, zone, events) {
    return function(params) {

        var z, 
            keepUpdatingZone, 
            timer, 
            timeout = params.period * 1000;

        // Ensure a valid period. Not required as PeriodicalUpdater already takes care of it
        if (params.period <= 0) {
            return;
        }

        // get zone
        z = dom.wrap(params.id);

        // Function to be called for each refresh
        keepUpdatingZone = function keepUpdatingZone(e) {

            if (z) {
                z.trigger(events.zone.refresh, {
                    url : params.URL
                });
            }
        };

        timer = window.setInterval(keepUpdatingZone, timeout);

        jQuery(window).unload(function() {
            window.clearInterval(timer);
        });

        jQuery("#"+params.id).on('stopRefresh', function() {
            window.clearInterval(timer);
        });

        jQuery("#"+params.id).on('startRefresh', function() {
            timer = window.setInterval(keepUpdatingZone, timeout);
        });
    };
});