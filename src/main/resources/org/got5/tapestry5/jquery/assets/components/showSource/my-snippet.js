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
        		showNum:specs.showNum,
        		clipboard:specs.clipboard
        	});
        	
        	if(specs.beginLine!=0)
        	{
	        	var count=0;
	        	
	        	var goodCount=0;
	        	
	        	var flag=false;
	        	
	        	$("pre."+specs.lang).find("li").each(function(index){
	        		
	        		var o = $(this);
	        		
	        		o.html(o.html().replace(/&nbsp;/gi,' '));
	        		
	        		if(!/^\s*$/.test(o.html()))
	        		{
		        		for(var i=0; i<o.html().length; i++)
			        	{	
		        			
			        		if(o.html().charAt(i)==' ')
			        		{
			        			count++;
			        		}
			        		else
			        		{
			        			break;
			        		}
			        	}
	        			
			        	if(count<goodCount || !flag) {goodCount=count;flag=true;}
			        	
			        	count=0;
	        		}
	        		
	        	});
	        	
	        	$("pre."+specs.lang).find("li").each(function(index){
	        		
	        		var o = $(this);
	        		
	        		o.html(o.html().substring(goodCount));
	        	});
        	}
        	
        }
    });
})(jQuery);