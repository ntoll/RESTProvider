package novoda.rest.test.apps.twitter;

import java.util.Map;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;

import android.database.AbstractCursor;
import android.net.Uri;
import novoda.rest.DefaultRESTProvider;

public class TwitterFeedExampleProvider2 extends DefaultRESTProvider {

	private static final String TAG = TwitterFeedExampleProvider.class
			.getSimpleName();

	@Override
	public HttpUriRequest getRequest(Uri uri, int type,
			Map<String, String> params) {
		return null;
	}

	@Override
	public ResponseHandler<? extends Integer> getDeleteHandler(Uri uri) {
		return null;
	}

	@Override
	public ResponseHandler<? extends Uri> getInsertHandler(Uri uri) {
		return null;
	}

	@Override
	public ResponseHandler<? extends AbstractCursor> getQueryHandler(Uri uri) {
		return null;
	}

	@Override
	public ResponseHandler<? extends Integer> getUpdateHandler(Uri uri) {
		return null;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

}
