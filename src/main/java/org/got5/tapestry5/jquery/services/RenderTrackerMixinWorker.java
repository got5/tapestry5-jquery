package org.got5.tapestry5.jquery.services;

import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Flow;
import org.apache.tapestry5.func.Predicate;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticMethod;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;
import org.got5.tapestry5.jquery.mixins.Selector;

/**
 * @since 5.2.6
 */
public class RenderTrackerMixinWorker implements ComponentClassTransformWorker2 {

	public void transform(PlasticClass plasticClass,
			TransformationSupport support, MutableComponentModel model) {
		
		Flow<PlasticMethod> methods = matchEventHandlerMethods(plasticClass);

        if (methods.isEmpty())
			return;
				
		if(!model.getMixinClassNames().contains(Selector.class.getName())){
			model.addMixinClassName(Selector.class.getName(),"before:*");		
		}
		
	}
	private Flow<PlasticMethod> matchEventHandlerMethods(PlasticClass plasticClass)
    {
        return F.flow(plasticClass.getMethods()).filter(new Predicate<PlasticMethod>()
        {
            public boolean accept(PlasticMethod method)
            {
                return "getClientId".equalsIgnoreCase(method.getDescription().methodName);
            }
        });
    }	
}
