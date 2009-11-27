
package novoda.rest.cursors;

import android.database.MatrixCursor;

/**
 * Custom cursor to return upon error. The cause of the error could be anything
 * from IO error, connection error to parsing error.
 */
public class ErrorCursor {

    private static final String TYPE_COLUMN = "type";

    private static final String MESSAGE_COLUMN = "message";

    private String message;

    private int type;

    private MatrixCursor cursor;

    public ErrorCursor() {
        this(0, "");
    }

    public ErrorCursor(int type, String message) {
        cursor = new MatrixCursor(columns, 1);
        setMessage(message);
        setType(type);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setType(int type) {
        this.type = type;
    }

    public MatrixCursor build() {
        cursor.addRow(new Object[] {
                type, message
        });
        return cursor;
    }

    public static MatrixCursor getCursor(int type, String message) {
        ErrorCursor cursor = new ErrorCursor();
        return cursor.build();
    }

    private static String[] columns = new String[] {
            TYPE_COLUMN, MESSAGE_COLUMN
    };

    /** Custom error codes **/
    public static final int CONNECTION_ERROR = 0;

    public static final int CONNECTION_TIMEOUT = 1;

    public static final int NO_CONNECTION = 2;

    public static final int CONNECTION_LOST = 3;

    public static final int IO_ERROR = 11;

    public static final int PARSING_ERROR = 12;
}
