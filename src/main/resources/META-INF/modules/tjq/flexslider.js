requirejs.config({
    "shim" : {
        "tjq/vendor/components/flexslider/jquery.flexslider" : [ "jquery" ]
    }
});
define([ "tjq/vendor/components/flexslider/jquery.flexslider" ], function() {
    return function(spec) {
        jQuery("#" + spec.id).flexslider(spec.params);
    };
});