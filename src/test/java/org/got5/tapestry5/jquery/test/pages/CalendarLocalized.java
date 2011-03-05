//
// Copyright 2010 GOT5 (GO Tapestry 5)
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

package org.got5.tapestry5.jquery.test.pages;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.DateField;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PersistentLocale;



public class CalendarLocalized extends Calendar
{
	@Persist
    private Date birthday;

    @Persist
    private Date asteroidImpact;
    
    @Component
	private Form testForm;
    
    @Component(id = "asteroidImpact")
	private DateField dfai;

    @Inject
    private PersistentLocale persistentLocale;

    @Validate("required")
    public Date getBirthday()
    {
        return birthday;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public DateFormat getDateFormat()
    {
        return new SimpleDateFormat("MM/dd/yyyy");
    }

    @Validate("required")
    public Date getAsteroidImpact()
    {
        return asteroidImpact;
    }

    public void setAsteroidImpact(Date asteroidImpact)
    {
        this.asteroidImpact = asteroidImpact;
    }

    @OnEvent(value = EventConstants.SUCCESS, component="testForm")
	Object onSuccess()
	{
		Date now = new Date(); 
		if(asteroidImpact.before(now))
		{
			testForm.recordError(dfai, "must be later than now");
			
		}	
		return null;
	 }
    
    
    void onActionFromClear()
    {
        birthday = null;
        asteroidImpact = null;
    }

    void onActionFromEnglish() { persistentLocale.set(Locale.ENGLISH); }

    void onActionFromFrench() { persistentLocale.set(Locale.FRENCH); }
    
    void onActionFromDeutsch() { persistentLocale.set(Locale.GERMAN); }
    
    
}
