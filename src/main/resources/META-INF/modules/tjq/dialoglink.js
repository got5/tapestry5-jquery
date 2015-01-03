define(function() {
    return function(spec) {
        jQuery('#' + spec.triggerId).click(function(e) {

            e.preventDefault();
            jQuery('#' + spec.dialogId).dialog('open');

            return false;
        });
    };

});
