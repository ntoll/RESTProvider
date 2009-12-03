
package novoda.rest;

import java.io.IOException;
import java.net.ConnectException;

import novoda.rest.cursors.ErrorCursor;
import novoda.rest.handlers.CursorHandler;

import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * Note: some code is taken from droidfu by Mathias Kaeppler:
 * http://github.com/kaeppler/droid-fu/
 */
public abstract class RESTProvider extends ContentProvider {

    private static final String TAG = RESTProvider.class.getSimpleName();

    private static final int MAX_CONNECTIONS = 6;

    private static final int CONNECTION_TIMEOUT = 10 * 1000;

    protected static final String HTTP_CONTENT_TYPE_HEADER = "Content-Type";

    protected static final String HTTP_USER_AGENT = "Android/RESTProvider";

    private static AbstractHttpClient httpClient;

    static {
        setupHttpClient();
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        try {
            return getResponseHandler(uri, QUERY).handleResponse(
                    httpClient.execute((HttpUriRequest)queryRequest(uri, projection, selection,
                            selectionArgs, sortOrder)));
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
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    public abstract HttpUriRequest deleteRequest(Uri uri, String selection, String[] selectionArgs);

    /**
     * @param uri
     * @param values
     * @return
     */
    public abstract HttpUriRequest insertRequest(Uri uri, ContentValues values);

    /**
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    public abstract HttpUriRequest queryRequest(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder);

    /**
     * @param uri
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */
    public abstract HttpUriRequest updateRequest(Uri uri, ContentValues values, String selection,
            String[] selectionArgs);

    public abstract CursorHandler<?> getResponseHandler(Uri uri, int requestType);

    // Different request type
    static final public int QUERY = 0;

    static final public int INSERT = 1;

    static final public int UPDATE = 2;

    static final public int DELETE = 3;

    public static final boolean DEBUG = true;

    /*
     * Gracefully taken from droidfu - rockon Mathias
     * github.com/kaeppler/droid-fu/
     */
    private static void setupHttpClient() {
        BasicHttpParams httpParams = new BasicHttpParams();
        ConnManagerParams.setTimeout(httpParams, CONNECTION_TIMEOUT);
        ConnManagerParams.setMaxConnectionsPerRoute(httpParams, new ConnPerRouteBean(
                MAX_CONNECTIONS));
        ConnManagerParams.setMaxTotalConnections(httpParams, MAX_CONNECTIONS);
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setUserAgent(httpParams, HTTP_USER_AGENT);
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", PlainSocketFactory.getSocketFactory(), 443));
        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(httpParams, schemeRegistry);
        httpClient = new DefaultHttpClient(cm, httpParams);
    }

}
