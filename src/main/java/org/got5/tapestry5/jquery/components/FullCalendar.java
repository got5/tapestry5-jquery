package org.got5.tapestry5.jquery.components;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.ImportJQueryUI;

/**
 * <p>
 * This Component is used to generate a calendar based on fullcalendar
 * </p>
 * <p>
 * Copyright (c) 2009 Adam Shaw Permission is hereby granted, free of charge, to
 * any person obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 * </p>
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * </p>
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * </p>
 * <p>
 * To use it, call the component as the following: <br/>
 * "&lt;div t:type="jquery/fullcalendar" t:parameter="&lt;&lt;1&gt;&gt;" /&gt;"
 * <br/>
 * <br/>
 * The fields can be filled with:
 * <ul>
 * <li>&lt;&lt;1&gt;&gt; : An array of JSON objects to configure fullcalendar
 * according to the functionalities of the jquery plugin "gmap3"</li>
 * </ul>
 * </p>
 */

@ImportJQueryUI(value = { "jquery.ui.widget", "jquery.ui.mouse",
		"jquery.ui.resizable", "jquery.ui.draggable" })
@Import(stylesheet = "classpath:org/got5/tapestry5/jquery/assets/components/fullcalendar/fullcalendar.css")
public class FullCalendar implements ClientElement {
	@Parameter
	@Property
	private JSONObject params;

	@Property
	private String id;

	@Inject
	@Path("classpath:org/got5/tapestry5/jquery/assets/components/fullcalendar/jquery.fullcalendar.js")
	private Asset fullcalendar;

	@Inject
	@Path("classpath:org/got5/tapestry5/jquery/assets/components/fullcalendar/jquery.gcal.js")
	private Asset gcal;

	@Inject
	@Path("classpath:org/got5/tapestry5/jquery/assets/components/fullcalendar/FullCalendar.js")
	private Asset fcalinit;

	@Inject
	private JavaScriptSupport javaScriptSupport;

	@Inject
	private ComponentResources resources;

	@BeginRender
	public void beginRender() {
		id = javaScriptSupport.allocateClientId(resources);
	}

	@AfterRender
	public void afterRender() {

		if (params == null) {
			params = new JSONObject();
		}

		JSONObject opt = new JSONObject();
		opt.put("id", id);

		// Header on the calendar
		if (params.has("header") && !params.isNull("header")) {
			opt.put("header", params.get("header"));
		} else {
			JSONObject header = new JSONObject();
			header.put("left", "prev,today,next");
			header.put("center", "title");
			header.put("right", "month,agendaWeek,agendaDay");
			opt.put("header", header);
		}

		// event JSON url
		if (params.has("events") && !params.isNull("events")) {
			opt.put("events", params.get("events"));
		} else {
			opt.put("events",
					"http://www.google.com/calendar/feeds/usa__en%40holiday.calendar.google.com/public/basic");
		}
		
		// click on event url
		if (params.has("eventclickurl") && !params.isNull("eventclickurl")) {
			opt.put("eventclickurl", params.get("eventclickurl"));
		}
		
		// click on day url
		if (params.has("dayclickurl") && !params.isNull("dayclickurl")) {
			opt.put("dayclickurl", params.get("dayclickurl"));
		}

		javaScriptSupport.importJavaScriptLibrary(fullcalendar);
		// Include Google Calendar support if necessary
		if (params.has("gcal") && params.get("gcal") == "true") {
			javaScriptSupport.importJavaScriptLibrary(gcal);
		}
		javaScriptSupport.importJavaScriptLibrary(fcalinit);
		javaScriptSupport.addInitializerCall("fcal", opt);
	}

	public String getClientId() {
		return id;
	}
}
