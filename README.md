# Tapestry 5 jQuery integration Module - 2.1-SNAPSHOT

## Changelog

- 2.1-SNAPSHOT : switch to Tapestry 5.2.4
- 1.1-SNAPSHOT : exclusives jQuery components
- 1.0-SNAPSHOT : initial releases

## Features

This module provides jQuery integration for Tapestry 5 and completely drop out Prototype, Scriptaculous and the base tapestry.js script. 

It also relies on [jQuery](http://jquery.com) 1.5 and [jQuery UI](http://jqueryui.com/) 1.8.

Exclusives jQuery components : 

- **Dialog** with **DialogLink** and **DialogAjaxLink**
	- based on [http://jqueryui.com/demos/dialog/](http://jqueryui.com/demos/dialog/)
- **Tabs** 
	- based on [http://jqueryui.com/demos/tabs/](http://jqueryui.com/demos/tabs/)	
- **Accordion** 
	- based on [http://jqueryui.com/demos/accordion/](http://jqueryui.com/demos/accordion/)	
- **AjaxUpload** 
	- based on [https://github.com/valums/file-uploader](https://github.com/valums/file-uploader)	
	
Exclusives jQuery Mixins :

- **Button** 
	- based on [http://jqueryui.com/demos/button/](http://jqueryui.com/demos/button/)	


Theses components were originally present in Tapestry 5 Core and can still be used as it :

- **Zone**
- **Form Validation** 
	- based on: [http://docs.jquery.com/Plugins/Validation](http://docs.jquery.com/Plugins/Validation)
- **AjaxFormLoop**
- **FormFragment**
- **TriggerFragment**
- **Grid (in place mode)**
- **DateField**
    - based on: [http://jqueryui.com/demos/datepicker/](http://jqueryui.com/demos/datepicker/)

Due to some extensibility issues of Core components, theses are originals components need to be used using the "jquery" namespace

- **Autocomplete**
	- based on: [http://jqueryui.com/demos/autocomplete/](http://jqueryui.com/demos/autocomplete/)
- **Palette**
 

## Maven dependency

To use this plugin, add the following dependency in your `pom.xml`.

	<dependencies>
		...
		<dependency>
			<groupId>org.got5</groupId>
			<artifactId>tapestry5-jquery</artifactId>
			<version>2.1.0-SNAPSHOT</version>
		</dependency>
		...
	</dependencies>
	
	<repositories>
		...
		<repository>
			<id>devlab722-repo</id>
			<url>http://nexus.devlab722.net/nexus/content/repositories/releases
			</url>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</repository>

		<repository>
			<id>devlab722-snapshot-repo</id>
			<url>http://nexus.devlab722.net/nexus/content/repositories/snapshots
			</url>
			<releases>
				<enabled>false</enabled>
			</releases>
		</repository>
		...
	</repositories>
	
## List Of Contributors
Robin Komiwes
Andreas Andreou
Christian Riedel
François Facon
Tom van Dijk

##Hosting
Christophe Furmaniak 	

## More Informations & contacts

* Twitter: [http://twitter.com/GOTapestry5](http://twitter.com/GOTapestry5)

## How to use it

Just add tapestry5-jquery to your classpath (see Maven dependency snippet below)!

Then use components like you would normally do. For Autocomplete and Palette use "jquery" namespace:
 
	<t:form>
        <t:jquery.autocomplete />
		<t:submit />
    </t:form>

Or add jquery to the tapestry-library namespace:

<html xmlns:t="http://tapestry.apache.org/schema/tapestry_5_1_0.xsd"
      xmlns:p="tapestry:parameter"
      xmlns:j="tapestry-library:jquery">

    <t:form>
        <j:autocomplete />
        <t:submit />
    </t:form>

</html>


## Important notice

All kind of feedback is very welcome. Please use [Github issues system](http://github.com/got5/tapestry5-jquery/issues) for that.

## License

This project is distributed under Apache 2 License. See LICENSE.txt for more information.
