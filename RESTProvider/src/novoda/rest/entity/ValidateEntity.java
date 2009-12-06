
package novoda.rest.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.entity.AbstractHttpEntity;

public abstract class ValidateEntity extends AbstractHttpEntity {

    private AbstractHttpEntity entity;

    public ValidateEntity(AbstractHttpEntity entity) {
        this.entity = entity;
    }

    public InputStream getContent() throws IOException, IllegalStateException {
        return entity.getContent();
    }

    public long getContentLength() {
        return entity.getContentLength();
    }

    public boolean isRepeatable() {
        return entity.isRepeatable();
    }

    public boolean isStreaming() {
        return entity.isStreaming();
    }

    public void writeTo(OutputStream outstream) throws IOException {
        entity.writeTo(outstream);
    }
    
    protected abstract boolean isError();
    
    protected abstract String reason();
    
    protected abstract int code();
}
