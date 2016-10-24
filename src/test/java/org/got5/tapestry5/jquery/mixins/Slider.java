package org.got5.tapestry5.jquery.mixins;

import org.got5.tapestry5.jquery.ImportJQueryUI;
import org.got5.tapestry5.jquery.mixins.ui.Widget;

@ImportJQueryUI(value = {"ie",
						 "version",
						 "keycode",
						 "widget", 
						 "widgets/mouse", 
						 "widgets/slider"})
public class Slider extends Widget {

}
