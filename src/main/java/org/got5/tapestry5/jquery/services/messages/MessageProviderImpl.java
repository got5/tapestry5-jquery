package org.got5.tapestry5.jquery.services.messages;

import org.apache.tapestry5.ComponentResources;

public class MessageProviderImpl implements MessageProvider {

	//Const used to identify missing key messages.
	private final String MISSING_MSG = "[[missing key";

	public MessageProviderImpl()
	{
	}

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
