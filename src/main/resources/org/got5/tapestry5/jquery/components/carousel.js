(function( $ ) {

$.extend(Tapestry.Initializer, {
    carousel: function(specs) {
        $("#" + specs.id).jcarousel(specs.params);
        
    }
});

}) ( jQuery );