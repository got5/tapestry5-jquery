(function( $ ) {

$.extend(Tapestry.Initializer, {
    dataTable: function(specs) {
        $("#" + specs.id).dataTable({
        		"bProcessing": specs.params.bProcessing,
        		"bServerSide": specs.params.bServerSide,
        		"sAjaxSource": specs.params.sAjaxSource
        });
	}
});

}) ( jQuery );

