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

package org.got5.tapestry5.jquery.test;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class DialogTest extends SeleniumTestCase {

	@Test
    public void testDialog()
    {
        open("/test/jquerydialog");

        String dialogLink = "identifier=dialoglink";
        String dialogAjaxLink = "identifier=dialogajaxlink";
        final String dialog = "identifier=myDialog";
        String closeDialog = "css=span[class~=\"ui-icon-closethick\"]";
        String zone = "identifier=myzone";

        checkDialogState(dialogLink, dialog, true);

        checkDialogState(closeDialog, dialog, false);

        checkDialogState(dialogAjaxLink, dialog, true);

        final String textFirstState = getText(dialog);

        checkDialogState(closeDialog, dialog, false);

        checkDialogState(dialogAjaxLink, dialog, true);

        final String textSecondState = getText(dialog);

       checkDialogState(closeDialog, dialog, false);
    }
	
	private void checkDialogState(String triggerLocator, final String dialogLocator, final boolean state)
    {
        click(triggerLocator);

        new Wait()
        {
            @Override
            public boolean until()
            {
                return (isVisible(dialogLocator) == state);
            }
        }.wait(dialogLocator + " visibility should be " + state, JQueryTestConstants.TIMEOUT);

        assertEquals(isVisible(dialogLocator), state, dialogLocator + " visibility should be " + state);
    }
}
