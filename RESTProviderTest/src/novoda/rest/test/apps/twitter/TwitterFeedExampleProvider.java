package novoda.rest.test.apps.twitter;

import novoda.rest.RESTProvider;
import novoda.rest.handlers.CursorHandler;

import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpGet;

import android.content.ContentValues;
import android.net.Uri;

public class TwitterFeedExampleProvider extends RESTProvider {

	@Override
	public HttpRequest deleteRequest(Uri uri, String selection,
			String[] selectionArgs) {
		return null;
	}

	@Override
	public CursorHandler<?> getResponseHandler(Uri uri, int requestType) {
		switch (requestType) {
		case RESTProvider.QUERY:
			
			break;
		}
		return null;
	}

	@Override
	public HttpRequest insertRequest(Uri uri, ContentValues values) {
		return null;
	}

	@Override
	public HttpRequest queryRequest(Uri uri, String[] projection,
			String selection, String[] selectionArgs, String sortOrder) {
		HttpGet get = new HttpGet("http://search.twitter.com/search.json");
		return get;
	}

	@Override
	public HttpRequest updateRequest(Uri uri, ContentValues values,
			String selection, String[] selectionArgs) {
		return null;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

}
