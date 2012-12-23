package org.got5.tapestry5.jquery.utils;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.StylesheetLink;

public class JQueryUtils {
	/**
	 * Merge obj1 with obj2. Obj2 has predominance.
	 * 
	 * @param obj1
	 * @param obj2
	 * @return null if obj1 is null. Else return obj1 merged with obj2
	 */
	public final static JSONObject merge(JSONObject obj1, JSONObject obj2) {
		if (obj1 == null)
			return null;

		if (obj2 == null)
			return obj1;

		for (String key : obj2.keys()) {
			obj1.put(key, obj2.get(key));
		}

		return null;
	}

	public static Mapper<Asset, StylesheetLink> assetToStylesheetLink = new Mapper<Asset, StylesheetLink>() {
		public StylesheetLink map(Asset input) {
			return new StylesheetLink(input);
		};
	};

	public static JSONObject convertInformalParametersToJson(
			ComponentResources resources, String prefix) {
		JSONObject json = new JSONObject();
		for (String parameter : resources.getInformalParameterNames()) {
			if (parameter.startsWith(prefix)) {
				json.put(parameter.substring(prefix.length()),
						resources.getInformalParameter(parameter, String.class));
			}
		}
		return json;
	}

}
