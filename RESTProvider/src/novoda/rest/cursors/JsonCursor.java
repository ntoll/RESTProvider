
package novoda.rest.cursors;

import java.io.IOException;

import novoda.rest.handlers.CursorHandler;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import android.database.AbstractCursor;

public class JsonCursor extends AbstractCursor implements CursorHandler<JsonCursor> {

    private static ObjectMapper mapper = new ObjectMapper();

    private String root = null;

    public JsonCursor() {
        this(null);
    }

    public JsonCursor(String rootNode) {
        root = rootNode;
    }

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

    public JsonCursor handleResponse(HttpResponse response) throws ClientProtocolException,
            IOException {
        JsonNode node = mapper.readTree(response.getEntity().getContent());
        if (root != null) {
            node = node.path(root);
        }
        
        return null;
    }

}
