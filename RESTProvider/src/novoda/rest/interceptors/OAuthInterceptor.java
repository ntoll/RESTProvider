
package novoda.rest.interceptors;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;

public class OAuthInterceptor implements HttpRequestInterceptor {

    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
    }

}
