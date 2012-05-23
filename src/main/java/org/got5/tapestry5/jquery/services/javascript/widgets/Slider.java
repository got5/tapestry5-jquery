//
// Copyright 2010 GOT5 (GO Tapestry 5)
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
package org.got5.tapestry5.jquery.services.javascript.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.StylesheetLink;

public class Slider implements JavaScriptStack
{
	public static final String STACK_ID = "slider";
	
    private final AssetSource assetSource;

    public Slider(final AssetSource assetSource)
    {
        this.assetSource = assetSource;
    }

    public String getInitialization()
    {
        return null;
    }

    public List<Asset> getJavaScriptLibraries()
    {
    	final List<Asset> javaScriptStack = new ArrayList<Asset>();

        javaScriptStack.add(assetSource.getExpandedAsset("${assets.path}/components/slider/slider.js"));
        javaScriptStack.add(assetSource.getExpandedAsset("${jquery.ui.path}/jquery.ui.mouse.js"));
        javaScriptStack.add(assetSource.getExpandedAsset("${jquery.ui.path}/jquery.ui.slider.js"));
        
        return javaScriptStack;
    }


    public List<StylesheetLink> getStylesheets()
    {
        return Collections.emptyList();
    }

    public List<String> getStacks()
    {
        return Collections.emptyList();
    }

}
