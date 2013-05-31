package org.got5.tapestry5.jquery.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.Request;
import org.got5.tapestry5.jquery.entities.User;

public class ShowSource {
	@Property
	private User user;

	@Property
	private User currentUser;

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
	Object showDetail(String lastName)
	{
		if (!request.isXHR()) { return this; }

		for(User u : users){
			if(u.getLastName().equalsIgnoreCase(lastName))
				user= u;
		}

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
}
