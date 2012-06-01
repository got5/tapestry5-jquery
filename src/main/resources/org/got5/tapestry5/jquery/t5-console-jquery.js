// Copyright 2011 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

(function( $ ) {
	T5.define("console", function() {
	
	    // FireFox throws an exception is you reference the console when it is not enabled.
	
	    var nativeConsoleExists = false, nativeConsole = {}, floatingConsole;
	
	    try {
	        if (console) {
	            nativeConsole = console;
	            nativeConsoleExists = true;
	        }
	    }
	    catch (e) {
	    }
	
	    function display(className, message) {
	    	
	    	if (!floatingConsole) {
	    		floatingConsole = $('<div></div>').addClass("t-console");
	    		$(document.body).prepend(floatingConsole);
	        }
	    	
	    	var div = $('<div></div>').addClass("t-console-entry " + className).html(message).hide();
	    	
	    	floatingConsole.prepend(div);
	    	
	    	div.fadeIn(250).delay(T5.console.DURATION).fadeOut(function(){
	    		T5.dom.remove(div);
	    	});
	    	
	    	div.bind('click', function(){
	    		div.stop();
	    		T5.dom.remove(div);
	    	});
	    }
	    //TODO need to be tested !!
	    function level(className, consolefn) {
	        return function (message) {
	            display(className, message);
	
	            consolefn && consolefn.call(console, message);
	        }
	    }
	
	    function error(message) {
	    	display("t-err", message);
	    	
	    	if(nativeConsoleExists) {
	    		console.error(message);
	    		
	    		// Chrome doesn't automatically output a trace with the error message.
	            // FireFox does.
	    		if (! $.browser.safari && console.trace) {
	                console.trace();
	            }
	    	}
	    }
	
	    return {
	        /** Time, in seconds, that floating console messages are displayed to the user. */
	        DURATION  : 10000,
	
	        debug : function(message) {
	            if (Tapestry.DEBUG_ENABLED) {
	                level("t-debug", nativeConsole.debug).call(window, message);
	            }
          },
	        info : level("t-info", nativeConsole.info),
	        warn : level("t-warn", nativeConsole.warn),
	        error : error
	    };
	});
})(jQuery);
