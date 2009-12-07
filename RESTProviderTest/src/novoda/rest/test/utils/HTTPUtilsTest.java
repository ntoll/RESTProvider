package novoda.rest.test.utils;

import java.util.HashMap;
import java.util.Map;

import novoda.rest.utils.HTTPUtils;

import junit.framework.TestCase;

public class HTTPUtilsTest extends TestCase {

	private String[] sArgs;
	private Map<String, String> expected;

	@Override
	protected void setUp() throws Exception {
		sArgs = new String[] { "carl", "2" };
		expected = new HashMap<String, String>();
		expected.put("name", "carl");
		expected.put("value", "2");
		super.setUp();
	}

	public void testQueryWithANDbetweenValues() throws Exception {
		String selection = "name=? AND value=?";
		assertEquals(expected, HTTPUtils.convertToParams(selection, sArgs));
	}

	public void testQueryWithAmpSignBetweenValues() throws Exception {
		String selection = "name=?&value=?";
		assertEquals(expected, HTTPUtils.convertToParams(selection, sArgs));
	}

	public void testQueryWithNullValues() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		assertEquals(map, HTTPUtils.convertToParams(null, null));
	}
}
