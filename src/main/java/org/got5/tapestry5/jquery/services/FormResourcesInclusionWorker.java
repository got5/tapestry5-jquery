//
// Copyright 2010 GOT5 (Gang Of Tapestry 5)
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// 	http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package org.got5.tapestry5.jquery.services;

import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.services.ClassTransformation;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.got5.tapestry5.jquery.mixins.IncludeFormResources;

/**
 * Adds a {@link IncludeFormResources} mixin when a Form component is transformed
 */
public class FormResourcesInclusionWorker implements ComponentClassTransformWorker
{
    public void transform(ClassTransformation transformation, MutableComponentModel model)
    {
        if (model.getComponentClassName().equals(Form.class.getName()))
            model.addMixinClassName(IncludeFormResources.class.getName());
    }
}
