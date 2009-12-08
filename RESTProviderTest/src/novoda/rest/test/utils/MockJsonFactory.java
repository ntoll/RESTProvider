package novoda.rest.test.utils;

import java.io.IOException;

import novoda.rest.cursors.JsonCursor;

import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

public class MockJsonFactory {

	public static JsonCursor createFromString(String json, String root,
			boolean useId, String rootId, String... fk)
			throws ClientProtocolException, IOException {
		MockHttpResponse response = new MockHttpResponse(json);
		return new JsonCursor(root, useId, rootId).withForeignKey(fk)
				.handleResponse(response);
	}

	private static class MockHttpResponse extends BasicHttpResponse {
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
