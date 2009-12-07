package novoda.rest.interceptor;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import novoda.rest.RESTProvider;
import novoda.rest.cursors.JsonCursor;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import android.net.Uri;

// can t get this to work :(
public class RESTProviderLocalTest {

	private static RESTProvider provider;
	private static JsonCursor cursor;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		provider = mock(RESTProvider.class);
		cursor = mock(JsonCursor.class);
		
		when(cursor.getForeignFields()).thenReturn(new String[] { "test" });
		when(provider.getQueryHandler(Uri.parse(anyString()))).thenAnswer(
				new Answer<JsonCursor>() {
					public JsonCursor answer(InvocationOnMock invocation)
							throws Throwable {
						return cursor;
					}
				});
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
	public void testQueryUriStringArrayStringStringArrayString() {
		fail("Not yet implemented");
	}

}
