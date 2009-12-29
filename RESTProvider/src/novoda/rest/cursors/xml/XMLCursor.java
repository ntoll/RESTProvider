package novoda.rest.cursors.xml;

import java.io.IOException;

import novoda.rest.handlers.QueryHandler;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;

import android.database.AbstractCursor;
import android.util.Log;

public class XMLCursor extends AbstractCursor implements QueryHandler<XMLCursor> {

    private static final String TAG = XMLCursor.class.getSimpleName();

    @Override
    public String[] getColumnNames() {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public double getDouble(int column) {
        return 0;
    }

    @Override
    public float getFloat(int column) {
        return 0;
    }

    @Override
    public int getInt(int column) {
        return 0;
    }

    @Override
    public long getLong(int column) {
        return 0;
    }

    @Override
    public short getShort(int column) {
        return 0;
    }

    @Override
    public String getString(int column) {
        return null;
    }

    @Override
    public boolean isNull(int column) {
        return false;
    }

    public XMLCursor handleResponse(HttpResponse response) throws ClientProtocolException,
            IOException {
        Log.i(TAG, EntityUtils.toString(response.getEntity()));
        return null;
    }

}
