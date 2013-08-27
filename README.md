# Tapestry 5 jQuery integration Module - 3.3.7

## Demo and documentation
http://tapestry5-jquery.com/

## List Of Contributors
- Robin KOMIWES as robink
- Andreas ANDREOU as andyhot
- Christian RIEDEL as criedel
- François FACON as got5     
- Tom van DIJK as trolando
- Emmanuel DEMEY as Gillespie59
- Clément USTER as cluster
- Amaury WILLEMANT as awillemant
- Barry BOOKS as trsvax
- Pablo NUSSEMBAUM as bauna
- Alexander OBLOVATNIY as oblalex
- Stéfanie DUPREY as Nephtys
- Michael ASPETSBERGER as maspetsberger
- Dragan SAHPASKI as dragansah
- Matias BLASI as mblasi
- Jon-Carlos Rivera as imbcmdth
- Nourredine KHADRI as adaptivui
- Michael GENTRY as mrg
- Mikhail NASYROV as mnasyrov
- Dmitry GUSEV as dmitrygusev
- Laurent WROBLEWSKI as LWroblewski
- Andreas Fink as fnk
- jochenberger
- ddelangle
- Iceo
- kaililleby
- daveyx


##Hosting
Christophe Furmaniak 	


##Questions? Ideas? Comments?
http://groups.google.com/group/tapestry5-jquery

## Changelog
- 3.4.0 : Upgrade libs
            jQuery 1.10.2
            jQueryUI 1.10.3
            jcarousel 0.2.9
            superfish 1.7.4
            Colorbox 1.4.27
            Masked Input plugin for jQuery 1.3.1

- 3.3.7 : #304, #303, #302, #301, #299, #298, #297, #296, #295, #294, #291, #223
- 3.3.6 : #290, #287, #286, #285, #284, #283, #278, #274, #273, #272, #271, #270, #268, #260
- 3.3.1 : add GMap component
- 3.3.0 : Update to Tapestry 5.3.3
- 3.2.0 : Update to Tapestry 5.3.2
- 3.1.0 : Update to Tapestry 5.3.1
- 3.0.0 : Switch to Tapestry 5.3 (new JavaScript Layer)
          add Components : Gallery
- 2.6.6 : handle datatable's ajax mode for server-side pagination
- 2.6.2 : more work on client side validation
- 2.6.1 : improve Validation Mecanism and DataTable Component
		  add Components : InPlaceEditor, Draggable
		  add Mixins : ZoneRefresh, ZoneDroppable 
- 2.6.0 : switch to Tapestry 5.2.6
		  add Mixins : CustomZone, Widget
		  Other Mecanisms : EffectsParam, WidgetParams, Selector Binding
- 2.1.1 : add Components : Carousel, Checkbox, RangeSlide, Slider, Superfish 
		  add Mixins     : CustomDatepicker, Mask, Reveal, Tooltip	
- 2.1.0 : switch to Tapestry 5.2.5
		  add (Tabs, Accordion, AjaxUpload, Button)	
- 1.1-SNAPSHOT : exclusive jQuery components
- 1.0-SNAPSHOT : initial releases  !

## Features

This module provides jQuery integration for Tapestry 5 and completely drop out Prototype, Scriptaculous and the base tapestry.js script. 

It also relies on [jQuery](http://jquery.com) 1.8.2 and [jQuery UI](http://jqueryui.com/) 1.8.24

Exclusive jQuery components : 

- **Accordion** 
	- based on [http://jqueryui.com/demos/accordion/](http://jqueryui.com/demos/accordion/)	
- **AjaxUpload** 
	- based on [https://github.com/valums/file-uploader](https://github.com/valums/file-uploader)	
- **Carousel**
	- Based on [http://sorgalla.com/jcarousel/](http://sorgalla.com/jcarousel/)
- **CheckBox**
	- Based on [http://access.aol.com/csun2011](http://access.aol.com/csun2011)
- **Dialog** with **DialogLink** and **DialogAjaxLink**
	- based on [http://jqueryui.com/demos/dialog/](http://jqueryui.com/demos/dialog/)
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
- **Reveal**
	- Based on [http://www.zurb.com/playground/reveal-modal-plugin](http://www.zurb.com/playground/reveal-modal-plugin)	
- **Slider**
	- Based on [http://jqueryui.com/demos/slider](http://jqueryui.com/demos/slider)
- **Tooltip** 
	- based on [http://access.aol.com/csun2011/](http://access.aol.com/csun2011/)
	
Exclusive jQuery Binding Prefixes :

- **selector**
    - selector:id returns #clientId allows finding typos at java runtime instead of at javascript runtime


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
- **ProgressiveDisplay**

## Maven dependency

To use this plugin, add the following dependency in your `pom.xml`.

	<dependencies>
		...
		<dependency>
			<groupId>org.got5</groupId>
			<artifactId>tapestry5-jquery</artifactId>
			<version>3.3.7</version>
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

* The [wiki](https://github.com/got5/tapestry5-jquery/wiki)
* Twitter: [http://twitter.com/GOTapestry5](http://twitter.com/GOTapestry5)

## How to use it

Just add tapestry5-jquery to your classpath (see Maven dependency snippet below)!

Then use components like you would normally do. For Autocomplete and Palette use "jquery" namespace:
 
	<t:form>
        <t:jquery.autocomplete />
		<t:submit />
    </t:form>

Or add jquery to the tapestry-library namespace:

	<html xmlns:t="http://tapestry.apache.org/schema/tapestry_5_3.xsd"
      xmlns:p="tapestry:parameter"
      xmlns:j="tapestry-library:jquery">

    <t:form>
        <j:palette />
        <t:textfield t:mixins="jquery/autocomplete" ... />
        <t:submit />
    </t:form>

	</html>

## You still need to have PrototypeJS and the components originally included in tapestry ?

Tapestry5-jquery project allows you to choose whether to include or not Prototype (and original tapestry components).
jQuery will be added to the javascript stack in every case.
In your AppModule, contributeApplicationDefaults method, you can add `configuration.add(JQuerySymbolConstants.SUPPRESS_PROTOTYPE, "false");`

## Note about jQuery

In traditional jQuery development, we are used to manipulate the `$` alias to select the elements we want to play with.
jQuery allows us to change this default alias (for compatibility with other js frameworks also using the `$` alias : like PrototypeJS, included in Tapestry, for example).
The tapestry5-jquery project has an option permitting you to customize this alias : in your AppModule, contributeApplicationDefaults method, you can add `configuration.add(JQuerySymbolConstants.JQUERY_ALIAS, "yourOwnAlias");`.
The default jquery alias is `$`.

By the way, if you've set the `JQuerySymbolConstants.SUPPRESS_PROTOTYPE` option to false, you may not use `$` to refer to jQuery, because `$` actually refers to Prototype.
Thus, you may want to change jQuery's alias in that particular case.
However, if you didn't change it, jQuery's alias will automatically be set to `$j`. 


## Important notice

All kind of feedback is very welcome. Please use [Github issues system](http://github.com/got5/tapestry5-jquery/issues) for that.

## License

This project is distributed under Apache 2 License. See LICENSE.txt for more information. 
