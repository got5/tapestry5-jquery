/* Copyright 2011 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Defines Tapestry Ajax support, which includes sending requests and receiving
 * replies, but also includes default handlers for errors and failures, and
 * processing of Tapestry's partial page render response (a common response for
 * many types of Ajax requests).             `
 */
T5.define("ajax", function() {

	var _ = T5._;
    //var $ = T5.$;
    var spi = T5.spi;

    var exceptionContainer, iframe, iframeDocument;

    function noop() {
    }

    function writeToErrorIFrame(content) {
    	// Clear current content.
        iframeDocument.open();
        // Write in the new content.
        iframeDocument.write(content);
        iframeDocument.close();

    }
    
    //from http://www.izzycode.com/jquery/jquery-get-browser-view-port-size.html
    function viewport() {
    	var viewportwidth;
    	 var viewportheight;
    	 
    	 // the more standards compliant browsers (mozilla/netscape/opera/IE7) use window.innerWidth and window.innerHeight
    	 
    	 if (typeof window.innerWidth != 'undefined')
    	 {
    	      viewportwidth = window.innerWidth,
    	      viewportheight = window.innerHeight
    	 }
    	 
    	// IE6 in standards compliant mode (i.e. with a valid doctype as the first line in the document)

    	 else if (typeof document.documentElement != 'undefined'
    	     && typeof document.documentElement.clientWidth !=
    	     'undefined' && document.documentElement.clientWidth != 0)
    	 {
    	       viewportwidth = document.documentElement.clientWidth,
    	       viewportheight = document.documentElement.clientHeight
    	 }
    	 
    	 // older versions of IE
    	 
    	 else
    	 {
    	       viewportwidth = document.getElementsByTagName('body')[0].clientWidth,
    	       viewportheight = document.getElementsByTagName('body')[0].clientHeight
    	 }
     
        return {width: viewportwidth, height: viewportheight}; 
    }
    
    function resizeExceptionDialog() {
    	
    	
    	// Very Prototype specific!
        var dims = viewport();

        iframe.attr("width",dims.width - 100);
        iframe.attr("height",dims.height - (100 + 20));
    }

    /**
     * When there's a server-side failure, Tapestry sends back the exception report page as HTML.
     * This function creates and displays a dialog that presents that content to the user using
     * a created iframe element.
     * @param exceptionContext HTML markup for the exception report
     */
    function showExceptionDialog(exceptionContent) {
    	 if (!exceptionContainer) {
    		 
    		 var markup = $('<div></div>').addClass("t-exception-container")
    		 
    		 var iframe_html = $('<iframe></iframe>').addClass("t-exception-frame").attr("width","100%");
    		 
    		 var span = $('<div></div>').addClass("t-exception-controls").html("<span class='t-exception-close'>Close</span>");
    		
    		 markup.append(iframe_html).append(span);
            
             
             $(document.body).append(markup);
             exceptionContainer = $("div.t-exception-container");
            
             iframe = exceptionContainer.find("iframe");

             // See http://xkr.us/articles/dom/iframe-document/

             iframeDocument = (iframe[0].contentWindow || iframe[0].contentDocument);
             if (iframeDocument.document) {
                 iframeDocument = iframeDocument.document;
             }

             var closeButton = exceptionContainer.find( ".t-exception-close");

             closeButton.bind("click", function(event) {
                 //event.stop();
                 writeToErrorIFrame("");
                 exceptionContainer.hide();
             });

             // Call it now to set initial width/height.

             resizeExceptionDialog();

             // Very Prototype specific:

             // See http://groups.google.com/group/prototype-scriptaculous/browse_thread/thread/1b0ce3e94020121f/cdbab773fd8e7a4b
             // debounced to handle the rate at which IE sends the resizes (every pixel!)

             $(window).resize(_.debounce(resizeExceptionDialog, 20));
         }


         writeToErrorIFrame(exceptionContent);

         exceptionContainer.show();
    }

    function defaultFailure(transport) {
    }

    function defaultException(exception) {
    }

    /**
     * Performs an AJAX request. The options object is used to identify
     * additional parameters to be encoded into the request, and to identify the
     * handlers for success and failure.
     * <p>
     * Option keys:
     * <dl>
     * <dt>parameters
     * <dd>object with string keys and string values, defines additional query
     * parameters
     * <dt>failure
     * <dd>A function invoked if the Ajax request fails; the function is passed
     * the transport
     * <dt>exception
     * <dd>A function invoked if there's an exception processing the Ajax
     * request, the function is passed the exception
     * <dt>success
     * <dd>A function invoked when the Ajax response is returned successfully.
     * The function is passed the transport object.
     * <dt>method
     * <dd>The type of request, 'get' or 'post'. 'post' is the default.
     * </dl>
     *
     * @param url
     *            the URL for the request
     * @param options
     *            an optional object that provides additional options.
     * @return not defined
     *
     */
    function request(url, options) {

        throw "not yet implemented";
    }

    return {
        defaultFailure : defaultFailure,
        defaultException : defaultException,
        defaultSuccess : noop,
        showExceptionDialog: showExceptionDialog,
        request : request
    };
});