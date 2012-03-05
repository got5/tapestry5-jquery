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

package org.got5.tapestry5.jquery.pages;

import java.util.Date;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.DateField;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

public class CalendarInAjaxForm {


	@Property
	private String _firstName;

	@Property
	private String _lastName;
	
	@Property
    private Date _birthday;
	
	@Component
	private Form _ajaxForm;
    
    @Component(id = "birthday",parameters ={"validate=required"})
	private DateField df;
    
    @Component(id = "firstName")
	private TextField _firstNameField;
    
    @Component(id = "lastName")
	private TextField _lastNameField;

	@Component(id="nameZone")
	private Zone _nameZone;
	
	@Component(id="formZone")
	private Zone _formZone;
	
	@Inject
	private AjaxResponseRenderer renderer;
	
	// The code
	
	void setupRender() {
		if (_firstName == null && _lastName == null) {
			_firstName = "Humpty";
			_lastName = "Dumpty";
		}
	}
	
	void onValidateForm() {
		
		if (_firstName == null || _firstName.trim().equals("")) {
			_ajaxForm.recordError(_firstNameField, "First Name is required.");
		}
		if (_lastName == null || _lastName.trim().equals("")) {
			_ajaxForm.recordError(_lastNameField, "Last Name is required.");
		}
		Date now = new Date(); 
		if(_birthday.after(now))
		{
			_ajaxForm.recordError(df, "invalid birthday");
		}
	}


	void onSuccess() {
		renderer.addRender(_nameZone).addRender(_formZone);
	}
	
	void onFailure() {
		renderer.addRender(_formZone).addRender(_nameZone);
	}

	public String getName() {
		return _firstName + " " + _lastName;
	}


}
