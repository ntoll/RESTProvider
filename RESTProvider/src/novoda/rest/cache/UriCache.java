
package novoda.rest.cache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import android.database.Cursor;
import android.net.Uri;

import com.google.common.collect.MapMaker;

public class UriCache implements Map<Uri, Cursor> {

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

    public Cursor put(Uri parse, Cursor cursor) {
        return cache.put(parse, cursor);
    }

    public Cursor get(Uri parse) {
        return cache.get(parse);
    }

    public boolean canRespondTo(Uri parse) {
        return cache.containsKey(parse);
    }

    public void clear() {
        cache.clear();
    }

    public boolean containsKey(Object key) {
        return cache.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return cache.containsValue(value);
    }

    public Set<java.util.Map.Entry<Uri, Cursor>> entrySet() {
        return cache.entrySet();
    }

    public Cursor get(Object key) {
        return cache.get(key);
    }

    public boolean isEmpty() {
        return cache.isEmpty();
    }

    public Set<Uri> keySet() {
        return cache.keySet();
    }

    public void putAll(Map<? extends Uri, ? extends Cursor> map) {
        cache.putAll(map);
    }

    public Cursor remove(Object key) {
        return cache.remove(key);
    }

    public int size() {
        return cache.size();
    }

    public Collection<Cursor> values() {
        return cache.values();
    }
}
