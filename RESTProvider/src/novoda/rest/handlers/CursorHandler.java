
package novoda.rest.handlers;

import org.apache.http.client.ResponseHandler;

import android.database.AbstractCursor;
import android.net.Uri;

public interface CursorHandler<T extends AbstractCursor> extends ResponseHandler<T> {
    
    // getMapper(Uri uri);
    
//    public Uri getInsertUri();
//    
//    public int getDeleteCount();
//    
//    public int getUpdateCount();
//    
//    public boolean isSuccessful();
}
