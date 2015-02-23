package org.got5.tapestry5.jquery.pages;

import java.util.Locale;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PersistentLocale;

public class ClientNumericValidationDemo {
	
	 @Persist
	    @Property
	    private long longValue;

	    @Persist
	    @Property
	    private double doubleValue;

	    @Inject
	    private PersistentLocale persistentLocale;

	    void onActionFromReset()
	    {
	        longValue = 1000;
	        doubleValue = 1234.67;

	        persistentLocale.set(Locale.ENGLISH);
	    }

	    void onActionFromGerman()
	    {
	        persistentLocale.set(Locale.GERMAN);
	    }

}
