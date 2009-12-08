package novoda.rest.test;

import junit.framework.TestCase;
import novoda.rest.cache.UriCache;
import novoda.rest.cursors.JsonCursor;
import novoda.rest.test.mock.MockRESTProvider;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import android.database.AbstractCursor;
import android.database.Cursor;
import android.net.Uri;

public class RESTProviderTest extends TestCase {

	private MyRestProvider provider;
	

	@Override
	protected void setUp() throws Exception {
		provider = new MyRestProvider();
		UriCache.getInstance().put(Uri.parse("content://test/1/f"), new MyCursor("content://test/1/f"));
		UriCache.getInstance().put(Uri.parse("content://test/2/f"), new MyCursor("content://test/2/f"));
		UriCache.getInstance().put(Uri.parse("content://test/3/f"), new MyCursor("content://test/3/f"));
		UriCache.getInstance().put(Uri.parse("content://test/4/f"), new MyCursor("content://test/4/f"));
		super.setUp();
	}

	public void testQueryCacheShouldReturnCache() throws Exception {
		Cursor cur = provider.query(Uri.parse("content://test/3/f"), null, null, null, null);
		assertEquals(cur.getString(0), "content://test/3/f");
	}

	private class MyRestProvider extends MockRESTProvider {
		@Override
		public ResponseHandler<? extends AbstractCursor> getQueryHandler(Uri uri) {
			return new MyCursor("");
		}

		@Override
		public HttpUriRequest queryRequest(Uri uri, String[] projection,
				String selection, String[] selectionArgs, String sortOrder) {
			return new HttpGet("http://google.com");
		}
	}

	private class MyCursor extends JsonCursor {
		
		private String value;

		public MyCursor(String value) {
			this.value = value;
		}
		
		@Override
		public String getString(int column) {
			return value;
		}
		
		@Override
		public String[] getForeignFields() {
			return new String[] { "f1" };
		}

		@Override
		public JsonCursor getForeignCursor(String string) {
			return super.getForeignCursor(string);
		}
	}

}
