package novoda.rest.test.mock;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;

import android.content.ContentValues;
import android.database.AbstractCursor;
import android.net.Uri;
import novoda.rest.RESTProvider;

public class MockRESTProvider extends RESTProvider {

	@Override
	public HttpUriRequest deleteRequest(Uri uri, String selection,
			String[] selectionArgs) {
		throw new NoSuchMethodError();
	}

	@Override
	public ResponseHandler<? extends Integer> getDeleteHandler(Uri uri) {
		throw new NoSuchMethodError();
	}

	@Override
	public ResponseHandler<? extends Uri> getInsertHandler(Uri uri) {
		throw new NoSuchMethodError();
	}

	@Override
	public ResponseHandler<? extends AbstractCursor> getQueryHandler(Uri uri) {
		throw new NoSuchMethodError();
	}

	@Override
	public ResponseHandler<? extends Integer> getUpdateHandler(Uri uri) {
		throw new NoSuchMethodError();
	}

	@Override
	public HttpUriRequest insertRequest(Uri uri, ContentValues values) {
		throw new NoSuchMethodError();
	}

	@Override
	public HttpUriRequest queryRequest(Uri uri, String[] projection,
			String selection, String[] selectionArgs, String sortOrder) {
		throw new NoSuchMethodError();
	}

	@Override
	public HttpUriRequest updateRequest(Uri uri, ContentValues values,
			String selection, String[] selectionArgs) {
		throw new NoSuchMethodError();
	}

	@Override
	public String getType(Uri uri) {
		throw new NoSuchMethodError();
	}

}
