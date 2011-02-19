//
// Copyright 2010 GOT5 (Gang Of Tapestry 5)
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

package org.got5.tapestry5.jquery.services;

import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.corelib.components.FormFragment;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.services.ClassTransformation;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.ComponentMethodAdvice;
import org.apache.tapestry5.services.ComponentMethodInvocation;
import org.apache.tapestry5.services.TransformConstants;
import org.apache.tapestry5.services.TransformMethod;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.services.javascript.FormFragmentSupportStack;

/**
 * Adds a {@link IncludeFormResources} mixin when a Form component is transformed
 */
public class FormFragmentResourcesInclusionWorker implements ComponentClassTransformWorker
{
    private final JavaScriptSupport javaScriptSupport;

    /**
     * @param javaScriptSupport
     */
    public FormFragmentResourcesInclusionWorker(JavaScriptSupport javaScriptSupport)
    {
        this.javaScriptSupport = javaScriptSupport;
    }

    public void transform(ClassTransformation transformation, MutableComponentModel model)
    {
        if (model.getComponentClassName().equals(FormFragment.class.getName()))
        {

            addAdvicetoBeginRender(transformation);

            model.addRenderPhase(BeginRender.class);
        }
    }

    private void addAdvicetoBeginRender(ClassTransformation transformation)
    {
        TransformMethod method = transformation.getOrCreateMethod(TransformConstants.BEGIN_RENDER_SIGNATURE);

        method.addAdvice(new ComponentMethodAdvice()
        {
            public void advise(ComponentMethodInvocation invocation)
            {
                javaScriptSupport.importStack(FormFragmentSupportStack.STACK_ID);

                invocation.proceed();
            }
        });
    }

}
