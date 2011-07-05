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
        return "/test/calendar";
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
        return "/test/jqueryautocomplete";
    }

    @Override
    public String getPalettePage()
    {
        return "/test/jquerypalette";
    }

    @Override
    public String getLinkSubmitPage()
    {
        return "/test/jquerylinksubmit";
    }

    @Override
    public String getTabsPage()
    {
        return "/test/jquerytabs";
    }
    
    
    /**
     * jQuery exclusives components
     */
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
        open("/test/AjaxUploadTest");

        final long start = System.currentTimeMillis();

        /*
         * As long as seleniums waits here you can test the component manually :)
         */
        new Wait()
        {
            @Override
            public boolean until()
            {
                return start + 2000 < System.currentTimeMillis();
            };
        }.wait("waited for a long time...", 3000);
    }
    
    @Test
    public void testAccordion()
    {
        open("/test/jqueryaccordion");
        // active tab must be second
        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=html body div.wrapper div#content div#accordion.ui-accordion div.ui-accordion-content-active h3").equals("Element 2");
            }
        }.wait("element not found!", 5000l);
       
          //click on first tab
        click("xpath=/html/body/div/div/div/h3/a");

        new Wait()
        {
            @Override
            public boolean until()
            {
                return getText("css=html body div.wrapper div#content div#accordion.ui-accordion div.ui-accordion-content-active h3").equals("Element 1");
            }
        }.wait("element not found!", 5000l);

       
    }
    
    /**
     * Test Method for Tooltip mixin
     */
    @Test
    public void testToolTip()
    {
    	open("/test/Tooltip");
    	
    	mouseOver("//div[@id='content']/a");
    	
    	
        new Wait()
        {
            @Override
            public boolean until()
            {
                return getAttribute("//div[@id='ui-tooltip-0']@aria-hidden").equals("false");
            }
        }.wait("The tooltip is not visible", 5000l);
    	
    	mouseOut("//div[@id='content']/a");
    	
        new Wait()
        {
            @Override
            public boolean until()
            {
                return getAttribute("//div[@id='ui-tooltip-0']@aria-hidden").equals("true");
            }
        }.wait("The tooltip is visible!", 5000l);
        
    }
    
    /**
     * Test Method for Mask mixin
     */
    @Test
    public void testMask(){
    	
    	open("/test/mask");
    	
    	click("//a[@id='myTestLink']");
    	
    	assertTrue(getValue("//input[@id='monTextField']").contains("1"));
    	
    	click("//a[@id='myTestLink2']");
    	
    	assertFalse(getValue("//input[@id='monTextField']").contains("a"));
    	
    }
    
    @Test
    public void testRevealMixin(){
    	
    	open("/test/reveal");
    	
    	click("//a[@id='pagelink']");
    	
    	 new Wait()
         {
             @Override
             public boolean until()
             {
                 return getAttribute("//div[@class='reveal-modal']@style").contains("visible");
             }
         }.wait("The reveal window is not visible", 5000l);
         
         click("//div[@class='reveal-modal-bg']");
         
        
    	 new Wait()
         {
             @Override
             public boolean until()
             {
                 return getAttribute("//div[@class='reveal-modal']@style").contains("hidden");
             }
         }.wait("The reveal window visible", 5000l);
    	
    }
    
    @Test
    public void testCheckboxComponent(){
    	
    	open("/test/Checkbox");
    	
    	new Wait()
        {
            @Override
            public boolean until()
            {
                return getAttribute("//form[@id='monForm']/fieldset/div/div/span@class").equals("ui-checkbox-icon");
            }
        }.wait("The checkbox should be unchecked", 5000l);
    	
       click("//form[@id='monForm']/fieldset/div/label");
        
        new Wait()
        {
            @Override
            public boolean until()
            {
            	return getAttribute("//form[@id='monForm']/fieldset/div/div/span@class").equals("ui-checkbox-icon ui-icon ui-icon-check");
            }
        }.wait("The checkbox should be checked", 5000l);
    }
    
    @Test
    public void testSuperfishComponent(){
    	
    	open("/test/SuperFish");
    	
    	new Wait()
        {
            @Override
            public boolean until()
            {
                return isElementPresent("//ul[@id='menu1'][contains(@class,'sf-menu')]");
            }
        }.wait("The ul element should have the sf-menu class : " + getAttribute("//ul[@id='menu1']@class"), 5000l);
        
        click("//ul[@id='menu1']/li[1]/a");
    	
        new Wait()
        {
            @Override
            public boolean until()
            {
                return getAttribute("//ul[@id='menu1']/li[1]@class").contains("sfHover");
            }
        }.wait("The ul element should have the sfHover class : " + getAttribute("//ul[@id='menu1'][contains(@class,'sf-menu')]/li[1]@class"), 5000l);
        
        click("//ul[@id='menu1']/li[2]/a");
        
        new Wait()
        {
            @Override
            public boolean until()
            {
                return !getAttribute("//ul[@id='menu1']/li[1]@class").contains("sfHover");
            }
        }.wait("The ul element should not have the sfHover class : " + getAttribute("//ul[@id='menu1'][contains(@class,'sf-menu')]/li[1]@class"), 5000l);
    }
    
    @Test
    public void testAjaxFormLoop(){
    	
    	open("/test/AjaxFormLoop");
    	
    	click("//a[@id='addrowlink']");
    	
    	new Wait()
        {
            @Override
            public boolean until()
            {
                return getXpathCount("//div[contains(@id,'rowInjector_')]").equals(1);
            }
        }.wait("A New row should be present " + getXpathCount("//div[contains(@id,'rowInjector_')]"), 5000l);
        
        click("//a[@id='addrowlink']");
    	
    	new Wait()
        {
            @Override
            public boolean until()
            {
                return getXpathCount("//div[contains(@id,'rowInjector_')]").equals(2);
            }
        }.wait("A New row should be present " + getXpathCount("//div[contains(@id,'rowInjector_')]"), 5000l);
        
        click("//a[contains(@id,'removerowlink_')][1]");
    	
    	new Wait()
        {
            @Override
            public boolean until()
            {
                return getXpathCount("//div[contains(@id,'rowInjector_')]").equals(1);
            }
        }.wait("A New row should be present " + getXpathCount("//div[contains(@id,'rowInjector_')]"), 5000l);
    	
    }
}
