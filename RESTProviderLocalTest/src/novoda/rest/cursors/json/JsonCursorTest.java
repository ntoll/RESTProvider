package novoda.rest.cursors.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import android.database.AbstractCursor;

public class JsonCursorTest {

	private static JsonNode json;
	private static JsonCursor jsoncursor;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		json = mock(JsonNode.class);
		jsoncursor = mock(JsonCursor.class);
		
		// does not work
		when(jsoncursor.moveToFirst()).thenReturn(true);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetCount() throws JsonProcessingException, IOException {
		when(json.toString()).thenReturn("{}");
		assertTrue(jsoncursor.getCount() == 0);
		
		when(json.toString()).thenReturn("{\"test\": \"1\"}");
		jsoncursor.handleResponse("{\"test\": \"1\"}");
		assertEquals(1, jsoncursor.getCount());
	}

	@Test
	public void testGetShort() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFloat() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDouble() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsNull() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetColumnNames() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStringInt() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testComplexJsonWithForeignKeys2() throws Exception {
		String json = "{\"sets\":[{\"set_date\":\"2008-10-16T00:00:00Z\",\"show_artist\":\"Armin Van Buuren\",\"title\":\"374\",\"track_plays\":463,\"show_title\":\"A State of Trance\",\"show_alias\":\"ASOT\",\"id\":1,\"small_image\":\"http://s3.amazonaws.com/mugasha/images/1/small.jpg?1229726763\",\"tracks\":[{\"artist\":\"A State of Trance\",\"number\":1,\"end_time\":44,\"title\":\"Intro\",\"start_time\":0},{\"artist\":\"Simon Patterson\",\"number\":2,\"end_time\":266,\"title\":\"Different Feeling\",\"start_time\":44},{\"artist\":\"Luke Chable\",\"number\":3,\"end_time\":325,\"title\":\"Melbourne (Maor Levi's Big Room Mix)\",\"start_time\":310},{\"artist\":\"EDX\",\"number\":4,\"end_time\":325,\"title\":\"Casa Grande\",\"start_time\":635},{\"artist\":\"Marcus Sch\",\"number\":5,\"end_time\":299,\"title\":\"The Last Pluck (Fluffy Mix)\",\"start_time\":960},{\"artist\":\"Arnej feat. Josie\",\"number\":6,\"end_time\":354,\"title\":\"Strangers We've Become (Vocal Mix)\",\"start_time\":1259},{\"artist\":\"Blake Jarrell\",\"number\":7,\"end_time\":234,\"title\":\"Punta Del Este (Arnej's Minimal Drum Dub)\",\"start_time\":1613},{\"artist\":\"Mat Zo\",\"number\":8,\"end_time\":279,\"title\":\"Rush\",\"start_time\":1847},{\"artist\":\"Danjo & Styles\",\"number\":9,\"end_time\":332,\"title\":\"Duende (La Vuelta) (Danjo's Kanova Remix)\",\"start_time\":2126},{\"artist\":\"Hodel & Hornblad\",\"number\":10,\"end_time\":427,\"title\":\"Hydrogen (Sundriver Remix) [Tune Of The Week]\",\"start_time\":2458},{\"artist\":\"Hydroid\",\"number\":11,\"end_time\":398,\"title\":\"The Eternal (Re:Locate Remix)\",\"start_time\":2885},{\"artist\":\"Vengeance\",\"number\":12,\"end_time\":441,\"title\":\"Explain (Reminder Remix)\",\"start_time\":3283},{\"artist\":\"Sied van Riel\",\"number\":13,\"end_time\":260,\"title\":\"Riel People Know (W&W Remix)\",\"start_time\":3724},{\"artist\":\"Heatbeat\",\"number\":14,\"end_time\":352,\"title\":\"Paradise Garage (DJ Eco Remix)\",\"start_time\":3984},{\"artist\":\"FKN feat. Jahala\",\"number\":15,\"end_time\":424,\"title\":\"Still Time? (Aly & Fila Remix)\",\"start_time\":4336},{\"artist\":\"Andy Prinz\",\"number\":16,\"end_time\":331,\"title\":\"Provision (Philippe El Sisi Remix) [Future Favorite]\",\"start_time\":4760},{\"artist\":\"Kandi & Neumann\",\"number\":17,\"end_time\":348,\"title\":\"Lovin' Feeling\",\"start_time\":5091},{\"artist\":\"Ferry Corsten vs. Prodigy\",\"number\":18,\"end_time\":292,\"title\":\"Radio Crash vs. Smack My Bitch Up (Armin van Buuren Mashup)\",\"start_time\":5439},{\"artist\":\"Michael Tsukerman\",\"number\":19,\"end_time\":313,\"title\":\"My Name Is Sawtooth (Sebastian Brandt Remix)\",\"start_time\":5731},{\"artist\":\"Duderstadt vs. Store N Forward\",\"number\":20,\"end_time\":362,\"title\":\"Broken (Nitrous Oxide Remix)\",\"start_time\":6044},{\"artist\":\"8 Wonders\",\"number\":21,\"end_time\":389,\"title\":\"Eventuality\",\"start_time\":6406},{\"artist\":\"Luc Poublon\",\"number\":22,\"end_time\":396,\"title\":\"Quantum Leap [ASOT Radio Classic]\",\"start_time\":6795}],\"file\":\"ASOT-374.mp3\",\"medium_image\":\"http://s3.amazonaws.com/mugasha/images/1/medium.jpg?1229726763\",\"track_likes\":11,\"description\":\"\",\"show_id\":1,\"likes\":2}]}";
		MockHttpResponse response = new MockHttpResponse(json);
		jsoncursor.handleResponse(response);
		assertTrue(jsoncursor.moveToFirst());
		assertEquals("Armin Van Buuren", jsoncursor.getString(jsoncursor
				.getColumnIndexOrThrow("show_artist")));
		JsonCursor tree = jsoncursor.getForeignCursor(0, "tracks");
		//assertNotNull(tree);
		assertTrue(tree.moveToFirst());
		assertEquals("A State of Trance", tree.getString(tree
				.getColumnIndexOrThrow("artist")));
		assertTrue(tree.moveToNext());
		assertEquals("Simon Patterson", tree.getString(tree
				.getColumnIndexOrThrow("artist")));
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
