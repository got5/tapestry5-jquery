requirejs.config({
    "shim" : {
        "tjq/vendor/mixins/raty/jquery.raty" : [ "jquery" ]
    }
});
define([ "tjq/vendor/mixins/raty/jquery.raty" ], function() {
    return function(spec) {
        if (spec.location === "before") {
            jQuery("#" + spec.target).before("<div id='" + spec.id + "'/>");
        } else {
            jQuery("#" + spec.target).after("<div id='" + spec.id + "'/>");
        }

        if (spec.hide) {
            jQuery("#" + spec.target).hide();
        }

        jQuery("#" + spec.id).raty(spec.params);
    };
});