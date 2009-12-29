
package novoda.rest.cursors.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import novoda.rest.handlers.QueryHandler;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import android.database.AbstractCursor;
import android.util.Log;

public abstract class XMLCursor extends AbstractCursor implements QueryHandler<XMLCursor> {

    private static final String TAG = XMLCursor.class.getSimpleName();
    private Document document;

    @Override
    public int getCount() {
        int ret = Integer.parseInt(((Node)document.selectSingleNode(getCountXPath())).getStringValue());
        return ret;
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
        String ret = ((Node)document.selectSingleNode(
                getXPath(mPos, getColumnName(column))))
                .getStringValue();
        return ret;
    }

    @Override
    public boolean isNull(int column) {
        return false;
    }

    public XMLCursor handleResponse(HttpResponse response) throws ClientProtocolException,
            IOException {
        String xml = EntityUtils.toString(response.getEntity());
        SAXReader reader = new SAXReader();
        try {
            document = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
        } catch (IllegalStateException e) {
            Log.e(TAG, "an error occured in handleResponse", e);
        } catch (DocumentException e) {
            e.printStackTrace();
            Log.e(TAG, "an error occured in handleResponse", e);
        } 
        return this;
    }
    
    public abstract String getXPath(int row, String column);
    public abstract String getCountXPath();
}
