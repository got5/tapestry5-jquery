(function($){
    
	/** Container of functions that may be invoked 
	 * by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
        source: function(specs){
        	
        	
        	$("pre."+specs.lang).snippet(specs.lang,
        			{
        				style:specs.style,
        				collapse:specs.collapse,
        				showMsg:specs.showMsg,
        				hideMsg:specs.hideMsg,
        				showNum:specs.showNum
        			});
        	
        	if(specs.beginLine!=0)
        	{
	        	var count=0;
	        	
	        	var goodCount=0;
	        	
	        	$("pre."+specs.lang).find("li").each(function(index){
	        		
	        		var o = $(this);
	        		
	        		
	        		
	        		for(var i=0; i<o.html().length; i++)
	        		{	
	        			
	        			o.html(o.html().replace('&nbsp;',' '));
	        			
	        			if(o.html().charAt(i)==' ')
	        			{
	        				count++;
	        			}
	        			else
	        			{
	        				break;
	        			}
	        		}
	        		
	        		if(count<goodCount || index==0) 
	        		{
	        			goodCount=count;
	        		}
	        		
	        		count=0;
	        	});
	        	
	        	$("pre."+specs.lang).find("li").each(function(index){
	        		
	        		var o = $(this);
	        		
	        		o.html(o.html().substring(goodCount));
	        	});
        	}
        	
        }
    });
})(jQuery);