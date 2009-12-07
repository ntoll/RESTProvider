package novoda.rest.cursors;

import android.database.Cursor;

public interface One2ManyMapping {
    public abstract String[] getForeignFields();
    public abstract Cursor getForeignCursor(String field);
}
