
package novoda.rest.handlers;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

public class ErrorHandler implements ResponseHandler<Boolean> {

    public Boolean handleResponse(HttpResponse response) throws ClientProtocolException,
            IOException {
        if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return true;
        }
        return false;
    }

}
