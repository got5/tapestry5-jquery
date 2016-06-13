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

public class CustomDatepickerTest extends SeleniumTestCase {

	@Test
	public void testCustomDatepicker() {

		open("/customdatepicker");

		new Wait() {
			@Override
			public boolean until() {
				return isElementPresent("//button[@class='ui-datepicker-trigger']");
			}
		}.wait("element not found!");

		click("//button[@class='ui-datepicker-trigger']");

		new Wait() {
			@Override
			public boolean until() {
				return (getAttribute("//div[@id='ui-datepicker-div']@style") != null);
			}
		}.wait("Your calendar should be visible.", JQueryTestConstants.TIMEOUT);
		
		//check Previous button message 
		new Wait() {
	            @Override
	            public boolean until()
	            {
	                return getText("//div[@id='ui-datepicker-div']/div/a[1]/span").equals("See Previous Month");
	            }
		}.wait("prevText should be modified", JQueryTestConstants.TIMEOUT);
		
		click("//button[@class='ui-datepicker-trigger']");

		new Wait() {
			@Override
			public boolean until() {
				return getAttribute("//div[@id='ui-datepicker-div']@style")
						.contains("DISPLAY: none;")
						|| getAttribute("//div[@id='ui-datepicker-div']@style")
								.contains("none");
			}
		}.wait("Your calendar should not be visible."
				+ getAttribute("//div[@id='ui-datepicker-div']@style"),
				JQueryTestConstants.TIMEOUT);

	}
}
