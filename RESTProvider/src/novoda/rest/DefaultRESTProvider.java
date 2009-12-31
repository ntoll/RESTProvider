
package novoda.rest;

import java.util.Map;

import novoda.rest.utils.HTTPUtils;

import org.apache.http.client.methods.HttpUriRequest;

import android.content.ContentValues;
import android.net.Uri;

public abstract class DefaultRESTProvider extends RESTProvider {

    @Override
    public HttpUriRequest deleteRequest(Uri uri, String selection, String[] selectionArgs) {
        return getRequest(uri, RESTProvider.DELETE, HTTPUtils.convertToParams(selection, selectionArgs));
    }

    @Override
    public HttpUriRequest insertRequest(Uri uri, ContentValues values) {
        return getRequest(uri, RESTProvider.INSERT, HTTPUtils.convertToParams(values));
    }

    @Override
    public HttpUriRequest queryRequest(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {   
        return getRequest(uri, RESTProvider.QUERY, HTTPUtils.convertToParams(selection, selectionArgs));
    }

    @Override
    public HttpUriRequest updateRequest(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        return getRequest(uri, RESTProvider.UPDATE, HTTPUtils.convertToParams(values));
    }

    /**
     * @param uri
     * @param type
     * @param params
     * @return
     */
    public abstract HttpUriRequest getRequest(Uri uri, int type, Map<String, String> params);
}
