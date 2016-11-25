package org.got5.tapestry5.jquery.utils;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.func.Predicate;
import org.apache.tapestry5.func.Worker;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.StylesheetLink;

public class JQueryUtils {
	/**
	 * Merge obj1 with obj2. Obj2 has predominance.
	 *
	 * @param obj1 base object
	 * @param obj2 merged into obj1
	 * @return null if obj1 is null. Else return obj1 merged with obj2
	 */
	public final static JSONObject merge(final JSONObject obj1, final JSONObject obj2) {
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

	//TODO Unit Test
	public static JSONObject convertInformalParametersToJson(
			final ComponentResources resources, final String prefix) {
		final JSONObject json = new JSONObject();
		F.flow(resources.getInformalParameterNames()).filter(new Predicate<String>() {

			public boolean accept(String param) {
				return param.startsWith(prefix);
			}
		}).each(new Worker<String>() {

			public void work(String params) {
				json.put(params.substring(prefix.length()),
						resources.getInformalParameter(params, String.class));
			}
		});

		return json;
	}

}
