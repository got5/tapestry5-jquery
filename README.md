# Tapestry 5 jQuery integration Module

## How to

This module provides jQuery integration for Tapestry.

Actually, theses components are integrated as it

- Zone
- Form Validation
- Ajax Form Loop
- Grid (in place mode)


Theses components need to be used using a new namespace:

- jquery/autocomplete
- jquery/datefield 


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

* Twitter: http://twitter.com/robinkomiwes


## License

This project is distributed under Apache 2 License. See LICENSE.txt for more information.
