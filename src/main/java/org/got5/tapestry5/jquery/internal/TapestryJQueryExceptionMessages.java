package org.got5.tapestry5.jquery.internal;

import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.internal.util.MessagesImpl;

public final class TapestryJQueryExceptionMessages {
	
	private static final Messages MESSAGES = MessagesImpl.forClass(TapestryJQueryExceptionMessages.class);

    private TapestryJQueryExceptionMessages()
    {
    }

    public static String importJQueryUiMissingValue()
    {
        return MESSAGES.get("importJQueryUi-missing-value");
    }
}
