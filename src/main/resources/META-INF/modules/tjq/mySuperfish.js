requirejs.config({
    "shim" : {
        "tjq/vendor/components/superfish/superfish" : [ "jquery" ],
        "tjq/vendor/components/superfish/hoverIntent" : [ "jquery" ],
        "tjq/vendor/components/superfish/jquery.bgiframe.min" : [ "jquery" ],
        "tjq/vendor/components/superfish/supersubs" : [ "jquery" ]
    }
});

define([ "tjq/vendor/components/superfish/superfish",
         "tjq/vendor/components/superfish/hoverIntent",
         "tjq/vendor/components/superfish/jquery.bgiframe.min",
         "tjq/vendor/components/superfish/supersubs" ], function() {

    return function(spec) {
        var field = jQuery("#" + spec.id);

        if (spec.supersubs) {
            field.supersubs(spec.supersubsParams);
        }

        field.superfish(spec.params);
    };
});