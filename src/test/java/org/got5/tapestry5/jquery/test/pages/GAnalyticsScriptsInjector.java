package org.got5.tapestry5.jquery.test.pages;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.dom.Document;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.internal.util.MessagesImpl;
import org.apache.tapestry5.services.MarkupRenderer;
import org.apache.tapestry5.services.MarkupRendererFilter;

public class GAnalyticsScriptsInjector implements MarkupRendererFilter {

	private final static Messages SCRIPTS = MessagesImpl.forClass(GAnalyticsScriptsMessages.class);

	private final String key;

	public GAnalyticsScriptsInjector(@Inject @Symbol("ganalytics.key") String key) {
		this.key = key;
	}

	private void addScript(Document document) {
		if (key != null && !key.trim().equals("")) {
			Element root = document.getRootElement();

			if (root == null)
				return;

			Element body = root.find("body");

			if (body == null) {
				body = root.element("body");
			}

			Element e = body.element("script", "type", "text/javascript");

			e.raw(SCRIPTS.get("scriptOne"));

			e = body.element("script", "type", "text/javascript");

			e.raw(SCRIPTS.format("scriptTwo", key));

		}
	}

	public void renderMarkup(MarkupWriter writer, MarkupRenderer renderer) {
		renderer.renderMarkup(writer);
		
		this.addScript(writer.getDocument());
	}

}
