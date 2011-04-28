package org.got5.tapestry5.jquery.base;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.FieldTranslator;
import org.apache.tapestry5.FieldValidationSupport;
import org.apache.tapestry5.FieldValidator;
import org.apache.tapestry5.NullFieldStrategy;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.base.AbstractField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.apache.tapestry5.services.Request;
import org.got5.tapestry5.jquery.ImportJQueryUI;

@ImportJQueryUI(value = {"jquery.ui.widget", "jquery.ui.mouse", "jquery.ui.slider"})
@Events({ EventConstants.TO_CLIENT, EventConstants.VALIDATE, EventConstants.PARSE_CLIENT })
public class AbstractSlider extends AbstractField
{
	@Parameter(required = true, principal = true)
    private Object value;
	
    @Environmental
    private ValidationTracker tracker;

    @Inject
    private ComponentResources resources;

    @Inject
    private Request request;
    
    @Inject
    private FieldValidationSupport fieldValidationSupport;
    
    @Parameter(defaultPrefix = BindingConstants.VALIDATE)
    @SuppressWarnings("unchecked")
    private FieldValidator<Object> validate;
    
    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.TRANSLATE)
    private FieldTranslator<Object> translate;

    @Inject
    private ComponentDefaultProvider defaultProvider;
    
    final Binding defaultTranslate()
    {
        return defaultProvider.defaultTranslatorBinding("value", resources);
    }
    
    final Binding defaultValidate()
    {
        return defaultProvider.defaultValidatorBinding("value", resources);
    }
    
    @Parameter(defaultPrefix = BindingConstants.NULLFIELDSTRATEGY, value = "default")
    private NullFieldStrategy nulls;
    
	@SuppressWarnings({ "unchecked" })
    @Override
    protected void processSubmission(String elementName)
    {
        String rawValue = request.getParameter(elementName);
        
        tracker.recordInput(this, rawValue);

        try
        {
            Object translated = fieldValidationSupport.parseClient(rawValue, resources, translate, nulls);
            
            putPropertyNameIntoBeanValidationContext("value");
            
            System.out.println(validate);
            fieldValidationSupport.validate(translated, resources, validate);

            if (!InternalUtils.isBlank(rawValue))
                value = translated;
        }
        catch (ValidationException ex)
        {
            tracker.recordError(this, ex.getMessage());
        }
        
        removePropertyNameFromBeanValidationContext();
    }

}