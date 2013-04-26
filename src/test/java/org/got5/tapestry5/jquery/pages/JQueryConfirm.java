package org.got5.tapestry5.jquery.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.Zone;

public class JQueryConfirm 
{
	@SuppressWarnings("unused")
	@Property
	private String login;
	
	@SuppressWarnings("unused")
	@Property
	private String password;
	
	@Component
    private Form loginForm;
	
	@InjectComponent(value = "password")
    private PasswordField passwordField;
	
	/**
     * Do the cross-field validation
     */
    void onValidateFromLoginForm() 
    {
    	loginForm.recordError(passwordField, "Invalid user name or password.");
    }
	
    @SuppressWarnings("unused")
	@Property
    @Persist
    private int clickCount;

    @Component
    private Zone counterZone;
    
    @OnEvent(value=EventConstants.ACTION, component="clicker")
    Object onActionFromClicker()
    {
      clickCount++;

      return counterZone.getBody();
    }
}
