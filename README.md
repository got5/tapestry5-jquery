# Tapestry 5 jQuery integration Module


## Features

This module provides jQuery integration for Tapestry and completely drop out Prototype, Scriptaculous and the base tapestry.js script. 
This project is based on [Tapestry5-ClientResources](http://github.com/got5/tapestry5-clientresources) project.

It also relies on jQuery 1.4.2 and jQuery UI 1.8. 

Theses components and features can still be used as it :

- Zone
- Form Validation
- AjaxFormLoop
- FormFragment
- TriggerFragment
- Grid (in place mode)

Due to some extensibility issues of core components, theses need to be used using a new namespace : jquery

- Autocomplete
- Datefield 
- Palette


## How to use it

Add the following lines in your application module class to enable JQuery:

    public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration)
    {
        configuration.add(ClientResourcesConstants.JAVASCRIPT_STACK, JQueryClientResourcesConstants.JAVASCRIPT_STACK_JQUERY);
    }


## Important notice

Please be aware that not every core components are working correctly at the moment. There is still lot of developments to do. 
All kind of feedback is very welcome. Please use Github issues system for that.
 

## Maven dependency

To use this plugin, add the following dependency in your `pom.xml`.

	<dependencies>
		...
		<dependency>
			<groupId>org.got5</groupId>
			<artifactId>tapestry5-jquery</artifactId>
			<version>1.0.0-SNAPSHOT</version>
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

* Twitter: [http://twitter.com/robinkomiwes]


## License

This project is distributed under Apache 2 License. See LICENSE.txt for more information.
