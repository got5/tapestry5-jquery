requirejs.config({
    "shim" : {
        "tjq/vendor/components/gallery/jquery.colorbox" : [ "jquery" ]
    }
});
define([ "tjq/vendor/components/gallery/jquery.colorbox" ], function() {
    return function(spec) {
        jQuery(spec.selector).colorbox(spec);
    };
});