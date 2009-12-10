
package novoda.rest.cursors.json;

import android.database.AbstractCursor;

public class JsonCursor extends AbstractCursor {

    @Override
    public String[] getColumnNames() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getDouble(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getFloat(int column) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getInt(int column) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long getLong(int column) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public short getShort(int column) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getString(int column) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isNull(int column) {
        // TODO Auto-generated method stub
        return false;
    }
    
    public static class Builder {
        
        public Builder withRootField(String root) {
            return this;
        }
        
        public Builder withIDField(String field) {
            return this;
        }
        
        public Builder addIDField(boolean add) {
            return this;
        }
        
        public Builder addOneToMany(JsonCursor.Builder... fields) {
            return this;
        }
        
        public Builder withArraysAsForeignKeys(boolean auto) {
            return this;
        }

        public Builder withForeignKeyIDFields(String... fields) {
            return null;
        }
        
        public Builder removeFields(String... fields) {
            return this;
        }
        
        public JsonCursor create() {
            return null;
        }
    }
}
