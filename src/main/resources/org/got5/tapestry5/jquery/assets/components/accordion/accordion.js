(function( $ ) {

$.extend(Tapestry.Initializer, {
    accordion: function(specs) {
        $("#" + specs.id).accordion(specs.params);
  
    }
});

}) ( jQuery );






