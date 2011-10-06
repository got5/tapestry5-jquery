//
// Copyright 2010 GOT5 (GOTapestry5)
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package org.got5.tapestry5.jquery.mixins;

import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentEventCallback;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ContentType;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Field;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.internal.util.Holder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ResponseRenderer;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.util.TextStreamResponse;
import org.got5.tapestry5.jquery.ImportJQueryUI;

/**
 * A mixin for a text field that allows for autocompletion of text fields. This is based on
 * Prototype's autocompleter
 * control.
 * <p/>
 * The mixin renders an (initially invisible) progress indicator after the field (it will also be
 * after the error icon most fields render). The progress indicator is made visible during the
 * request to the server. The mixin then renders a &lt;div&gt; that will be filled in on the client
 * side with dynamically obtained selections.
 * <p/>
 * Multiple selection on the client is enabled by binding the tokens parameter (however, the mixin
 * doesn't help split multiple selections up on the server, that is still your code's
 * responsibility).
 * <p/>
 * The container is responsible for providing an event handler for event "providecompletions". The
 * context will be the partial input string sent from the client. The return value should be an
 * array or list of completions, in presentation order. I.e.
 * <p/>
 *
 * <pre>
 * String[] onProvideCompletionsFromMyField(String input)
 * {
 *   return . . .;
 * }
 * </pre>
 * 
 * @see <a href="http://jqueryui.com/demos/autocomplete/">The JQuery AutoComplete Mixin</a>
 * @see <a href="http://tapestry.apache.org/current/tapestry-core/ref/org/apache/tapestry5/corelib/mixins/Autocomplete.html">The Tapestry AutoComplete Mixin</a>
 */
@ImportJQueryUI({"jquery.ui.widget", "jquery.ui.position", "jquery.ui.autocomplete" })
@Import(library = { "${assets.path}/mixins/autocomplete/autocomplete.js" })
@Events(EventConstants.PROVIDE_COMPLETIONS)
public class Autocomplete
{
    static final String EVENT_NAME = "autocomplete";

    private static final String PARAM_NAME = "t:input";

    /**
     * The field component to which this mixin is attached.
     */
    @InjectContainer
    private Field field;

    @Inject
    private ComponentResources resources;

    @Environmental
    private JavaScriptSupport javaScriptSupport;

    @Inject
    private Request request;

    /**
     * Overwrites the default minimum characters to trigger a server round trip (the default is 1).
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private int minLength;

    @Inject
    private ResponseRenderer responseRenderer;

    /**
     * Overrides the default check frequency for determining whether to send a server request. The
     * default is .4
     * seconds.
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private double delay;

    /**
     * Mixin afterRender phrase occurs after the component itself. This is where we write the
     * &lt;div&gt; element and
     * the JavaScript.
     *
     * @param writer
     */
    void afterRender(MarkupWriter writer)
    {
        String id = field.getClientId();

        Link link = resources.createEventLink(EVENT_NAME);

        JSONObject config = new JSONObject();
        config.put("id", id);
        config.put("url", link.toAbsoluteURI());

        config.put("paramName", PARAM_NAME);

        if (resources.isBound("minLength"))
            config.put("minLength", minLength);

        if (resources.isBound("delay"))
            config.put("delay", delay);

        // Let subclasses do more.
        configure(config);

        javaScriptSupport.addInitializerCall("autocomplete", config);
    }

    Object onAutocomplete()
    {
        String input = request.getParameter(PARAM_NAME);

        final Holder<List> matchesHolder = Holder.create();

        // Default it to an empty list.

        matchesHolder.put(Collections.emptyList());

        ComponentEventCallback<List> callback = new ComponentEventCallback<List>()
        {
            public boolean handleResult(List result)
            {
                matchesHolder.put(result);

                return true;
            }
        };

        resources.triggerEvent(EventConstants.PROVIDE_COMPLETIONS, new Object[]
        { input }, callback);

        ContentType contentType = responseRenderer.findContentType(this);

        return new TextStreamResponse(contentType.toString(), generateResponseJSON(matchesHolder.get()).toString());
    }

    /**
     * @param config
     *            parameters object
     */
    protected void configure(JSONObject config)
    {
    }

    /**
     * Transforms the matches into a JSONArray
     *
     * @return JSONArray of available responses
     */
    protected JSONArray generateResponseJSON(List matches)
    {
        JSONArray array = new JSONArray();
        for (Object o : matches)
        {
            array.put(o.toString());
        }
        return array;
    }
}
