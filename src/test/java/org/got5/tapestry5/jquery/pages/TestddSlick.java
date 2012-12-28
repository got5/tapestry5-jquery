package org.got5.tapestry5.jquery.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;

public class TestddSlick {

	@Property
	private List<JSONObject> options;

	@Property
	private JSONObject currentOptions;

	@Property
	private int index;

	@Property
	@InjectComponent(value = "myZone")
	private Zone myZone;

	@Inject
	private Request request;

	@Property
	private String value;

	@SetupRender
	public void init() {
		System.out.println("---------------->init");

		options = new ArrayList<JSONObject>();

		// the json config options 1 (color)
		JSONObject options1 = new JSONObject();

		// item config options
		JSONObject option1Item1 = new JSONObject();
		option1Item1.put("text", "Red");
		option1Item1.put("value", "red");
		option1Item1.put("valueOther", "red");
		option1Item1.put("selected", true);
		option1Item1.put("description", "Color red");
		option1Item1.put("imageSrc", "http://dl.dropbox.com/u/40036711/Images/facebook-icon-32.png");
		options1.accumulate("ddData", option1Item1);

		JSONObject option1Item2 = new JSONObject();
		option1Item2.put("text", "Green");
		option1Item2.put("value", "Green");
		option1Item2.put("valueOther", "Green");
		option1Item2.put("selected", false);
		option1Item2.put("description", "Color green");
		option1Item2.put("imageSrc", "http://dl.dropbox.com/u/40036711/Images/twitter-icon-32.png");
		options1.accumulate("ddData", option1Item2);

		// other config options see: http://designwithpc.com/Plugins/ddSlick#demo
		options1.put("ddSelectText", "Select your color");
		options1.put("ddDefaultSelectedIndex", null);
		options1.put("ddImagePosition", "left");
		options1.put("ddTruncateDescription ", true);
		options1.put("ddBackground ", "#66A3C7");
		options1.put("ddWidth", 260);
		options1.put("ddHeight", null);
		options1.put("ddShowSelectedHTML ", true);

		options.add(options1);

		

	}

	@OnEvent(value = "onSelectedElement")
	public Object onSelectedElement(@RequestParameter(value = "value", allowBlank = true) String value,
			@RequestParameter(value = "valueOther", allowBlank = true) String valueOther) {
		System.out.println("---------------->" + value);
		System.out.println("---------------->" + valueOther);

		options = new ArrayList<JSONObject>();

		// the json config options 1 (color)
		JSONObject options1 = new JSONObject();

		// item config options
		JSONObject option1Item1 = new JSONObject();
		option1Item1.put("text", "Red");
		option1Item1.put("value", "red");
		option1Item1.put("valueOther", "red");
		option1Item1.put("lauchEvent", true);
		option1Item1.put("description", "Color red");
		option1Item1.put("imageSrc", "http://dl.dropbox.com/u/40036711/Images/facebook-icon-32.png");
		options1.accumulate("ddData", option1Item1);

		JSONObject option1Item2 = new JSONObject();
		option1Item2.put("text", "Green");
		option1Item2.put("value", "Green");
		option1Item2.put("valueOther", "Green");
		
		option1Item2.put("description", "Color green");
		option1Item2.put("imageSrc", "http://dl.dropbox.com/u/40036711/Images/twitter-icon-32.png");
		options1.accumulate("ddData", option1Item2);

		// other config options see: http://designwithpc.com/Plugins/ddSlick#demo
		options1.put("ddSelectText", "Select your color");
		options1.put("ddDefaultSelectedIndex", valueOther.equalsIgnoreCase("Green") ? 1 : 0 );
		options1.put("ddImagePosition", "left");
		options1.put("ddTruncateDescription ", true);
		options1.put("ddBackground ", "#66A3C7");
		options1.put("ddWidth", 260);
		options1.put("ddHeight", null);
		options1.put("ddShowSelectedHTML ", true);

		options.add(options1);

		return request.isXHR() ? myZone.getBody() : null;
	}

}
