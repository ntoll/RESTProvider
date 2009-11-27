
package novoda.rest;

import java.io.IOException;
import java.net.ConnectException;

import novoda.rest.cursors.ErrorCursor;
import novoda.rest.handlers.CursorHandler;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.github.droidfu.http.BetterHttpRequest;

public abstract class RESTProvider extends ContentProvider {

    private static final String TAG = RESTProvider.class.getSimpleName();

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        Cursor cursor = null;
        BetterHttpRequest request = (BetterHttpRequest)queryRequest(uri, projection, selection,
                selectionArgs, sortOrder);
        try {
            getResponseHandler(uri, QUERY).handleResponse((HttpResponse)request.send());
        } catch (ConnectException e) {
            Log.w(TAG, "an error occured in query", e);
            return ErrorCursor.getCursor(0, e.getMessage());
        } catch (ClientProtocolException e) {
            Log.w(TAG, "an error occured in query", e);
            return ErrorCursor.getCursor(0, e.getMessage());
        } catch (IOException e) {
            Log.w(TAG, "an error occured in query", e);
            return ErrorCursor.getCursor(0, e.getMessage());
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    /**
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    public abstract HttpRequest deleteRequest(Uri uri, String selection, String[] selectionArgs);

    /**
     * @param uri
     * @param values
     * @return
     */
    public abstract HttpRequest insertRequest(Uri uri, ContentValues values);

    /**
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    public abstract HttpRequest queryRequest(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder);

    /**
     * @param uri
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */
    public abstract HttpRequest updateRequest(Uri uri, ContentValues values, String selection,
            String[] selectionArgs);

    public abstract CursorHandler<?> getResponseHandler(Uri uri, int requestType);

    // Different request type
    static final public int QUERY = 0;

    static final public int INSERT = 1;

    static final public int UPDATE = 2;

    static final public int DELETE = 3;

}
