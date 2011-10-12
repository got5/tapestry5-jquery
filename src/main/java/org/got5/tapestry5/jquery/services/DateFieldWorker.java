package org.got5.tapestry5.jquery.services;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.corelib.components.DateField;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticMethod;
import org.apache.tapestry5.services.TransformConstants;
import org.apache.tapestry5.services.javascript.JavaScriptStackSource;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;

public class DateFieldWorker implements ComponentClassTransformWorker2{

	
	private final JavaScriptStackSource js;
	
	public DateFieldWorker(JavaScriptStackSource javaScriptSupport)
    {
    	this.js = javaScriptSupport;
    }
	
	public void transform(PlasticClass plasticClass,
			TransformationSupport support, MutableComponentModel model) {
		
		
		if (model.getComponentClassName().equals(DateField.class.getName())
				&& js.getStack("core-datefield").toString().contains("JQueryDateFieldStack"))
        {
			PlasticMethod setupRender = plasticClass.introduceMethod(TransformConstants.AFTER_RENDER_DESCRIPTION);
    		
    		setupRender.addAdvice(new MethodAdvice() {
				
				public void advise(MethodInvocation invocation) {
					
					MarkupWriter writer = (MarkupWriter) invocation.getParameter(0);
					
					invocation.proceed();
					
					Element el = writer.getElement().getElementByAttributeValue("class", "t-calendar-trigger");
					
					if(el != null){
						el.remove();
					}
				}
			});
    		
    		model.addRenderPhase(AfterRender.class);

        }
	}

}
