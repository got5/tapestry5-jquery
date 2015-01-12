requirejs.config({
    "shim" : {
        "tjq/vendor/components/gmap/gmap3" : [ "async!http://maps.google.com/maps/api/js?sensor=false", "jquery" ]
    }
});

define([ "tjq/vendor/components/gmap/gmap3" ], function() {
    return function(spec) {

        if (isEmpty(specs.params)) {

            jQuery('#' + specs.id).gmap3();

        } else {

            jQuery('#' + specs.id).gmap3(specs.params);
        }
    };
});