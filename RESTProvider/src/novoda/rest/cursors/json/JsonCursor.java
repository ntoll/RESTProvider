
package novoda.rest.cursors.json;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import novoda.rest.RESTProvider;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.database.AbstractCursor;
import android.os.Bundle;
import android.util.Log;

public class JsonCursor extends AbstractCursor {

    private static final String TAG = JsonCursor.class.getSimpleName();

    private static final String COLUMN_ID = "_id";

    private static ObjectMapper mapper = new ObjectMapper();

    private String root = null;

    private JsonNode current;

    private JsonNode array;

    private String[] columnNames;

    private boolean withId;

    private String idNode = null;

    private String[] foreignKeys;

    /**
     * Very basic cursor which will parse the response into a JSON object using
     * {@link handleResponse(HttpResponse response)}. The key for the cursor is
     * the keys within the JSON. If the JSON is a JSONArray, the cursor will
     * have the same size as the JSON array. If not, the cursor will return size
     * 1.
     */
    public JsonCursor() {
        this(null);
    }

    /**
     * If the JSON results are contained within an array, you can specify the
     * root node of the array in order to set it as base for the cursor.
     * 
     * @param rootNode, the root node of the JSON array - e.g. {"rootNode":[]}
     */
    public JsonCursor(String rootNode) {
        this(rootNode, false, null);
    }

    /**
     * Many components will use the field "_id" within a cursor (e.g.
     * SimpleCursorHandler). In order to add an extra field to the cursor, you
     * can construct the cursor accordingly.
     * 
     * @param rootNode the root node of the JSON array - can be null
     * @param withId true if the extra field should be added, false otherwise -
     *            default is false
     */
    public JsonCursor(String rootNode, boolean withId) {
        this(rootNode, withId, null);
    }

    /**
     * Same as above but instead of adding an extra field, it maps the JSON
     * field given with idNode to the "_id" field in the cursor.
     * 
     * @param rootNode the root node of the JSON array - can be null
     * @param withId
     * @param idNode the node to be transformed to field _id within the cursor
     */
    public JsonCursor(String rootNode, boolean withId, String idNode) {
        root = rootNode;
        this.withId = withId;
        this.idNode = idNode;
    }

    public JsonCursor withForeignKey(String... keys) {
        foreignKeys = keys;
        return this;
    }

    @Override
    public String[] getColumnNames() {
        if (withId && idNode == null) {
            columnNames[columnNames.length - 1] = COLUMN_ID;
        }
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
        if (withId && idNode == null && columnNames[column].equals(COLUMN_ID)) {
            return mPos;
        }

        if (columnNames[column].equals(COLUMN_ID))
            return current.path(idNode).getIntValue();
        else
            return current.path(columnNames[column]).getIntValue();
    }

    @Override
    public long getLong(int column) {
        if (columnNames[column].equals(COLUMN_ID))
            return current.path(idNode).getLongValue();
        else
            return current.path(columnNames[column]).getLongValue();
    }

    @Override
    public short getShort(int column) {
        return (short)current.path(columnNames[column]).getIntValue();
    }

    @Override
    public String getString(int column) {
        if (columnNames[column].equals(COLUMN_ID))
            return current.path(idNode).getValueAsText();
        else
            return current.path(columnNames[column]).getValueAsText();
    }

    @Override
    public boolean isNull(int column) {
        return current.path(columnNames[column]).isNull();
    }

    @Override
    public Bundle getExtras() {
        Bundle b = new Bundle();
        String[] ids = new String[array.size()];
        for (int i = 0; i < array.size(); i++) {
            ids[i] = array.get(i).path(idNode).getValueAsText();
        }
        b.putStringArray("ids", ids);
        b.putStringArray("foreign_keys", foreignKeys);
        b.putString("json", array.toString());
        return b;
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
        return init();
    }
    
    public JsonCursor handleResponse(String json) throws JsonProcessingException, IOException {
        array = mapper.readTree(json);
        return init();
    }

    private JsonCursor init() {
        if (RESTProvider.DEBUG)
            Log.i(TAG, "JSON: " + array.toString());

        if (root != null) {
            array = array.path(root);
        }
        Iterator<String> it = null;
        int size = 0;
        if (array.isArray()) {
            it = array.path(0).getFieldNames();
            size = array.path(0).size();
        } else {
            it = array.getFieldNames();
            size = array.size();
        }

        if (foreignKeys != null) {
            size -= foreignKeys.length;
        }

        if (withId && idNode == null)
            size += 1;

        columnNames = new String[size];
        int i = 0;
        String node;
        while (it.hasNext()) {
            node = it.next();
            if (foreignKeys != null && Arrays.asList(foreignKeys).contains(node))
                continue;

            if (idNode != null && node.equals(idNode)) {
                node = COLUMN_ID;
            }
            columnNames[i++] = node;
        }
        return this;
    }

    public JsonCursor getForeignCursor(String string) {
        JsonCursor cursor = new JsonCursor();
        cursor.setArray(current.path(string));
        cursor.init();
        return cursor;
    }

    public JsonCursor getForeignCursor(int index, String string) {
        JsonCursor cursor = new JsonCursor();
        cursor.setArray(array.get(index).path(string));
        cursor.init();
        return cursor;
    }

    protected void setArray(JsonNode node) {
        this.array = node;
    }

    public String[] getForeignFields() {
        return foreignKeys;
    }

    public String getPrimaryFieldName() {
        return (idNode != null) ? idNode : (withId) ? COLUMN_ID : null;
    }

    public static class Builder {
        
        private String root = null;

        private String[] columnNames;

        private boolean withId;

        private String idNode = null;

        private String[] foreignKeys;
        
        public Builder withRootField(String root) {
            this.root = root;
            return this;
        }

        public Builder withIDField(String field) {
            idNode = field;
            return this;
        }

        public Builder addIDField(boolean add) {
            withId = add;
            return this;
        }

        public Builder addOneToMany(JsonCursor.Builder... fields) {
            return this;
        }

        public Builder removeFields(String... fields) {
            return this;
        }

        public Builder withArraysAsForeignKeys(boolean auto) {
            new UnsupportedOperationException("use addOneToMany instead");
            return null;
        }

        public JsonCursor create() {
            return null;
        }
    }
}
