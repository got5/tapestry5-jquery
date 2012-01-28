/*
 * Copyright 2010 GOT5 (Gang Of Tapestry 5)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
(function(window) {
	
	var Tapestry = {

	    /** Event that allows observers to perform cross-form validation after individual
	     *  fields have performed their validation. The form element is passed as the
	     *  event memo. Observers may set the validationError property of the Form's Tapestry object to true (which
	     *  will prevent form submission).
	     */
	    FORM_VALIDATE_EVENT: "tapestry:formvalidate",
	    
	    /** Event fired just before the form submits, to allow observers to make
	     *  final preparations for the submission, such as updating hidden form fields.
	     *  The form element is passed as the event memo.
	     */
	    FORM_PREPARE_FOR_SUBMIT_EVENT: "tapestry:formprepareforsubmit",
	    
	    /**
	     *  Form event fired after prepare.
	     */
	    FORM_PROCESS_SUBMIT_EVENT: "tapestry:formprocesssubmit",
	    
	    /** Event, fired on a field element, to cause observers to validate the input. Passes a memo object with
	     * two keys: "value" (the raw input value) and "translated" (the parsed value, usually meaning a number
	     * parsed from a string).  Observers may invoke Element.showValidationMessage()
	     *  to identify that the field is in error (and decorate the field and show a popup error message).
	     */
	    FIELD_VALIDATE_EVENT: "tapestry:fieldvalidate",
	
	    /**
	     * Event notification, on a form object, that is used to trigger validation
	     * on all fields within the form (observed by each field's
	     * Tapestry.FieldEventManager).
	     */
	    FORM_VALIDATE_FIELDS_EVENT : "tapestry:validatefields",
	    
	    /** Event, fired on the document object, which identifies the current focus input element. */
	    FOCUS_CHANGE_EVENT: "tapestry:focuschange",
	    
	    /** Event, fired on a zone element when the zone is updated with new content. */
	    ZONE_UPDATED_EVENT: "tapestry:zoneupdated",
	
	    /**
	     * Event fired on a form fragment element to change the visibility of the
	     * fragment. The event memo object includes a key, visible, that should be
	     * true or false.
	     */
	    CHANGE_VISIBILITY_EVENT : "tapestry:changevisibility",
	
	    /**
	     * Event fired on a form fragment element to hide the element and remove it
	     * from the DOM.
	     */
	    HIDE_AND_REMOVE_EVENT : "tapestry:hideandremove",
	
	    /**
	     * Event fired on a link or submit to request that it request that the
	     * correct ZoneManager update from a provided URL.
	     */
	    TRIGGER_ZONE_UPDATE_EVENT : "tapestry:triggerzoneupdate",
	
	    /** Event used when intercepting and canceling the normal click event. */
	    ACTION_EVENT : "tapestry:action",
	    
	    /** When false, the default, the Tapestry.debug() function will be a no-op. */
	    DEBUG_ENABLED: false,
	    
	    /** Time, in seconds, that console messages are visible. */
	    CONSOLE_DURATION: 10,

		/**
		 * CSS Class added to a <form> element that directs Tapestry to prevent
		 * normal (HTTP POST) form submission, in favor of Ajax (XmlHttpRequest)
		 * submission.
		 */
		PREVENT_SUBMISSION : "t-prevent-submission",
	    
		/**
		 * Event triggered when a row of the AjaxFormLoop has been added
		 */
		AJAXFORMLOOP_ROW_ADDED : "tjq:rowadded",
		
		/**
		 * Event triggered when a row of the AjaxFormLoop has been deleted
		 */
		AJAXFORMLOOP_ROW_REMOVED : "tjq:rowremoved",
		
	    /** Container of functions that may be invoked by the Tapestry.init() function. */
		Initializer: { }
		
	};

	window.Tapestry = Tapestry;
})(window);