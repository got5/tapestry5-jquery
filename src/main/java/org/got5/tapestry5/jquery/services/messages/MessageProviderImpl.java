package org.got5.tapestry5.jquery.services.messages;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.runtime.Component;

public class MessageProviderImpl implements MessageProvider {
	
	//Const used to identify missing key messages.
	private final String MISSING_MSG = "[[missing key";
	
	public MessageProviderImpl() 
	{
	}
	
	/**
	 * Returns the message corresponding to the component id <i>pClientId</i> and the message key <i>pKey</i>. This function
	 * also needs the ComponentResources injected service instance from the page/component to work.
	 * 
	 * @param String pKey
	 * @param String pClientId
	 * @param ComponentResources pResources
	 * 
	 * @return String
	 */
	public String get(String pKey, String pClientId, ComponentResources pResources) 
	{
		if (pResources != null) 
		{
			//First is checked if a property key is defined in the parent page or upper with following syntax : id-key
			String message = pResources.getPage().getComponentResources().getMessages().get(pClientId + "-" + pKey);
			if (message.indexOf(MISSING_MSG) > -1)
			{
				//Then, if nothing is found, we check the message catalog for the key only.
				message = pResources.getMessages().get(pKey);
			}
			return message;
		}
		return null;
	}

}
