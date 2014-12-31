//
// Copyright 2011 GOT5 (GO Tapestry 5)
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class ConfirmTest extends SeleniumTestCase {

	private final String confirmLocator = "//div[@id='dialogConfirmationJQuery']";

	private final String confirmOkLocator = "//div[@class='ui-dialog-buttonset']/button[1]";

	@Test
    public void testDialog()
    {
        open("/jqueryconfirm");

        //PageLink Confirmation test.
        checkDialogStateAfterClick("//a[@id='pageLinkTest']");

        //Did we quit the JQueryConfirm Page?
        final String baseUrl = getBaseURL();
        assertTrue(baseUrl.indexOf("/jqueryconfirm") == -1);

        //open("/jqueryconfirm");

        //Zone update confirmation
        //checkDialogStateAfterClick("//a[@id='clicker']");

        //Form submit confirmation
        //checkDialogStateAfterClick("//input[@id='btnValid']");
    }

	private void checkDialogStateAfterClick(final String triggerLocator)
    {
        click(triggerLocator);

        //Checks if the confirm popup is displayed.
        new Wait()
        {
            @Override
            public boolean until()
            {
                return (isElementPresent(confirmLocator));
            }
        }.wait("Confirm popup should be visible after clicking on : " + triggerLocator, JQueryTestConstants.TIMEOUT);

        //Confirm dialog popup closed by pressing Enter key.
        checkDialogStateAfterClickOK();
    }

	private void checkDialogStateAfterClickOK()
    {
		//Press OK button.
        click(confirmOkLocator);

        //Checks if the popup has been removed.
        new Wait()
        {
            @Override
            public boolean until()
            {
                return !isElementPresent(confirmLocator) || !isVisible(confirmLocator);
            }
        }.wait("Confirm popup should be removed after clicking on OK button.", JQueryTestConstants.TIMEOUT);
    }
}
