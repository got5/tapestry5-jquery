requirejs.config({
    "shim" : {
        "tjq/vendor/ui/custom" : [ "jquery" ],
        "tjq/vendor/jquery.json-2.4" : [ "jquery" ],
        "tjq/vendor/components/palette/jquery.palette" : [ "tjq/vendor/ui/custom", "tjq/vendor/jquery.json-2.4" ]
    }
});
define([ "tjq/vendor/components/palette/jquery.palette" ], function() {
    return function(specs) {
        jQuery("#" + specs.id).palette(specs);
    };
});
