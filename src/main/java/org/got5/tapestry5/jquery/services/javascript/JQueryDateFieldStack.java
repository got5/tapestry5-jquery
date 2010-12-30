package org.got5.tapestry5.jquery.services.javascript;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.ThreadLocale;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.StylesheetLink;


public class JQueryDateFieldStack implements JavaScriptStack
{
    private final ThreadLocale threadLocale;

    private final boolean compactJSON;

    private final List<Asset> javaScriptStack;

    public JQueryDateFieldStack(ThreadLocale threadLocale, @Symbol(SymbolConstants.COMPACT_JSON)
    boolean compactJSON, final AssetSource assetSource)
    {
        this.threadLocale = threadLocale;
        this.compactJSON = compactJSON;

        final Mapper<String, Asset> pathToAsset = new Mapper<String, Asset>()
        {
            @Override
            public Asset map(String path)
            {
                return assetSource.getExpandedAsset(path);
            }
        };

        javaScriptStack = F
                .flow("${tapestry.jquery.path}/ui_1_8/minified/jquery.ui.datepicker.min.js", 
                      "${tapestry.jquery.path}/components/datefield.js")
                .map(pathToAsset).toList();
    }

    public String getInitialization()
    {
        Locale locale = threadLocale.getLocale();

        JSONObject spec = new JSONObject();

        DateFormatSymbols symbols = new DateFormatSymbols(locale);

        spec.put("months", new JSONArray(symbols.getMonths()));

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

        // DatePicker expects 0 to be monday. Calendar defines SUNDAY as 1, MONDAY as 2, etc.

        spec.put("firstDay", firstDay == Calendar.SUNDAY ? 6 : firstDay - 2);

        // TODO: Skip localization if locale is English?

        return String.format("Tapestry.init({\"dateField\": %s });", spec.toString(compactJSON));
    }

    public List<Asset> getJavaScriptLibraries()
    {
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
