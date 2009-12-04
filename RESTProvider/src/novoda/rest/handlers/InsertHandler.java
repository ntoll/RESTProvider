package novoda.rest.handlers;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

import android.net.Uri;

public class InsertHandler implements ResponseHandler<Uri> {

    public Uri handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        return null;
    }

}
