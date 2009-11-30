
package novoda.rest.cursors;

import java.io.IOException;

import novoda.rest.handlers.CursorHandler;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;


// not used for now
public class CursorFactory {

    private static final String APPLICATION_JSON = "application/json";

    private static final String CONTENT_TYPE = "Content-Type";

    public static CursorHandler<JsonCursor> create(String root) throws ClientProtocolException,
            IOException {
        return new JsonCursor(root);
    }

    public static boolean isJson(HttpResponse response) {
        if (response.containsHeader(CONTENT_TYPE)
                && response.getFirstHeader(CONTENT_TYPE).equals(APPLICATION_JSON)) {
            return true;
        }
        return false;
    }

    public static boolean isOK(HttpResponse response) {
        if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return true;
        }
        return false;
    }
}
