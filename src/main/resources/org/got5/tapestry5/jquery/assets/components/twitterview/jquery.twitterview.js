(function($){
 
    $.fn.extend({
         
       
        twitterView: function(options) {
 
 			var formatDate = function(tweet){
				var tweetDate = new Date(tweet);
				var tweetDateTxt = tweetDate.getDate()+"/"+(tweetDate.getMonth()+1)+"/"+(tweetDate.getYear()+1900)+" ";
				tweetDateTxt+=tweetDate.getHours()<10?"0"+tweetDate.getHours():tweetDate.getHours();
				tweetDateTxt+=":"+(tweetDate.getMinutes()<10?"0"+tweetDate.getMinutes():tweetDate.getMinutes());
				return tweetDateTxt;
			};
            
            var defaults = {
                account :"gotapestry5",
				loader :"",
				timelineUrl :"",
				count :10,
				includeRetweets:1,
				callbackName:"twitterView",
				errorMessage:"an error occured while loading Twitter timeline",
				dateFormatCallback:formatDate
            };
                 
            var options =  $.extend(defaults, options);
 			
			var timelineUrl = options.timelineUrl!=""?options.timelineUrl:'http://api.twitter.com/1/statuses/user_timeline.json';
			timelineUrl += '?screen_name='+options.account;
			timelineUrl += '&include_rts='+options.includeRetweets;
			timelineUrl += '&count='+options.count;
	
			
	
			var parseText = function(tweet){
				tweetText = tweet;
				var dashRegexp = new RegExp("(#[0-9A-Za-z]*)", "g");
				var atRegexp = new RegExp("(@([0-9A-Za-z]*))","g");
				var urlRegexp = new RegExp("(https?://[^,;\\s]*)","g");
				tweetText = tweetText.replace(urlRegexp,"<a href='$1' target='_blank'>$1</a>");
				tweetText = tweetText.replace(atRegexp,"<a href='http://twitter.com/$2' target='_blank'>$1</a>");
				tweetText = tweetText.replace(dashRegexp,"<a href='http://twitter.com/search?q=$1' target='_blank'>$1</a>");
				return tweetText;
			};
	
            return this.each(function() {
				var elt = $(this);
				if(options.loader!=""){
					elt.html("<img src='"+options.loader+"' />");
				}else{
					elt.html("...");
				}
				
				
				$.ajax({
					url:timelineUrl,
					jsonp:"callback",
					dataType:"jsonp",
					jsonpCallback:options.callbackName,
					timeout:30000,
					error:function(jqXHR, textStatus, errorThrown){
						elt.html(options.errorMessage);
					},
					success:function(data, textStatus, jqXHR){
						elt.html("");
						for(var i=0; i<data.length;i++){
							var tweet = data[i];
							var retweet=(tweet.retweeted_status!=null);
							if(retweet){
								tweet = tweet.retweeted_status;	
							}
							var div = $("<div>");
							div.attr("class","tweet");
							div.append($("<img>").attr('src',tweet.user.profile_image_url));
							
							var span = $("<span>");
							span.append($("<strong>").append(tweet.user.screen_name));
							span.append(options.dateFormatCallback(tweet.created_at));
							div.append(span);
							var content = $("<div>");
							content.attr("class","content");
							content.append(parseText(tweet.text));
							if(retweet){
								content.append("<em>(retweet)</em>");	
							}
							div.append(content);
							div.append($("<div style='clear:both'></div>"));
							
							elt.append(div);
						}
					}
				});
				
                
             
            });
        }
    });
     
})(jQuery);