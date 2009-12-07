
package novoda.rest;

import java.io.IOException;
import java.net.ConnectException;

import novoda.rest.cursors.ErrorCursor;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
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
import org.apache.http.protocol.HttpRequestInterceptorList;
import org.apache.http.protocol.HttpResponseInterceptorList;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.AbstractCursor;
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

    protected static AbstractHttpClient httpClient;

    static {
        setupHttpClient();
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        try {
            return getDeleteHandler(uri).handleResponse(
                    httpClient
                            .execute((HttpUriRequest)deleteRequest(uri, selection, selectionArgs)));
        } catch (ClientProtocolException e) {
            Log.e(TAG, "an error occured in delete", e);
            return -1;
        } catch (IOException e) {
            Log.e(TAG, "an error occured in delete", e);
            return -1;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        try {
            return getInsertHandler(uri).handleResponse(
                    httpClient.execute((HttpUriRequest)insertRequest(uri, values)));
        } catch (ClientProtocolException e) {
            Log.e(TAG, "an error occured in insert", e);
            return null;
        } catch (IOException e) {
            Log.e(TAG, "an error occured in insert", e);
            return null;
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        try {
            HttpUriRequest request = queryRequest(uri, projection, selection, selectionArgs,
                    sortOrder);
            
            if (DEBUG)
                Log.i(TAG, "will query: " + request.getURI());

            return getQueryHandler(uri).handleResponse(httpClient.execute(request));
            
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
        try {
            return getUpdateHandler(uri).handleResponse(
                    httpClient.execute(updateRequest(uri, values, selection, selectionArgs)));
        } catch (ClientProtocolException e) {
            Log.e(TAG, "an error occured in update", e);
            return -1;
        } catch (IOException e) {
            Log.e(TAG, "an error occured in update", e);
            return -1;
        }
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

    public abstract ResponseHandler<? extends AbstractCursor> getQueryHandler(Uri uri);

    public abstract ResponseHandler<? extends Uri> getInsertHandler(Uri uri);

    public abstract ResponseHandler<? extends Integer> getUpdateHandler(Uri uri);

    public abstract ResponseHandler<? extends Integer> getDeleteHandler(Uri uri);

    public void preProcess(HttpUriRequest request) {
    }

    public void postProcess(HttpResponse response) {
    }

    protected HttpRequestInterceptorList getHttpRequestInterceptorList() {
        return null;
    }

    protected HttpResponseInterceptorList getHttpResponseInterceptorList() {
        return null;
    }

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
