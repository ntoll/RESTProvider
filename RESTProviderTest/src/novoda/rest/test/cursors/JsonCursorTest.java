package novoda.rest.test.cursors;

import junit.framework.TestCase;
import novoda.rest.cursors.JsonCursor;

import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

// Tried using mock but failed miserably
public class JsonCursorTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testEmptyJsonShouldReturnCursorWithCountZero() throws Exception {
		String json = "{}";
		MockHttpResponse response = new MockHttpResponse(json);
		JsonCursor cursor = new JsonCursor((String)"root").handleResponse(response);
		assertEquals(0, cursor.getCount());
	}

	public void testOneDepthJSON() throws Exception {
		String json = "{\"test\":\"value\"}";
		MockHttpResponse response = new MockHttpResponse(json);
		JsonCursor cursor = new JsonCursor().handleResponse(response);
		assertEquals(1, cursor.getCount());
		assertTrue(cursor.moveToFirst());
		assertEquals("value", cursor.getString(0));
	}

	public void testsimpleJSONwithRootNode() throws Exception {
		String json = "{\"root\": [{\"test\": \"value\"},{\"test\": \"value2\"}]}";
		MockHttpResponse response = new MockHttpResponse(json);
		JsonCursor cursor = new JsonCursor("root").handleResponse(response);
		assertEquals(2, cursor.getCount());
		assertTrue(cursor.moveToFirst());
		assertEquals("value", cursor.getString(0));
		assertTrue(cursor.moveToNext());
		assertEquals("value2", cursor.getString(0));
	}

	public void testsimpleJSONArray() throws Exception {
		String json = "[{\"test\": \"value\"},{\"test\": \"value2\"}]";
		MockHttpResponse response = new MockHttpResponse(json);
		JsonCursor cursor = new JsonCursor().handleResponse(response);
		assertEquals(2, cursor.getCount());
		assertTrue(cursor.moveToFirst());
		assertEquals("value", cursor.getString(0));
		assertTrue(cursor.moveToNext());
		assertEquals("value2", cursor.getString(0));
	}

	public void testsimpleJSONArrayWithNullRoot() throws Exception {
		String json = "[{\"test\": \"value\"},{\"test\": \"value2\"}]";
		MockHttpResponse response = new MockHttpResponse(json);
		JsonCursor cursor = new JsonCursor(null).handleResponse(response);
		assertEquals(2, cursor.getCount());
		assertTrue(cursor.moveToFirst());
		assertEquals("value", cursor.getString(0));
		assertTrue(cursor.moveToNext());
		assertEquals("value2", cursor.getString(0));
	}

	public void testsimpleJSONwithRootNamedNode() throws Exception {
		String json = "{\"root\": [{\"test\": \"value\"},{\"test\": \"value2\"}]}";
		MockHttpResponse response = new MockHttpResponse(json);
		JsonCursor cursor = new JsonCursor("root", true, "test")
				.handleResponse(response);
		assertEquals(2, cursor.getCount());
		assertTrue(cursor.moveToFirst());
		assertEquals("value", cursor.getString(cursor
				.getColumnIndexOrThrow("_id")));
		assertTrue(cursor.moveToNext());
		assertEquals("value2", cursor.getString(cursor
				.getColumnIndexOrThrow("_id")));
	}

	public void testsimpleJSONwithRootNamedNodeAsInt() throws Exception {
		String json = "{\"root\": [{\"test\": 1},{\"test\": 2}]}";
		MockHttpResponse response = new MockHttpResponse(json);
		JsonCursor cursor = new JsonCursor("root", true, "test")
				.handleResponse(response);
		assertEquals(2, cursor.getCount());
		assertTrue(cursor.moveToFirst());
		assertEquals(1, cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
		assertTrue(cursor.moveToNext());
		assertEquals(2, cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
	}

	public void testsimpleJSONwithRootNamedNodeAsLong() throws Exception {
		String json = "{\"root\": [{\"test\": 1},{\"test\": 2}]}";
		MockHttpResponse response = new MockHttpResponse(json);
		JsonCursor cursor = new JsonCursor("root", true, "test")
				.handleResponse(response);
		assertEquals(2, cursor.getCount());
		assertTrue(cursor.moveToFirst());
		assertEquals(1, cursor.getLong(cursor.getColumnIndexOrThrow("_id")));
		assertTrue(cursor.moveToNext());
		assertEquals(2, cursor.getLong(cursor.getColumnIndexOrThrow("_id")));
	}

	public void testJSONWithForeignKeys() throws Exception {
		String json = "{\"root\": [{\"id\": 1, \"test\": [{ \"f1\":1}]} ]}";
		MockHttpResponse response = new MockHttpResponse(json);
		JsonCursor cursor = new JsonCursor("root").withForeignKey("test")
				.handleResponse(response);
		JsonCursor f = cursor.getForeignCursor("test");
		assertTrue(f.moveToFirst());
		assertEquals(1 ,f.getInt(0));
	}

	private class MockHttpResponse extends BasicHttpResponse {
		private byte[] response;

		public MockHttpResponse(StatusLine statusline, String response) {
			super(statusline);
			this.response = response.getBytes();
		}

		public MockHttpResponse(String json) {
			this(new BasicStatusLine(new ProtocolVersion("http", 4, 1), 200,
					"All Good"), json);
		}

		@Override
		public HttpEntity getEntity() {
			return new ByteArrayEntity(response);
		}
	}
}
