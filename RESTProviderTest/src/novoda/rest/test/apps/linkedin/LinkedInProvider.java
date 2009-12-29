
package novoda.rest.test.apps.linkedin;

import java.util.Map;

import novoda.rest.DefaultRESTProvider;
import novoda.rest.RESTProvider;
import novoda.rest.cursors.xml.XMLCursor;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.signature.SignatureMethod;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.AbstractCursor;
import android.net.Uri;
import android.util.Log;

import com.google.gdata.client.authn.oauth.OAuthParameters;

public class LinkedInProvider extends DefaultRESTProvider {

    private static final String TAG = LinkedInProvider.class.getSimpleName();

    private OAuthConsumer consumer;

    @Override
    public boolean onCreate() {
        SharedPreferences pref = getContext().getSharedPreferences(Constants.SHARED_PREF_NAME,
                Context.MODE_PRIVATE);

        consumer = new CommonsHttpOAuthConsumer(Constants.CONSUMER_KEY, Constants.CONSUMER_KEY_SECRET,
                SignatureMethod.HMAC_SHA1);

        consumer.setTokenWithSecret(pref.getString(OAuthParameters.OAUTH_TOKEN_KEY, ""), pref
                .getString(OAuthParameters.OAUTH_TOKEN_SECRET_KEY, ""));

        if (consumer.getConsumerKey().equals("") || consumer.getConsumerKey() == null)
            return false;

        return super.onCreate();
    }

    @Override
    public HttpUriRequest getRequest(Uri uri, int type, Map<String, String> params) {

        if (type == RESTProvider.QUERY) {
            HttpGet get = new HttpGet("http://api.linkedin.com/v1/people/~/network");
            try {
                consumer.sign(get);
            } catch (OAuthMessageSignerException e) {
                Log.e(TAG, "an error occured in getRequest", e);
            } catch (OAuthExpectationFailedException e) {
                Log.e(TAG, "an error occured in getRequest", e);
            }
            return get;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseHandler<? extends Integer> getDeleteHandler(Uri uri) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseHandler<? extends Uri> getInsertHandler(Uri uri) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseHandler<? extends AbstractCursor> getQueryHandler(Uri uri) {
        return new XMLCursor();
    }

    @Override
    public ResponseHandler<? extends Integer> getUpdateHandler(Uri uri) {
        throw new UnsupportedOperationException();

    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

}
