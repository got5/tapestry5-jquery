package org.got5.tapestry5.jquery.test.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.MarkupWriterListener;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.dom.Element;

public class AddHeaderDataTable {

	@InjectContainer
	private ClientElement element;

	@Parameter(defaultPrefix = BindingConstants.BLOCK)
	private Block blockHeader;

	private Element theNewRow;

	private MarkupWriterListener listener = new MarkupWriterListener() {

		public void elementDidStart(Element element) {
			// TODO Auto-generated method stub

		}

		public void elementDidEnd(Element element) {

			if (element.getName().equals("thead")) {

				theNewRow.moveToTop(element);
			}

		}
	};

	public Block beginRender(MarkupWriter writer) {
		theNewRow = writer.element("tr");
		return blockHeader;
	}

	public void beforeRenderTemplate(MarkupWriter writer) {
		writer.end();
	}

	public void setupRender(MarkupWriter writer) {
		writer.addListener(listener);
	}

	public void afterRender(MarkupWriter writer) {
		writer.removeListener(listener);
	}
}
