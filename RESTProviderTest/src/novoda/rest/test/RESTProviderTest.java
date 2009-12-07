package novoda.rest.test;

import junit.framework.TestCase;
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
		super.setUp();
	}

	public void testForeignKey() throws Exception {
		provider.query(Uri.parse("content://test"), null, null, null, null);
		Cursor cur = provider.query(Uri.parse("content://test/1/f1"), null,
				null, null, null);
		assertNotNull(cur.moveToFirst());
		assertEquals(cur.getString(0), "1");
	}

	private class MyRestProvider extends MockRESTProvider {
		@Override
		public ResponseHandler<? extends AbstractCursor> getQueryHandler(Uri uri) {
			return new MyCursor();
		}

		@Override
		public HttpUriRequest queryRequest(Uri uri, String[] projection,
				String selection, String[] selectionArgs, String sortOrder) {
			return new HttpGet("http://google.com");
		}
	}

	private class MyCursor extends JsonCursor {

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
