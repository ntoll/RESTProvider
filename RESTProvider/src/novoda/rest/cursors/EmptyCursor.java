/**
 * 
 */

package novoda.rest.cursors;

import android.database.AbstractCursor;

/**
 * This is an empty cursor which will return -1 for ints/longs etc... and an
 * empty string for getString. This is useful for when we don't get any cursor
 * back from a request or in a one to many relationship in order to ensure no
 * exception is thrown on the client side.
 * 
 * @author Carl-Gustaf Harroch
 */
public class EmptyCursor extends AbstractCursor {

    /*
     * (non-Javadoc)
     * @see android.database.AbstractCursor#getColumnNames()
     */
    @Override
    public String[] getColumnNames() {
        return new String[]{};
    }

    /*
     * (non-Javadoc)
     * @see android.database.AbstractCursor#getCount()
     */
    @Override
    public int getCount() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see android.database.AbstractCursor#getDouble(int)
     */
    @Override
    public double getDouble(int column) {
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see android.database.AbstractCursor#getFloat(int)
     */
    @Override
    public float getFloat(int column) {
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see android.database.AbstractCursor#getInt(int)
     */
    @Override
    public int getInt(int column) {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see android.database.AbstractCursor#getLong(int)
     */
    @Override
    public long getLong(int column) {
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see android.database.AbstractCursor#getShort(int)
     */
    @Override
    public short getShort(int column) {
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see android.database.AbstractCursor#getString(int)
     */
    @Override
    public String getString(int column) {
        return "";
    }

    /*
     * (non-Javadoc)
     * @see android.database.AbstractCursor#isNull(int)
     */
    @Override
    public boolean isNull(int column) {
        return true;
    }

}
