
package novoda.rest.cursors.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import novoda.rest.RESTProvider;
import novoda.rest.handlers.QueryHandler;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import android.database.AbstractCursor;
import android.util.Log;

public abstract class XMLCursor extends AbstractCursor implements QueryHandler<XMLCursor> {

    private static final String TAG = XMLCursor.class.getSimpleName();

    private Document document;

    @Override
    public int getCount() {
        int ret = 0;
        try {
            ret = Integer.parseInt(((Node)document.selectSingleNode(getCountXPath()))
                    .getStringValue());
        } catch (NumberFormatException e) {
            Log.e(TAG, "Can't parse the count for: " + document.asXML() + " using "
                    + getCountXPath());
        }
        return ret;
    }

    @Override
    public double getDouble(int column) {
        double ret = 0;
        try {
            ret = Double.parseDouble(((Node)document.selectSingleNode(getXPath(mPos,
                    getColumnName(column)))).getStringValue());
        } catch (NumberFormatException e) {
            Log.e(TAG, "Can't parse double for: " + document.asXML() + " using " + getCountXPath());
        }
        return ret;
    }

    @Override
    public float getFloat(int column) {
        float ret = 0;
        try {
            ret = Float.parseFloat(((Node)document.selectSingleNode(getXPath(mPos,
                    getColumnName(column)))).getStringValue());
        } catch (NumberFormatException e) {
            Log.e(TAG, "Can't parse float for: " + document.asXML() + " using " + getCountXPath());
        }
        return ret;
    }

    @Override
    public int getInt(int column) {
        int ret = 0;
        try {
            ret = Integer.parseInt(((Node)document.selectSingleNode(getXPath(mPos,
                    getColumnName(column)))).getStringValue());
        } catch (NumberFormatException e) {
            Log.e(TAG, "Can't parse int for: " + document.asXML() + " using " + getCountXPath());
        }
        return ret;
    }

    @Override
    public long getLong(int column) {
        long ret = 0;
        try {
            ret = Long.parseLong(((Node)document.selectSingleNode(getXPath(mPos,
                    getColumnName(column)))).getStringValue());
        } catch (NumberFormatException e) {
            Log.e(TAG, "Can't parse long for: " + document.asXML() + " using " + getCountXPath());
        }
        return ret;
    }

    @Override
    public short getShort(int column) {
        short ret = 0;
        try {
            ret = Short.parseShort(((Node)document.selectSingleNode(getXPath(mPos, getColumnName(column))))
                    .getStringValue());
        } catch (NumberFormatException e) {
            Log.e(TAG, "Can't parse short for: " + document.asXML() + " using " + getCountXPath());
        }
        return ret;
    }

    @Override
    public String getString(int column) {
        String ret = ((Node)document.selectSingleNode(getXPath(mPos, getColumnName(column))))
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
        if (RESTProvider.DEBUG)
            Log.d(TAG, xml);
        SAXReader reader = new SAXReader();
        try {
            document = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
        } catch (IllegalStateException e) {
            Log.e(TAG, "an error occured in handleResponse", e);
        } catch (DocumentException e) {
            Log.e(TAG, "an error occured in handleResponse", e);
        }
        return this;
    }

    public abstract String getXPath(int row, String column);

    public abstract String getCountXPath();
}
