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

package org.got5.tapestry5.jquery.entities;

import java.util.Date;

import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.beaneditor.Width;

public class Phone
{

    @NonVisual
    private long id;

    private Person person;

    private PhoneType type;
    
    private Date startDate;


	@Width(20)
    @Validate("required,maxlength=20")
    private String number;

    public long getId()
    {
	return id;
    }

    public void setId(long id)
    {
	this.id = id;
    }

    public Person getPerson()
    {
	return person;
    }

    public void setPerson(Person person)
    {
	this.person = person;
    }

    public PhoneType getType()
    {
	return type;
    }

    public void setType(PhoneType type)
    {
	this.type = type;
    }

    public String getNumber()
    {
	return number;
    }

    public void setNumber(String number)
    {
	this.number = number;
    }
    
    public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
}