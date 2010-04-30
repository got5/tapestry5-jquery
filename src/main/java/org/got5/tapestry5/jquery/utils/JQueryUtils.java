package org.got5.tapestry5.jquery.utils;

import org.apache.tapestry5.json.JSONObject;

public class JQueryUtils
{
    /**
     * Merge obj1 with obj2. Obj2 has predominance.
     * @param obj1
     * @param obj2
     * @return null if obj1 is null. Else return obj1 merged with obj2
     */
    public final static JSONObject merge(JSONObject obj1, JSONObject obj2) {
        if (obj1 == null)
            return null;
        
        if (obj2 == null)
            return obj1;
        
        for (String key : obj2.keys())
        {
            obj1.put(key, obj2.get(key));
        }
        
        return null;
    }
    
}
