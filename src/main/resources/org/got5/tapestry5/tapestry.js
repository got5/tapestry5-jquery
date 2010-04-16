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

Tapestry = {
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
    
    /** Event, fired on the document object, which identifies the current focus input element. */
    FOCUS_CHANGE_EVENT: "tapestry:focuschange",
    
    /** Event, fired on a zone element when the zone is updated with new content. */
    ZONE_UPDATED_EVENT: "tapestry:zoneupdated",
    
    /** When false, the default, the Tapestry.debug() function will be a no-op. */
    DEBUG_ENABLED: false,
    
    /** Time, in seconds, that console messages are visible. */
    CONSOLE_DURATION: 10,
    
    /**
     * Marks a number of script libraries as loaded; this is used with virtual scripts (which combine multiple
     * actual scripts). This is necessary so that subsequent Ajax requests do not load scripts that have
     * already been loaded
     * @param scripts 	array of script paths
     */
    markScriptLibrariesLoaded: function(scripts){
    }
};

/** Container of functions that may be invoked by the Tapestry.init() function. */
Tapestry.Initializer = {

    ajaxFormLoop: function(spec){
    },
    
    formLoopRemoveLink: function(spec){
    },
    
    /**
     * Convert a form or link into a trigger of an Ajax update that
     * updates the indicated Zone.
     * @param element id or instance of <form> or <a> element
     * @param zoneId id of the element to update when link clicked or form submitted
     * @param url absolute component event request URL
     */
    linkZone: function(element, zoneId, url){
    },
    
    validate: function(field, specs){
    },
    
    zone: function(spec){
    },
    
    formFragment: function(spec){
    },
    
    formInjector: function(spec){
    },
    
    // Links a FormFragment to a trigger (a radio or a checkbox), such that changing the trigger will hide
    // or show the FormFragment. Care should be taken to render the page with the
    // checkbox and the FormFragment('s visibility) in agreement.
    linkTriggerToFormFragment: function(trigger, element){
    }
};
