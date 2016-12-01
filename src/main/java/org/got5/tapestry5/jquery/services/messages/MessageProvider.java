package org.got5.tapestry5.jquery.services.messages;

import org.apache.tapestry5.ComponentResources;

/**
 * Service used to get messages from parent's components or pages, and to handle name convention Tapestry
 * system in property files (component_id-message_id).
 *
 */
public interface MessageProvider {

	/**
	 * Returns the message corresponding to the component id <i>pClientId</i> and the message key <i>pKey</i>. This function
	 * also needs the ComponentResources injected service instance from the page/component to work.
	 *
	 * @param pKey a key from the component’s messages
	 * @param pClientId the component’s client id
	 * @param pResources the component resources that will be searched for the message key
	 *
	 * @return the message or null
	 */
	String get(String pKey, String pClientId, ComponentResources pResources);
}
