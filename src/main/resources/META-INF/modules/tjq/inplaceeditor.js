requirejs.config({
    "shim" : {
        "tjq/vendor/components/jeditable/jquery.jeditable" : [ "jquery" ]
    }
});
define([ "tjq/vendor/components/jeditable/jquery.jeditable" ], function() {
    return function(spec) {
        jQuery("#" + spec.clientId).editable(spec.href, spec.options);
    };
});