package org.got5.tapestry5.jquery.services;

import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;

public interface JGrowlManager {

	public abstract void success(String message);

	public abstract void info(String message);

	public abstract void warn(String message);

	public abstract void error(String message);

	public abstract void alert(Duration duration, Severity severity,
			String message);

}