
package novoda.rest.cursors;

import java.io.IOException;
import java.util.Iterator;

import novoda.rest.RESTProvider;
import novoda.rest.handlers.CursorHandler;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import android.database.AbstractCursor;
import android.util.Log;

public class JsonCursor extends AbstractCursor implements CursorHandler<JsonCursor> {

    private static final String TAG = JsonCursor.class.getSimpleName();

    private static ObjectMapper mapper = new ObjectMapper();

    private String root = null;

    private JsonNode current;

    private JsonNode array;

    private String[] columnNames;

    public JsonCursor() {
        this(null);
    }

    public JsonCursor(String rootNode) {
        root = rootNode;
    }

    @Override
    public String[] getColumnNames() {
        return columnNames;
    }

    @Override
    public int getCount() {
        if (array == null)
            return 0;
        return array.size();
    }

    @Override
    public double getDouble(int column) {
        return current.path(columnNames[column]).getDoubleValue();
    }

    @Override
    public float getFloat(int column) {
        return (float)current.path(columnNames[column]).getDoubleValue();
    }

    @Override
    public int getInt(int column) {
        return current.path(columnNames[column]).getIntValue();
    }

    @Override
    public long getLong(int column) {
        return current.path(columnNames[column]).getLongValue();
    }

    @Override
    public short getShort(int column) {
        return (short)current.path(columnNames[column]).getIntValue();
    }

    @Override
    public String getString(int column) {
        return current.path(columnNames[column]).getValueAsText();
    }

    @Override
    public boolean isNull(int column) {
        return current.path(columnNames[column]).isNull();
    }

    @Override
    public boolean onMove(int oldPosition, int newPosition) {
        if (array.isArray())
            current = array.path(newPosition);
        else
            current = array;
        return super.onMove(oldPosition, newPosition);
    }

    public JsonCursor handleResponse(HttpResponse response) throws ClientProtocolException,
            IOException {
        array = mapper.readTree(response.getEntity().getContent());
        
        if (RESTProvider.DEBUG)
            Log.i(TAG, "getting: " + array.toString());
        
        if (root != null) {
            array = array.path(root);
        }
        Iterator<String> it = null;
        int size = 0;
        if (array.isArray()) {
            it = array.path(0).getFieldNames();
            size = array.path(0).size();
        }else {
            it = array.getFieldNames();
            size = array.size();
        }
        columnNames = new String[size];
        int i = 0;
        while (it.hasNext()) {
            columnNames[i++] = it.next();
        }
        return this;
    }
}
