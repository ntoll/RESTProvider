package novoda.rest.cache;

import junit.framework.TestCase;
import novoda.rest.cursors.JsonCursor;
import android.net.Uri;

public class UriCacheTest extends TestCase {

	public void testSavingAnewCursor() throws Exception {
		JsonCursor cur = new JsonCursor();
		UriCache.getInstance().put(Uri.parse("content://test"), cur);
		assertSame(cur, UriCache.getInstance().get(Uri.parse("content://test")));
	}
	
	public void testCacheHasURI() throws Exception {
		JsonCursor cur = new JsonCursor();
		UriCache.getInstance().put(Uri.parse("content://test"), cur);
		UriCache.getInstance().put(Uri.parse("content://test2"), cur);
		UriCache.getInstance().put(Uri.parse("content://test3"), cur);
		UriCache.getInstance().put(Uri.parse("content://test4"), cur);
		assertTrue(UriCache.getInstance().canRespondTo(Uri.parse("content://test3")));
	}
}
