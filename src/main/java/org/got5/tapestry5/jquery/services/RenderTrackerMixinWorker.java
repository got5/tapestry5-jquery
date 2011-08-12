package org.got5.tapestry5.jquery.services;

import java.util.List;

import org.apache.tapestry5.func.Predicate;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.services.ClassTransformation;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.ComponentInstanceOperation;
import org.apache.tapestry5.services.ComponentMethodAdvice;
import org.apache.tapestry5.services.ComponentMethodInvocation;
import org.apache.tapestry5.services.TransformField;
import org.apache.tapestry5.services.TransformMethod;
import org.apache.tapestry5.services.TransformMethodSignature;
import org.got5.tapestry5.jquery.mixins.Selector;

public class RenderTrackerMixinWorker implements ComponentClassTransformWorker {
	private final RenderTracker renderTracker;
	
	public RenderTrackerMixinWorker(RenderTracker renderTracker) {
		this.renderTracker = renderTracker;
	}

	public void transform(ClassTransformation transformation, MutableComponentModel model) {

		List<TransformMethod> methods = transformation.matchMethods( new Predicate<TransformMethod>() {
			
			@Override
			public boolean accept(TransformMethod method) {
				if ( "getClientId".equals(method.getName()) ) {
					return true;
				}
				return false;
			}
		});
		if ( methods == null || methods.size() == 0 ) {
			return;
		}
		for ( String mixin : model.getMixinClassNames() ) {
			if ( mixin.equals(Selector.class.getName())) {
				return;
			}
		}
		
		model.addMixinClassName(Selector.class.getName(),"before:*");
				
	}

}
