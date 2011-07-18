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
package org.got5.tapestry5.jquery.services.javascript;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.internal.services.javascript.DateFieldStack;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.ThreadLocale;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.StylesheetLink;

/**
 * Replacement for the core stack {@link DateFieldStack}.
 *
 * @author criedel, GOT5
 */
public class JQueryDateFieldStack implements JavaScriptStack
{
    private final ThreadLocale threadLocale;
    
    private final AssetSource assetSource;

    private final boolean compactJSON;

    private final List<Asset> javaScriptStack;

    public JQueryDateFieldStack(final ThreadLocale threadLocale,

                                @Symbol(SymbolConstants.COMPACT_JSON)
                                final boolean compactJSON,

                                @Symbol(SymbolConstants.PRODUCTION_MODE)
                                final boolean productionMode,
                            	
                                final AssetSource assetSource)
    {
        this.threadLocale = threadLocale;
        this.assetSource = assetSource;
        this.compactJSON = compactJSON;

        final Mapper<String, Asset> pathToAsset = new Mapper<String, Asset>()
        {
            @Override
            public Asset map(String path)
            {
                return assetSource.getExpandedAsset(path);
            }
        };
        
        
        if (productionMode) {

            javaScriptStack = F
                    .flow("${jquery.ui.path}/minified/jquery.ui.datepicker.min.js",
                          "${assets.path}/components/datefield/datefield.js")
                    .map(pathToAsset).toList();

        } else {

            javaScriptStack = F
                    .flow("${jquery.ui.path}/jquery.ui.datepicker.js",
                          "${assets.path}/components/datefield/datefield.js")
                    .map(pathToAsset).toList();
        }

    }

    public String getInitialization()
    {
        Locale locale = threadLocale.getLocale();

        JSONObject spec = new JSONObject();

        DateFormatSymbols symbols = new DateFormatSymbols(locale);

        spec.put("months", new JSONArray((Object[]) symbols.getMonths()));

        StringBuilder days = new StringBuilder();

        String[] weekdays = symbols.getWeekdays();

        Calendar c = Calendar.getInstance(locale);

        int firstDay = c.getFirstDayOfWeek();

        // DatePicker needs them in order from monday to sunday.

        for (int i = Calendar.MONDAY; i <= Calendar.SATURDAY; i++)
        {
            days.append(weekdays[i].substring(0, 1));
        }

        days.append(weekdays[Calendar.SUNDAY].substring(0, 1));

        spec.put("days", days.toString().toLowerCase(locale));

        // jQuery DatePicker widget expects 0 to be sunday. Calendar defines SUNDAY as 1, MONDAY as 2, etc.        
        spec.put("firstDay", firstDay-1);

        // set language
        spec.put("language", locale.getLanguage());
        
        // TODO: Skip localization if locale is English?

        return String.format("Tapestry.DateField.initLocalization(%s);", spec.toString(compactJSON));
    }

    public List<Asset> getJavaScriptLibraries()
    {
    	Locale locale = threadLocale.getLocale();
        String pathToDatepickerI18nRess = "${jquery.ui.path}/i18n/jquery.ui.datepicker-"+locale.getLanguage()+".js";
        Asset datePickerI18nAsset = this.assetSource.getExpandedAsset(pathToDatepickerI18nRess);
     	if (datePickerI18nAsset.getResource().exists())
     	{
     		List<Asset> ret = new ArrayList<Asset>();
     		ret.addAll(javaScriptStack);
     		ret.add(datePickerI18nAsset);
     		return ret;
     	}
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
