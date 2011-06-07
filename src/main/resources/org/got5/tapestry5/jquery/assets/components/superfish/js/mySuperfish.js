(function( $ ) {

$.extend(Tapestry.Initializer, {
    superfish: function(specs) {
        
    	if(specs.supersubs)
    	{
    		$("#" + specs.id).supersubs(specs.supersubsParams);
    	}
    		
    	$("#" + specs.id).superfish(specs.params);
    }
});

}) ( jQuery );

