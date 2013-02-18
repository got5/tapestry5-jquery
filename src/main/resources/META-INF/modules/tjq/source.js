requirejs.config({
	"shim" : {
		"tjq/vendor/showsource/jquery.snippet": ["jquery"]
	}
});

define(["tjq/vendor/showsource/jquery.snippet"], function() {
	init = function(specs) {
		
	    var snippet = jQuery("#"+specs.id + " pre."+specs.lang);
        	
        	jQuery(snippet).snippet(specs.lang,
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
	        	
	        	jQuery(snippet).find("li").each(function(index){
	        		
	        		var o = jQuery(this);
	        		
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
	        	
	        	jQuery(snippet).find("li").each(function(index){
	        		
	        		var o = $(this);
	        		
	        		o.html(o.html().substring(goodCount));
	        	});
        	}
        	
        	jQuery(snippet).closest(".my-snippet-container").css("display", "block");
        	
        
	  };
  	
  	return exports = init;
});