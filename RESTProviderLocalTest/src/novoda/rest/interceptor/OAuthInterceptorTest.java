package novoda.rest.interceptor;


import novoda.rest.interceptors.OAuthInterceptor;

import org.apache.http.HttpRequest;
import org.apache.http.protocol.HttpContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OAuthInterceptorTest {

	private static OAuthInterceptor oauth;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		oauth = new OAuthInterceptor();
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
	public void testSimpleOAuth() {
		HttpRequest request;
		HttpContext context;
		//oauth.process(request, context);
	}
}
