package org.got5.tapestry5.jquery.internal;

/**
 * see Issue #285 : do not use the environment to give access to the current DataTable's row and rowIndex parameters
 * This is a naive fake binding implementation to allow access to the container setters.
 */
public interface FakeInheritedBinding {

	/**
	 * Updates the current value.
	 *
	 * @param value
	 */
	void set(Object value);

}
