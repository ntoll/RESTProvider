
package novoda.rest.cache;

import java.util.Map;

import novoda.rest.cursors.JsonCursor;

import android.database.Cursor;
import android.net.Uri;

import com.google.common.collect.MapMaker;

public class UriCache {

    private Map<Uri, Cursor> cache;

    private volatile static UriCache uInstance;

    private UriCache() {
        this.cache = new MapMaker().weakValues().makeMap();
    }

    public static UriCache getInstance() {
        if (uInstance == null) {
            synchronized (UriCache.class) {
                if (uInstance == null)
                    uInstance = new UriCache();
            }
        }
        return uInstance;
    }

    public void put(Uri parse, Cursor cursor) {
        cache.put(parse, cursor);
    }

    public Cursor get(Uri parse) {
        return cache.get(parse);
    }

    public boolean canRespondTo(Uri parse) {
        return cache.containsKey(parse);
    }
}
