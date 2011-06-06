(function( $ ) {

$.extend(Tapestry.Initializer, {
    superfish: function(specs) {
        
    	if(specs.supersubs)
    	{
    		$("#" + specs.id).supersubs(specs.supersubsParams);
    	}
    		
    	$("ul." + specs.classe).superfish(specs.params);
    }
});

}) ( jQuery );

