
package novoda.rest.interceptors;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeRegistry;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

public class OAuthInterceptor implements HttpRequestInterceptor {

    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        AuthState authState = (AuthState)context.getAttribute(ClientContext.TARGET_AUTH_STATE);
        if (authState != null && authState.getAuthScheme() != null) {
            return;
        }
        HttpHost target = (HttpHost)context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
        CredentialsProvider creds = (CredentialsProvider)context
                .getAttribute(ClientContext.CREDS_PROVIDER);
        AuthSchemeRegistry schemes = (AuthSchemeRegistry)context
                .getAttribute(ClientContext.AUTHSCHEME_REGISTRY);
        
        for (Object schemeName : (Iterable)context.getAttribute(ClientContext.AUTH_SCHEME_PREF)) {
            AuthScheme scheme = schemes.getAuthScheme(schemeName.toString(), request.getParams());
            if (scheme != null) {
                AuthScope targetScope = new AuthScope(target.getHostName(), target.getPort(),
                        scheme.getRealm(), scheme.getSchemeName());
                Credentials cred = creds.getCredentials(targetScope);
                if (cred != null) {
                    authState.setAuthScheme(scheme);
                    authState.setCredentials(cred);
                    return;
                }
            }
        }
    }

}
