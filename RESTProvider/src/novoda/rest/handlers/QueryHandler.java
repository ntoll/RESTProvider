
package novoda.rest.handlers;

import org.apache.http.client.ResponseHandler;

import android.database.AbstractCursor;

public interface QueryHandler<T extends AbstractCursor> extends ResponseHandler<T> {
}
