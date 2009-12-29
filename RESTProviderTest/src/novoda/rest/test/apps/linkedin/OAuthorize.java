package novoda.rest.test.apps.linkedin;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.google.gdata.client.authn.oauth.OAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthUtil;

public class OAuthorize {
	private static final String TAG = OAuthorize.class.getSimpleName();

	private static DefaultHttpClient client;
	private OAuthHmacSha1Signer signer;
	private OAuthParameters oauthParameters;
	private String accessURL;
	private String requestURL;

	private String authorizeURL;

	public OAuthorize(String consumerKey, String consumerSecret,
			String requestURL, String authorizeURL, String accessURL) {
		oauthParameters = new OAuthParameters();
		signer = new OAuthHmacSha1Signer();
		client = new DefaultHttpClient();

		oauthParameters.setOAuthConsumerKey(consumerKey);
		oauthParameters.setOAuthConsumerSecret(consumerSecret);
		oauthParameters
				.setOAuthCallback("x-oauth-android-linkedin://callback.local");
		oauthParameters.setOAuthSignatureMethod("HMAC-SHA1");
		oauthParameters.setOAuthVersion("1.0");

		this.authorizeURL = authorizeURL;
		this.requestURL = requestURL;
		this.accessURL = accessURL;
	}

	public Map<String, String> getRequestToken()
			throws ClientProtocolException, IOException, OAuthException {

		oauthParameters.setOAuthTimestamp(OAuthUtil.getTimestamp());
		oauthParameters.setOAuthNonce(OAuthUtil.getNonce());
		HttpPost get = new HttpPost(requestURL);
		String signature = signer.getSignature(OAuthUtil
				.getSignatureBaseString(requestURL, "POST", oauthParameters
						.getBaseParameters()), oauthParameters);

		oauthParameters.setOAuthSignature(OAuthUtil.encode(signature));

		StringBuffer buf = new StringBuffer("OAuth ");
		for (Entry<String, String> entry : oauthParameters.getBaseParameters()
				.entrySet()) {
			buf.append(entry.getKey()).append("=\"").append(entry.getValue())
					.append("\"").append(", ");
		}

		for (Entry<String, String> entry : oauthParameters.getExtraParameters()
				.entrySet()) {
			if (entry.getKey().equals("oauth_consumer_secret"))
				continue;
			buf.append(entry.getKey()).append("=\"").append(entry.getValue())
					.append("\"").append(", ");
		}

		buf.deleteCharAt(buf.length() - 1);

		Log.i("TEST", buf.toString());
		get.addHeader("Authorization", buf.toString());
		Map<String, String> ret = client.execute(get,
				new OAuthFirstLegHandler());
		oauthParameters.setOAuthToken(ret.get(OAuthParameters.OAUTH_TOKEN_KEY));
		oauthParameters.setOAuthTokenSecret(ret
				.get(OAuthParameters.OAUTH_TOKEN_SECRET_KEY));
		return ret;
	}

	public void getRequestToken(final OAuthorizeCallback callback) {
		new Thread(new Runnable() {
			public void run() {
				try {
					Map<String, String> ret = getRequestToken();
					callback.onRequestTokenSuccess(ret
							.get(OAuthParameters.OAUTH_TOKEN_KEY), ret
							.get(OAuthParameters.OAUTH_TOKEN_SECRET_KEY));
				} catch (ClientProtocolException e) {
					callback.onError(0, e.getMessage());
				} catch (IOException e) {
					callback.onError(1, e.getMessage());
				} catch (OAuthException e) {
					callback.onError(2, e.getMessage());
				}
			}
		}).start();
	}
	
	public String getAuthURL() throws ClientProtocolException, IOException, OAuthException {
		return this.authorizeURL + "?oauth_token=" + getRequestToken().get(OAuthParameters.OAUTH_TOKEN_KEY);
	}

	public Map<String, String> getAccessToken(String token, String verifier)
			throws OAuthException, ClientProtocolException, IOException {

		oauthParameters.reset();
		oauthParameters
				.removeCustomBaseParameter(OAuthParameters.OAUTH_CALLBACK_KEY);

		oauthParameters.assertOAuthTokenExists();
		oauthParameters.assertOAuthTokenSecretExists();
		oauthParameters.assertOAuthConsumerKeyExists();
		oauthParameters.assertOAuthConsumerSecretExists();

		oauthParameters.setOAuthVerifier(verifier);
		oauthParameters.setOAuthToken(token);

		oauthParameters.setOAuthTimestamp(OAuthUtil.getTimestamp());
		oauthParameters.setOAuthNonce(OAuthUtil.getNonce());
		HttpPost get = new HttpPost(accessURL);

		String signature = signer.getSignature(OAuthUtil
				.getSignatureBaseString(accessURL, "POST", oauthParameters
						.getBaseParameters()), oauthParameters);

		oauthParameters.setOAuthSignature(OAuthUtil.encode(signature));

		StringBuffer buf = new StringBuffer("OAuth ");
		for (Entry<String, String> entry : oauthParameters.getBaseParameters()
				.entrySet()) {
			buf.append(entry.getKey()).append("=\"").append(entry.getValue())
					.append("\"").append(", ");
		}

		for (Entry<String, String> entry : oauthParameters.getExtraParameters()
				.entrySet()) {
			if (entry.getKey().equals("oauth_consumer_secret")
					|| entry.getKey().equals(
							OAuthParameters.OAUTH_TOKEN_SECRET_KEY))
				continue;
			Log.i(TAG, entry.getKey());
			buf.append(entry.getKey()).append("=\"").append(entry.getValue())
					.append("\"").append(", ");
		}

		buf.deleteCharAt(buf.length() - 1);
		Log.i("TEST", buf.toString());
		get.addHeader("Authorization", buf.toString());
		Map<String, String> ret = null;
		ret = client.execute(get, new OAuthFirstLegHandler());
		oauthParameters.setOAuthToken(ret.get(OAuthParameters.OAUTH_TOKEN_KEY));
		oauthParameters.setOAuthTokenSecret(ret
				.get(OAuthParameters.OAUTH_TOKEN_SECRET_KEY));
		return ret;

	}

	public void getAccessToken(String token, String verifier,
			OAuthorizeCallback callback) {
	}

	private class OAuthFirstLegHandler implements
			ResponseHandler<Map<String, String>> {
		public Map<String, String> handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			String res = EntityUtils.toString(response.getEntity());
			Log.i(TAG, res);
			if (response != null && response.getStatusLine() != null
					&& response.getStatusLine().getStatusCode() != 200)
				return null;
			return OAuthUtil.parseQuerystring(res);
		}
	}

}
