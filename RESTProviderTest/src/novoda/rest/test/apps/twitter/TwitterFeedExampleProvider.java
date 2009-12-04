package novoda.rest.test.apps.twitter;

import novoda.rest.RESTProvider;
import novoda.rest.cursors.JsonCursor;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import android.content.ContentValues;
import android.database.AbstractCursor;
import android.net.Uri;

public class TwitterFeedExampleProvider extends RESTProvider {

	private static final String TAG = TwitterFeedExampleProvider.class
			.getSimpleName();

	@Override
	public HttpUriRequest deleteRequest(Uri uri, String selection,
			String[] selectionArgs) {
		throw new UnsupportedOperationException("not in use");
	}

	@Override
	public HttpUriRequest insertRequest(Uri uri, ContentValues values) {
		throw new UnsupportedOperationException("not in use");
	}

	@Override
	public HttpUriRequest queryRequest(Uri uri, String[] projection,
			String selection, String[] selectionArgs, String sortOrder) {
		HttpGet get = new HttpGet(
				"http://search.twitter.com/search.json?q=droidcon");
		return get;
	}

	@Override
	public HttpUriRequest updateRequest(Uri uri, ContentValues values,
			String selection, String[] selectionArgs) {
		throw new UnsupportedOperationException("not in use");
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public ResponseHandler<? extends Integer> getDeleteHandler(Uri uri) {
		throw new UnsupportedOperationException("not in use");
	}

	@Override
	public ResponseHandler<? extends Uri> getInsertHandler(Uri uri) {
		throw new UnsupportedOperationException("not in use");
	}

	@Override
	public ResponseHandler<? extends AbstractCursor> getQueryHandler(Uri uri) {
		return new JsonCursor("results", true);
	}

	@Override
	public ResponseHandler<? extends Integer> getUpdateHandler(Uri uri) {
		throw new UnsupportedOperationException("not in use");
	}

}
