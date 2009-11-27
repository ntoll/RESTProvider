
package novoda.rest.cursors;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;

import android.database.Cursor;

public class CursorFactory {

    private static final String APPLICATION_JSON = "application/json";

    private static final String CONTENT_TYPE = "Content-Type";

    public static Cursor create(HttpResponse response, String root) throws ClientProtocolException,
            IOException {
        if (isOK(response) && isJson(response)) {
            return new JsonCursor(root).handleResponse(response);
        }
        return null;
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
