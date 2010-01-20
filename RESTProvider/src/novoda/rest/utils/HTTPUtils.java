
package novoda.rest.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.content.ContentValues;

public class HTTPUtils {

    /**
     * Given a selection string and selectionArgs, returns the key/value pair to
     * be used in a HTTP request. Usually the selection and selectionArgs are
     * given during a query/delete/update/insert call against the
     * RESTProvider/ContentProvider.
     * 
     * @param selection string used in conjunction with selectionArgs
     * @param selectionArgs each string will be replaced with the orderly '?'
     *            character in the selection string
     * @return key/value pairs mapped between the selection string and the
     *         selectionArgs replacing the '?'
     */
    public static Map<String, String> convertToParams(String selection, String[] selectionArgs) {
        if (selection == null)
            return new HashMap<String, String>();

        String t = selection.replaceAll("\\sAND\\s|\\sand\\s", "&");
        if (selectionArgs != null) {
            for (int i = 0; i < selectionArgs.length; i++) {
                t = t.replaceFirst("\\?", selectionArgs[i]);
            }
        }
        return parseQuerystring(t);
    }

    /**
     * Parse a query string into a map of key/value pairs
     * 
     * @param queryString the string to parse (without the '?')
     * @return key/value pairs mapping
     */
    public static Map<String, String> parseQuerystring(String queryString) {
        Map<String, String> map = new HashMap<String, String>();
        if ((queryString == null) || (queryString.equals(""))) {
            return map;
        }
        String[] params = queryString.split("&");
        for (String param : params) {
            try {
                String[] keyValuePair = param.split("=", 2);
                String name = URLDecoder.decode(keyValuePair[0], "UTF-8");
                if (name == "") {
                    continue;
                }
                String value = keyValuePair.length > 1 ? URLDecoder
                        .decode(keyValuePair[1], "UTF-8") : "";
                map.put(name, value);
            } catch (UnsupportedEncodingException e) {
            }
        }
        return map;
    }

    public static Map<String, String> convertToParams(ContentValues values) {
        Map<String, String> ret = new HashMap<String, String>();
        for (Entry<String, Object> entry : values.valueSet()) {
            ret.put(entry.getKey(), entry.getValue().toString());
        }
        return ret;
    }
    
    public static String convertToQueryString(Map<String, String> param){
        if (param == null)
            return null;
        
        StringBuffer buf = new StringBuffer("?");
        for (Map.Entry<String, String> entry : param.entrySet()) {
            buf.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        buf.deleteCharAt(buf.length()-1);
        return buf.toString();
    }
}
