
package novoda.rest.test.apps.linkedin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import novoda.rest.DefaultRESTProvider;
import novoda.rest.RESTProvider;
import novoda.rest.cursors.xml.XMLCursor;
import novoda.rest.handlers.UpdateHandler;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.signature.SignatureMethod;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.AbstractCursor;
import android.net.Uri;
import android.util.Log;

import com.google.gdata.client.authn.oauth.OAuthParameters;

public class LinkedInProvider extends DefaultRESTProvider {

    private static final String TAG = LinkedInProvider.class.getSimpleName();

    private OAuthConsumer consumer;

    private OnSharedPreferenceChangeListener listener = new OnSharedPreferenceChangeListener() {
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (sharedPreferences.contains(OAuthParameters.OAUTH_TOKEN_KEY)
                    && sharedPreferences.contains(OAuthParameters.OAUTH_TOKEN_SECRET_KEY)) {
                consumer.setTokenWithSecret(sharedPreferences.getString(OAuthParameters.OAUTH_TOKEN_KEY, ""),
                        sharedPreferences.getString(OAuthParameters.OAUTH_TOKEN_SECRET_KEY, ""));
            }
        }
    };

    @Override
    public boolean onCreate() {
        SharedPreferences pref = getContext().getSharedPreferences(Constants.SHARED_PREF_NAME,
                Context.MODE_PRIVATE);

        consumer = new CommonsHttpOAuthConsumer(Constants.CONSUMER_KEY,
                Constants.CONSUMER_KEY_SECRET, SignatureMethod.HMAC_SHA1);

        consumer.setTokenWithSecret(pref.getString(OAuthParameters.OAUTH_TOKEN_KEY, ""), pref
                .getString(OAuthParameters.OAUTH_TOKEN_SECRET_KEY, ""));

        if (consumer.getConsumerKey().equals("") || consumer.getConsumerKey() == null)
            pref.registerOnSharedPreferenceChangeListener(listener);

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
        } else if (type == RESTProvider.UPDATE) {
            HttpPut put = new HttpPut("http://api.linkedin.com/v1/people/~/current-status");
            try {
                put.setEntity(new StringEntity("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                        + "<current-status>" + params.get(Constants.CURRENT_STATUS)
                        + "</current-status>"));
                consumer.sign(put);
            } catch (OAuthMessageSignerException e) {
                Log.e(TAG, "an error occured in getRequest", e);
            } catch (OAuthExpectationFailedException e) {
                Log.e(TAG, "an error occured in getRequest", e);
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, "an error occured in getRequest", e);
            }
            return put;
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
        return new mXML();
    }

    @Override
    public ResponseHandler<? extends Integer> getUpdateHandler(Uri uri) {
        return new LinkedInUpdateHandler();
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    public class mXML extends XMLCursor {

        final Map<String, String> xpath = new HashMap<String, String>(2);

        public mXML() {
            // we need _id for the adapter
            xpath.put("_id", "/network/updates/update[#]/update-content/person/id");
            xpath.put("first-name", "/network/updates/update[#]/update-content/person/first-name");
            xpath.put("last-name", "/network/updates/update[#]/update-content/person/last-name");
            xpath.put("headline", "/network/updates/update[#]/update-content/person/headline");
            xpath.put("update-type", "/network/updates/update[#]/update-type");
        }

        @Override
        public String getXPath(int row, String column) {
            return xpath.get(column).replace("#", "" + (row + 1));
        }

        @Override
        public String[] getColumnNames() {
            return (String[])xpath.keySet().toArray(new String[0]);
        }

        @Override
        public String getCountXPath() {
            return "/network/updates/@count";
        }
    }
    
    
    public class LinkedInUpdateHandler extends UpdateHandler {
        public Integer handleResponse(HttpResponse response) throws ClientProtocolException,
                IOException {
            return response.getStatusLine().getStatusCode();
        }
    }

}
