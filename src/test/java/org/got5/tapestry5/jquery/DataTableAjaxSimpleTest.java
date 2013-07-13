package org.got5.tapestry5.jquery;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Wait;

public class DataTableAjaxSimpleTest extends SeleniumTestCase {

    @Test
    public void testHeaderOverride() {
        this.open("/DataTablesAjaxSimple");

        new Wait() {

            @Override
            public boolean until() {
                return DataTableAjaxSimpleTest.this.getText("//table[@id='datatableAjax']/thead/tr/th[2]/div[@class='DataTables_sort_wrapper']")
                                                   .contains("Header");
            }
        }.wait("The Header is wrong : "
                   + this.getText("//table[@id='datatableAjax']/thead/tr/th[2]/div[@class='DataTables_sort_wrapper']"),
               JQueryTestConstants.TIMEOUT);
    }

    @Test
    public void testFooterOverride() {
        this.open("/DataTablesAjaxSimple");

        new Wait() {

            @Override
            public boolean until() {
                return DataTableAjaxSimpleTest.this.getText("//table[@id='datatableAjax']/tfoot/tr/th[2]")
                                                   .contains("Footer");
            }
        }.wait("The Footer is wrong : " + this.getText("//table[@id='datatableAjax']/tfoot/tr/th[2]"),
               JQueryTestConstants.TIMEOUT);
    }

    @Test
    public void testNumberRows5() {
        this.open("/DataTablesAjaxSimple");

        new Wait() {

            @Override
            public boolean until() {
                return DataTableAjaxSimpleTest.this.getXpathCount("//table[@id='datatableAjax']/tbody/tr").equals(5);
            }
        }.wait("We should have 5 rows", JQueryTestConstants.TIMEOUT);

        this.checkNumberPage(3);
    }

    @Test(dependsOnMethods = "testNumberRows5")
    public void testNumberRows10() {
        this.open("/DataTablesAjaxSimple");

        this.select("//select[@name='datatableAjax_length']", "label=10");

        this.focus("//body");

        new Wait() {

            @Override
            public boolean until() {
                return DataTableAjaxSimpleTest.this.getXpathCount("//table[@id='datatableAjax']/tbody/tr").equals(10);
            }
        }.wait("We should have 10 rows", JQueryTestConstants.TIMEOUT);

        this.checkNumberPage(2);

        this.select("//select[@name='datatableAjax_length']", "label=5");

        this.focus("//body");
    }

    @Test(dependsOnMethods = "testNumberRows10")
    public void testFilter() {
        this.open("/DataTablesAjaxSimple");
        this.type("//div[@id='datatableAjax_filter']/label/input[@type='text']", "Ka");
        this.keyUp("//div[@id='datatableAjax_filter']/label/input[@type='text']", "f");

        new Wait() {

            @Override
            public boolean until() {
                return DataTableAjaxSimpleTest.this.getXpathCount("//table[@id='datatableAjax']/tbody/tr").equals(1);
            }
        }.wait("The filter does not work", JQueryTestConstants.TIMEOUT);
        //reset filter
        this.type("//div[@id='datatableAjax_filter']/label/input[@type='text']", "");
        this.keyUp("//div[@id='datatableAjax_filter']/label/input[@type='text']", " ");
        new Wait() {

            @Override
            public boolean until() {
                return !DataTableAjaxSimpleTest.this.getXpathCount("//table[@id='datatableAjax']/tbody/tr").equals(1);
            }
        }.wait("The reset filter does not work", JQueryTestConstants.TIMEOUT);
    }

    @Test
    public void testData() {
        this.open("/DataTablesAjaxSimple");
        this.waitForPageToLoad();

        //click on first page
        this.click("datatableAjax_first");
        final String expectedText = "Clinton";
        new Wait() {

            @Override
            public boolean until() {
                return DataTableAjaxSimpleTest.this.getText("//table[@id='datatableAjax']").contains(expectedText);
            }
        }.wait("Expected text is missing [" + expectedText + "]", JQueryTestConstants.TIMEOUT);

        //click on next page
        this.click("datatableAjax_next");
        final String expectedText2 = "Pascal";
        new Wait() {

            @Override
            public boolean until() {
                return DataTableAjaxSimpleTest.this.getText("//table[@id='datatableAjax']").contains(expectedText2);
            }
        }.wait("Expected text is missing [" + expectedText2 + "]", JQueryTestConstants.TIMEOUT);
    }

    private void checkDisable(final int i) {
        new Wait() {

            @Override
            public boolean until() {
                return DataTableAjaxSimpleTest.this.getAttribute("//div[@id='datatableAjax_paginate']/span/a[contains(@class,'fg-button')]["
                                                                     + i + "]@class").contains("ui-state-disabled");
            }
        }.wait("Page " + i + " should be disabled", JQueryTestConstants.TIMEOUT);
    }

    private void checkNumberPage(final int i) {
        new Wait() {

            @Override
            public boolean until() {
                return DataTableAjaxSimpleTest.this.getXpathCount("//div[@id='datatableAjax_paginate']/span/a[contains(@class,'fg-button')]")
                                                   .equals(i);
            }
        }.wait("We should have " + i + " pages, instead we have "
                   + this.getXpathCount("//div[@id='datatableAjax_paginate']/span/span[contains(@class,'fg-button')]"),
               JQueryTestConstants.TIMEOUT);
    }

}
