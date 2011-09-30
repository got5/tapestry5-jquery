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

package org.got5.tapestry5.jquery.test.pages.test;

import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.util.EnumSelectModel;
import org.apache.tapestry5.util.EnumValueEncoder;
import org.got5.tapestry5.jquery.test.entities.SpecialHandling;

public class Palette
{
    @Persist
    private List<SpecialHandling> _handling;

    @Inject
    private Messages _messages;

    @Inject
    private TypeCoercer coercer;
    
    private final ValueEncoder<SpecialHandling> _encoder = 
    		new EnumValueEncoder<SpecialHandling>(coercer, SpecialHandling.class);

    private final SelectModel _model = 
    		new EnumSelectModel(SpecialHandling.class, _messages);

    public List<SpecialHandling> getHandling()
    {
	return _handling;
    }

    public void setHandling(List<SpecialHandling> handling)
    {
	_handling = handling;
    }

    public ValueEncoder<SpecialHandling> getEncoder()
    {
	return _encoder;
    }

    public SelectModel getModel()
    {
	return _model;
    }

}
