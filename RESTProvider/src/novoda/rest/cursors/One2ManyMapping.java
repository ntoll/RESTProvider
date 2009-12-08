package novoda.rest.cursors;

import android.database.Cursor;

public interface One2ManyMapping {
    public String[] getForeignFields();
    public Cursor getForeignCursor(int id, String field);
    public String getPrimaryFieldName();
}
