(function( $ ) {

$.extend(Tapestry.Initializer, {
    dialog: function(specs) {
        $("#" + specs.id).dialog(specs.params);		
    }
});

}) ( jQuery );






