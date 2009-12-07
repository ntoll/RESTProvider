package novoda.rest.cursors;

import android.database.Cursor;
import android.database.CursorWrapper;

public class MappedCursorWrapper extends CursorWrapper {

    public MappedCursorWrapper(Cursor cursor) {
        super(cursor);
    }
}
