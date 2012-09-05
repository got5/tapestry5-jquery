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

package org.got5.tapestry5.jquery.test.pages.docs.components;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONLiteral;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.Request;
import org.got5.tapestry5.jquery.components.InPlaceEditor;
import org.got5.tapestry5.jquery.test.entities.User;
import org.got5.tapestry5.jquery.utils.JQueryTabData;


public class DocsInPlaceEditor
{
	public List<JQueryTabData> getListTabData()
	{
		List<JQueryTabData> listTabData = new ArrayList<JQueryTabData>();
        listTabData.add(new JQueryTabData("Example","example"));
        return listTabData;
    }
	
@Property
private User user;
    
@Property
private int currentIndex;
    
@Property
@Persist
private List<User> users;

@Component
private Zone detailZone;
    
@Inject
private BeanModelSource _beanModelSource;
    
@Inject
private ComponentResources _componentResources;
    
@Inject
private Request request;

void setupRender() {
	users = createUsers(50);
}

public BeanModel getMyModel(){
	BeanModel myModel = _beanModelSource.createDisplayModel(User.class, 
				_componentResources.getMessages());
	myModel.add("action", null);
	myModel.include("firstName", "lastName", "action");
	myModel.get("firstName").sortable(false);
	myModel.get("lastName").label("Surname");
	return myModel;
}
    
@OnEvent(value = EventConstants.ACTION)
Object showDetail(int index)
{
	if (!request.isXHR()) { return this; }
    user= (User)users.get(index);
    return detailZone;
}
    
public JSONObject getDialogParam()
{
	JSONObject param = new JSONObject();
	param.put("width", 400);
	return param;
}
    
private User createUser(int i)
{
	User u = new User();
    u.setAge(i);
    u.setFirstName("Humpty" + i + 10);
    u.setLastName("Dumpty" + i + 200);
    return u;
}

private List<User> createUsers(int number)
{
	List<User> users = new ArrayList<User>();

    for (int i = 0; i < number; i++)
    {
    	users.add(createUser(i));
    }

    return users;
}

@OnEvent(component = "inPlaceEditor", value = InPlaceEditor.SAVE_EVENT)
void actionFromEditor(Long id, String value)
{
	User user = (User)users.get(id.intValue());
	user.setLastName(value);
	System.err.println("User #" + id + " changed to '" + value + "'");
}


@InjectComponent
private Zone updateZone;

/**
 * <p>
 * JSON parameter used to configure InPlaceEditor callback
 * </p>
 */
public JSONObject getOptionsJSON()
{	
	JSONObject params=new JSONObject();
	Object [] context = new Object [] {currentIndex};
	String listenerURI = _componentResources.createEventLink("refresh", context).toAbsoluteURI(false);
    String zoneID = updateZone.getClientId();
    params.put("tooltip", "Cliquer pour �diter");
	params.put("callback", new JSONLiteral("function(value, settings)" +
						   "{" +
						   " var zoneElement = $('#"+zoneID+"');" +
						   " zoneElement.tapestryZone('update',{url : '"+listenerURI+"',params : {id:"+currentIndex+"} });" +
						   "}"));
return params;
}

@OnEvent(value = "refresh")
public Object refresh(Long id) {
		user = (User)users.get(id.intValue());
		return updateZone.getBody();
	
}





}
