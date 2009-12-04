package novoda.rest.handlers;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

public class UpdateHandler implements ResponseHandler<Integer> {

    public Integer handleResponse(HttpResponse response) throws ClientProtocolException,
            IOException {
        return null;
    }

}
