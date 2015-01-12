requirejs.config({
    "shim" : {
        "tjq/vendor/mixins/mask/jquery-maskedinput" : [ "jquery" ]
    }
});
define([ "tjq/vendor/mixins/mask/jquery-maskedinput" ], function() {
    return function(spec) {
        jQuery("#" + spec.id).mask(spec.format);
    };
});