# Tapestry 5 jQuery integration Module - 1.1-SNAPSHOT

## Changelog

- 1.1-SNAPSHOT : exclusives jQuery components
- 1.0-SNAPSHOT : initial releases

## Features

This module provides jQuery integration for Tapestry and completely drop out Prototype, Scriptaculous and the base tapestry.js script. 
This project is based on [Tapestry5-ClientResources](http://github.com/got5/tapestry5-clientresources) project.

It also relies on [jQuery](http://jquery.com) 1.4.2 and [jQuery UI](http://jqueryui.com/) 1.8.

Exclusives jQuery components : 

- **Dialog** with **DialogLink** and **DialogAjaxLink**
	- based on [http://jqueryui.com/demos/dialog/](http://jqueryui.com/demos/dialog/)

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
- **LinkSubmit**

## How to use it

Just add tapestry5-jquery to your classpath (see Maven dependency snippet below)!

Then use components like you would normally do. For Autocomplete, Palette and LinkSubmit use "jquery" namespace:
 
	<t:form>
        <t:jquery.autocomplete />
		<t:submit />
    </t:form>


## Important notice

Please be aware that not every core components are working correctly at the moment. There is still lot of developments to do. 
All kind of feedback is very welcome. Please use [Github issues system](http://github.com/got5/tapestry5-jquery/issues) for that.
 

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

## More Informations & contacts

* Twitter: [http://twitter.com/robinkomiwes](http://twitter.com/robinkomiwes)


## License

This project is distributed under Apache 2 License. See LICENSE.txt for more information.
