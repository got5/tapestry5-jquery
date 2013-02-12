package org.got5.tapestry5.jquery.services.impl;

import org.apache.tapestry5.alerts.Alert;
import org.apache.tapestry5.alerts.AlertStorage;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.ioc.services.PerThreadValue;
import org.apache.tapestry5.ioc.services.PerthreadManager;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.ajax.JavaScriptCallback;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.services.JGrowlManager;

public class JGrowlManagerImpl implements JGrowlManager
{
 private final ApplicationStateManager asm;

 private final Request request;

 private final AjaxResponseRenderer ajaxResponseRenderer;

 private final PerThreadValue<Boolean> needAlertStorageCleanup;

 public JGrowlManagerImpl(ApplicationStateManager asm, Request request, AjaxResponseRenderer ajaxResponseRenderer, PerthreadManager perThreadManager)
 {
     this.asm = asm;
     this.request = request;
     this.ajaxResponseRenderer = ajaxResponseRenderer;

     needAlertStorageCleanup = perThreadManager.createValue();
 }

 /* (non-Javadoc)
 * @see org.got5.tapestry5.jquery.services.impl.JGrowlManager#success(java.lang.String)
 */
public void success(String message)
 {
     alert(Duration.SINGLE, Severity.SUCCESS, message);
 }
 
 /* (non-Javadoc)
 * @see org.got5.tapestry5.jquery.services.impl.JGrowlManager#info(java.lang.String)
 */
public void info(String message)
 {
     alert(Duration.SINGLE, Severity.INFO, message);
 }

 /* (non-Javadoc)
 * @see org.got5.tapestry5.jquery.services.impl.JGrowlManager#warn(java.lang.String)
 */
public void warn(String message)
 {
     alert(Duration.SINGLE, Severity.WARN, message);
 }

 /* (non-Javadoc)
 * @see org.got5.tapestry5.jquery.services.impl.JGrowlManager#error(java.lang.String)
 */
public void error(String message)
 {
     alert(Duration.SINGLE, Severity.ERROR, message);
 }

 /* (non-Javadoc)
 * @see org.got5.tapestry5.jquery.services.impl.JGrowlManager#alert(org.apache.tapestry5.alerts.Duration, org.apache.tapestry5.alerts.Severity, java.lang.String)
 */
public void alert(Duration duration, Severity severity, String message)
 {
     final Alert alert = new Alert(duration, severity, message);

     if (request.isXHR())
     {
         addCallbackForAlert(alert);
     }

     // Add it to the storage; this is always done, even in an Ajax request, because we may end up
     // redirecting to a new page, rather than doing a partial page update on the current page ... in which
     // case we need the alerts stored persistently until we render the new page.

     getAlertStorage().add(alert);
 }

 private void addCallbackForAlert(final Alert alert)
 {
     ajaxResponseRenderer.addCallback(new JavaScriptCallback()
     {
         public void run(JavaScriptSupport javascriptSupport)
         {
             javascriptSupport.require("tjq/jgrowl").with(alert.toJSON());
         }
     });

     addAlertStorageCleanupCallback();
 }

 private void addAlertStorageCleanupCallback()
 {
     // Add a callback that exists just to clear the non-persistent alerts.
     // Only one of these is needed.

     if (needAlertStorageCleanup.get(true))
     {
         ajaxResponseRenderer.addCallback(new JavaScriptCallback()
         {
             public void run(JavaScriptSupport javascriptSupport)
             {
                 // In an Ajax request, the Alerts are added, just so that they can be removed if not persistent.
                 // Again, this is for the rare case where there's a redirect to another page.

                 getAlertStorage().dismissNonPersistent();
             }
         });

         needAlertStorageCleanup.set(false);
     }
 }

 private AlertStorage getAlertStorage()
 {
     return asm.get(AlertStorage.class);
 }

}
