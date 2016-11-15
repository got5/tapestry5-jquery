[![Build Status](https://travis-ci.org/got5/tapestry5-jquery.svg?branch=master)](https://travis-ci.org/got5/tapestry5-jquery)
# Tapestry 5 jQuery integration Module - 4.1.1

## Demo and documentation
http://tapestry5-jquery.com/

##Hosting
http://nexus.devlab722.net/nexus/content/repositories/releases will stop at the end November 2015, 
So please note that the latest releases for 5.4 and 5.3 are now available at [Maven Central](https://repo1.maven.org/maven2/org/got5/tapestry5-jquery/).   	

## Features

for version >= 4.0.0 
Since version 5.4, Tapestry supports jQuery and Prototype. So you don't need any more our Tapestry-jquery.js to use integrate your favorite jQuery plugin.     
Tapestry5-jquery module is now focusing on :
	- overriding some core component like Datefield 
	- providing a collections of jQuery components that play well with Tapestry 5.4 or higher.

for version < 4.0.0
  
This module provides jQuery integration for Tapestry 5 and allow you to work with or to replace the tapestry.js file based on prototype. 

Exclusive jQuery components : 

- **Accordion** 
	- based on [http://jqueryui.com/demos/accordion/](http://jqueryui.com/demos/accordion/)	
- **Carousel**
	- Based on [http://sorgalla.com/jcarousel/](http://sorgalla.com/jcarousel/)
- **CheckBox**
	- Based on [http://access.aol.com/csun2011](http://access.aol.com/csun2011)
- **Dialog** with **DialogLink** and **DialogAjaxLink**
	- based on [http://jqueryui.com/demos/dialog/](http://jqueryui.com/demos/dialog/)
- **FlexSlider** 
	- based on [https://github.com/woothemes/FlexSlider](https://github.com/woothemes/FlexSlider)	
- **InPlaceEditor** 
	- based on [http://www.appelsiini.net/projects/jeditable](http://www.appelsiini.net/projects/jeditable)
- **RangeSlider** 
	- based on [http://jqueryui.com/demos/slider/#range](http://jqueryui.com/demos/slider/#range)
- **Slider** 
	- based on [http://jqueryui.com/demos/slider/](http://jqueryui.com/demos/slider/)	
- **Superfish**
	- Based on [http://users.tpg.com.au/j_birch/plugins/superfish/](http://users.tpg.com.au/j_birch/plugins/superfish/)
- **Tabs** 
	- based on [http://jqueryui.com/demos/tabs/](http://jqueryui.com/demos/tabs/)	
- **Gallery** 
    - based on [http://colorpowered.com/colorbox/](http://colorpowered.com/colorbox/)
- **Jcrop** 
    - based on [http://github.com/tapmodo/Jcrop](http://github.com/tapmodo/Jcrop)
    
    
		
Exclusive jQuery Mixins :

- **Button** 
	- based on [http://jqueryui.com/demos/button/](http://jqueryui.com/demos/button/)
- **Bind** 
	- binds jQuery events to Tapestry pages/components and HTML elements. based on [http://api.jquery.com/bind/](http://api.jquery.com/bind/)
- **CustomDatepicker**
	- Based on [http://jqueryui.com/demos/datepicker](http://jqueryui.com/demos/datepicker)
- **CustomZone**
	- Based on [http://jqueryui.com/demos/effect/](http://jqueryui.com/demos/effect/)
- **Mask** 
	- based on [http://digitalbush.com/projects/masked-input-plugin/](http://digitalbush.com/projects/masked-input-plugin/)
- **Slider**
	- Based on [http://jqueryui.com/demos/slider](http://jqueryui.com/demos/slider)
- **Tooltip** 
	- based on [http://access.aol.com/csun2011/](http://access.aol.com/csun2011/)
	
Exclusive jQuery Binding Prefixes :

- **selector**
    - selector:id returns #clientId allows finding typos at java runtime instead of at javascript runtime


Theses following components are proposing an alternative implementation of some Tapestry 5 component :

- **DateField**
    - based on: [http://jqueryui.com/demos/datepicker/](http://jqueryui.com/demos/datepicker/)
- **Palette**
- **ProgressiveDisplay**

Theses components were originally present in Tapestry 5 jQuery 

- **Reveal** is now part of [tapestry5-foundation](https://github.com/got5/tapestry5-foundation)	

- **AjaxUpload** is now part of [tapestry5-jquery-upload](https://github.com/got5/tapestry5-jquery-upload)	
	
	

##Questions? Ideas? Comments?
All kind of feedback is very welcome. Please use [Github issues system](http://github.com/got5/tapestry5-jquery/issues) for that.

## More Informations & contacts

* The [wiki](https://github.com/got5/tapestry5-jquery/wiki)
* Twitter: [http://twitter.com/GOTapestry5](http://twitter.com/GOTapestry5)


## How to use it

Just  add the following dependency in your `pom.xml`.

For Tapestry 5.4 users:

	<dependencies>
		...
		<dependency>
			<groupId>org.got5</groupId>
			<artifactId>tapestry5-jquery</artifactId>
			<version>4.1.1</version>
		</dependency>
		...
	</dependencies>

	<repositories>
		...
		<repository>
          		<id>central</id>
          		<url>https://repo1.maven.org/maven2</url>
          		<releases>
            			<enabled>true</enabled>
          		</releases>
        	</repository>

		<repository>
			<id>oss—sonatype-snapshot-repo</id>
			<url>https://oss.sonatype.org/content/repositories/snapshots
			</url>
			<releases>
				<enabled>false</enabled>
			</releases>
		</repository>
		...
	</repositories>

For Tapestry 5.3 users:  

    <dependencies>
        ...
        <dependency>
            <groupId>org.got5</groupId>
            <artifactId>tapestry5-jquery</artifactId>
            <version>3.5.2</version>
        </dependency>
        ...
    </dependencies>
	
	<repositories>
		...
		<repository>
          		<id>central</id>
          		<url>https://repo1.maven.org/maven2</url>
          		<releases>
            			<enabled>true</enabled>
          		</releases>
        	</repository>

		<repository>
			<id>oss—sonatype-snapshot-repo</id>
			<url>https://oss.sonatype.org/content/repositories/snapshots
			</url>
			<releases>
				<enabled>false</enabled>
			</releases>
		</repository>
		...
	</repositories>

Then use components like you would normally do. For Autocomplete and Palette use "jquery" namespace:
 
	<t:form>
        <t:jquery.autocomplete />
		<t:submit />
    </t:form>

Or add jquery to the tapestry-library namespace:

	<html xmlns:t="http://tapestry.apache.org/schema/tapestry_5_4.xsd"
      xmlns:p="tapestry:parameter"
      xmlns:j="tapestry-library:jquery">

    <t:form>
        <j:palette />
        <t:textfield t:mixins="jquery/autocomplete" ... />
        <t:submit />
    </t:form>

	</html>

# Changelog related to Tapestry 5.4.x branch
* 4.1.1 : #372, #373, #375
* 4.1.0 : Update to Tapestry 5.4.1
* 4.0.0 : Update to Tapestry 5.4.0
* 4.0-rc-1 : Update to Tapestry 5.4-rc-1 & Upgrade libs
	* Jcrop from v0.9.12 to  v2.0.2
	* Colorbox from v1.4.27 (16/072013) to  v1.6.3 (27/07/2015)
	* FlexSlider from v2.2.0 to v2.5.0 
	* jeditable from v1.7.1 to v1.7.3 
	* contextMenu from ???  to v1.9.2 (2015)
	* raty from v2.5.2 (2010) to v2.7.0 (2015)
	* placeholder from v2.0.7 (2013) to v2.1.3 (2015)
	* masked input from v1.3.1 (2013) to v1.4.1 (2015)
	* jsrcollpane from v2.0.0beta12 (2012) to v2.0.22 (2015)
* 4.0-beta-35-SNAPSHOT : Update to Tapestry 5.4-beta-35 & Upgrade libs
	* superfish from v1.7.4 (2013) to v1.7.6 (2015)
* 4.0-beta-29 : Update to Tapestry 5.4-beta-29 & jQuery UI 1.11.3,#362, deployment to Maven Central 
* 4.0.1-beta-26 : Update to Tapestry 5.4-beta-26 , #353, #352
* 4.0.1-SNAPSHOT : Update to Tapestry 5.4-beta-22, #339
* 4.0.0-SNAPSHOT : Update to Tapestry 5.4-beta-2

# Changelog related to Tapestry 5.3.8 with jQuery 1.12.2 
* 3.5.2: Fix "Unable to locate asset" issue on ImportJQueryUIWorker  
* 3.5.1: #383 Reverse jQuery version to 1.12.2 to avoid jQuery issue 2432  
* 3.5.0: #380 Update to support jQuery 1.12.4 & jQuery UI 1.12.1  

# Changelog related to Tapestry 5.3.x with jQuery 1.10.2
* 3.4.3-SNAPSHOT #353, #352, #350 
* 3.4.2 (stable): Update to Tapestry 5.3.8 (Java 1.8) deployment to Maven Central
* 3.4.1 : #342, #336
* 3.4.0 : Upgrade libs
	* jQuery 1.10.2
	* jQueryUI 1.10.3
    * jcarousel 0.2.9
    * superfish 1.7.4
    * Colorbox 1.4.27
    * Masked Input plugin for jQuery 1.3.1
    * FlexSlider 2.2.0
    * Jcrop 0.9.12
    * Replaced jquery.placeholder.js with https://github.com/mathiasbynens/jquery-placeholder
    * [breaking change] Replaced codemirror.js with codemirror: http://codemirror.net

# Changelog related to Tapestry 5.3.x with jQuery 1.7.2
* 3.3.11 : (stable) backport  #263 deployment to Maven Central
* 3.3.10 : backport #311
* 3.3.9 : Update to Tapestry 5.3.8 (Java 1.8) #342, #336
* 3.3.8 : add EXCLUDE_CORE_JS_STACK symbol needed when more than one war is deployed in a portlet container.
* 3.3.7 : #304, #303, #302, #301, #299, #298, #297, #296, #295, #294, #291, #223
* 3.3.6 : #290, #287, #286, #285, #284, #283, #278, #274, #273, #272, #271, #270, #268, #260
* 3.3.1 : add GMap component
* 3.3.0 : Update to Tapestry 5.3.3
* 3.2.0 : Update to Tapestry 5.3.2
* 3.1.0 : Update to Tapestry 5.3.1
* 3.0.0 : Switch to Tapestry 5.3 (new JavaScript Layer)
	* add Components : Gallery

# Changelog related to Tapestry 5.2.x with jQuery 1.6.4
* 2.6.9 : last release for Tapestry 5.2.6
* 2.6.8 : add Components ImageCropper
* 2.6.6 : handle datatable's ajax mode for server-side pagination
* 2.6.2 : more work on client side validation
* 2.6.1 : 
	* improve Validation Mecanism and DataTable Component
	* add Components : InPlaceEditor, Draggable
	* add Mixins : ZoneRefresh, ZoneDroppable 
* 2.6.0 : 
	* switch to Tapestry 5.2.6
	* add Mixins : CustomZone, Widget
	* Other Mecanisms : EffectsParam, WidgetParams, Selector Binding
* 2.1.1 :  
	* added Components: Carousel, Checkbox, RangeSlide, Slider, Superfish 
	* added Mixins: CustomDatepicker, Mask, Reveal, Tooltip	
* 2.1.0 : switch to Tapestry 5.2.5
	* add (Tabs, Accordion, AjaxUpload, Button)	

* 1.1-SNAPSHOT : exclusive jQuery components
* 1.0-SNAPSHOT : initial releases  !

## License

This project is distributed under Apache 2 License. See LICENSE.txt for more information. 

## List Of Contributors
* Contributors: [https://github.com/got5/tapestry5-jquery/graphs/contributors](https://github.com/got5/tapestry5-jquery/graphs/contributors)

