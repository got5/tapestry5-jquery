//
// Copyright 2010 GOT5 (Gang Of Tapestry 5)
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

import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class JQueryTest extends JavascriptTestSuite
{

    public JQueryTest()
    {
    	//chrome on macosx
        //super("src/test/jquery/webapp", "*googlechrome /Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
        //super("src/test/jquery/webapp", "*googlechrome");
    	//safari on macosx
    	//super("src/test/jquery/webapp", "*safari /Applications/Safari.app/Contents/MacOS/Safari");
    	//super("src/test/jquery/webapp", "*safari");
    	//firefox on macosx
    	//super("src/test/jquery/webapp", "*firefox /Applications/Firefox.app/Contents/MacOS/firefox-bin");
    	//super("src/test/jquery/webapp", "*firefox");
    	//super("src/test/jquery/webapp", "*iehta");
    }

    @Override
    protected String getValidationElementLocator(String fieldId)
    {
        return "css=label[for=\"" + fieldId + "\"][generated=\"true\"]";
    }

    @Override
    public void additionalValidationTest()
    {
        // nothing to do
    }

    @Override
    public String getCalendarPage()
    {
        return "/jquerycalendar";
    }

    @Override
    public String getCalendarDivSelector()
    {
        return "identifier=ui-datepicker-div";
    }

    @Override
    public String getCalendarField()
    {
        return "identifier=datefield";
    }

    @Override
    public String getAutocompleteDivSelector()
    {
        return "css=ul[class~=\"ui-autocomplete\"]";
    }

    @Override
    public String getAutocompleteField()
    {
        return "identifier=foo";
    }

    @Override
    public String getAutocompletePage()
    {
        return "/jqueryautocomplete";
    }

    @Override
    public String getPalettePage()
    {
        return "/jquerypalette";
    }

    @Override
    public String getLinkSubmitPage()
    {
        return "/jquerylinksubmit";
    }

    /**
     * jQuery exclusives components
     */
    @Test
    public void testDialog()
    {
        open("/jquerydialog");

        String dialogLink = "identifier=dialoglink";
        String dialogAjaxLink = "identifier=dialogajaxlink";
        final String dialog = "identifier=myDialog";
        String closeDialog = "css=span[class~=\"ui-icon-closethick\"]";
        String zone = "identifier=myzone";

        checkDialogState(dialogLink, dialog, true);

        checkDialogState(closeDialog, dialog, false);

        checkDialogState(dialogAjaxLink, dialog, true);

        String textFirstState = getText(dialog);

        checkDialogState(closeDialog, dialog, false);

        checkDialogState(dialogAjaxLink, dialog, true);

        String textSecondState = getText(dialog);

        checkDialogState(closeDialog, dialog, false);

        assertTrue(!textFirstState.equals(textSecondState), "Zone content should be different");
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
        }.wait(dialogLocator + " visibility should be " + state);

        assertEquals(isVisible(dialogLocator), state, dialogLocator + " visibility should be " + state);
    }

    @Test
    public void testAjaxUpload()
    {
        open("/AjaxUploadTest");

        final long start = System.currentTimeMillis();

        /*
         * As long as seleniums waits here you can test the component manually :)
         */
        new Wait()
        {
            @Override
            public boolean until()
            {
                return start + 60000 < System.currentTimeMillis();
            };
        }.wait("waited for a long time...", 120000);
    }
}
