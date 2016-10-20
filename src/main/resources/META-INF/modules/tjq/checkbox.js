requirejs.config({
    "shim" : {
        "tjq/vendor/components/ckbox/jquery.ui.checkbox" : [ "tjq/vendor/ui/jquery-ui" ]
    }
});

define([ "tjq/vendor/components/ckbox/jquery.ui.checkbox" ], function() {
    return function(spec) {
        jQuery("#" + spec.id).checkbox();
    };
});