package novoda.rest.test.apps.twitter;

import java.io.IOException;

import novoda.rest.RESTProvider;
import novoda.rest.cursors.JsonCursor;
import novoda.rest.handlers.CursorHandler;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import android.content.ContentValues;
import android.net.Uri;
import android.util.Log;

public class TwitterFeedExampleProvider extends RESTProvider {

	private static final String TAG = TwitterFeedExampleProvider.class.getSimpleName();

	@Override
	public HttpUriRequest deleteRequest(Uri uri, String selection,
			String[] selectionArgs) {
		return null;
	}

	@Override
	public CursorHandler<?> getResponseHandler(Uri uri, int requestType) {
		switch (requestType) {
		case RESTProvider.QUERY:
			return new JsonCursor("results");
		}
		return null;
	}

	@Override
	public HttpUriRequest insertRequest(Uri uri, ContentValues values) {
		return null;
	}

	@Override
	public HttpUriRequest queryRequest(Uri uri, String[] projection,
			String selection, String[] selectionArgs, String sortOrder) {
		HttpGet get = new HttpGet("http://search.twitter.com/search.json?q=droidcon");
		return get;
	}

	@Override
	public HttpUriRequest updateRequest(Uri uri, ContentValues values,
			String selection, String[] selectionArgs) {
		return null;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

}
