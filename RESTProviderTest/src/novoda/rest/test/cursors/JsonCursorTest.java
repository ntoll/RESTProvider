package novoda.rest.test.cursors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;

import novoda.rest.cursors.JsonCursor;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class) 
public class JsonCursorTest {

	@Test
	public void testEmptyJsonShouldReturnCursorWithCountZero() {
		//HttpResponse response = mock(HttpResponse.class);
		HttpResponse response = PowerMock.createMock(HttpResponse.class);
		HttpEntity entity = PowerMock.createMock(HttpEntity.class);
//		PowerMock.
//		
//		String json = "{}";
//		when(entity.getContent()).thenReturn(
//				new ByteArrayInputStream(json.getBytes("UTF-8")));
//		when(response.getEntity()).thenReturn(entity);
//		JsonCursor cursor = new JsonCursor("root").handleResponse(response);
//		assertEquals(0, cursor.getCount());
	}
	
	@Test
	public void testHandleResponse() {
		fail("Not yet implemented");
	}

}
