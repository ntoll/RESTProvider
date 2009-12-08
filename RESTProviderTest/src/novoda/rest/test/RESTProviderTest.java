
package novoda.rest.test;

import junit.framework.TestCase;
import novoda.rest.cache.UriCache;
import novoda.rest.cursors.JsonCursor;
import novoda.rest.test.mock.MockRESTProvider;
import novoda.rest.test.utils.MockJsonFactory;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import android.database.AbstractCursor;
import android.database.Cursor;
import android.net.Uri;

public class RESTProviderTest extends TestCase {

    private MyRestProvider provider;

    @Override
    protected void setUp() throws Exception {
        provider = new MyRestProvider();
        UriCache.getInstance().put(Uri.parse("content://test/1/f"),
                new MyCursor("content://test/1/f"));
        UriCache.getInstance().put(Uri.parse("content://test/2/f"),
                new MyCursor("content://test/2/f"));
        UriCache.getInstance().put(Uri.parse("content://test/3/f"),
                new MyCursor("content://test/3/f"));
        UriCache.getInstance().put(Uri.parse("content://test/4/f"),
                new MyCursor("content://test/4/f"));
        super.setUp();
    }

    public void testQueryCacheShouldReturnCache() throws Exception {
        Cursor cur = provider.query(Uri.parse("content://test/3/f"), null, null, null, null);
        assertEquals(cur.getString(0), "content://test/3/f");
    }

    public void testCursorMappingAndCaching() throws Exception {
        String json = "{\"sets\":[{\"set_date\":\"2008-10-16T00:00:00Z\",\"show_artist\":\"Armin Van Buuren\",\"title\":\"374\",\"track_plays\":463,\"show_title\":\"A State of Trance\",\"show_alias\":\"ASOT\",\"id\":1,\"small_image\":\"http://s3.amazonaws.com/mugasha/images/1/small.jpg?1229726763\",\"tracks\":[{\"artist\":\"A State of Trance\",\"number\":1,\"end_time\":44,\"title\":\"Intro\",\"start_time\":0},{\"artist\":\"Simon Patterson\",\"number\":2,\"end_time\":266,\"title\":\"Different Feeling\",\"start_time\":44},{\"artist\":\"Luke Chable\",\"number\":3,\"end_time\":325,\"title\":\"Melbourne (Maor Levi's Big Room Mix)\",\"start_time\":310},{\"artist\":\"EDX\",\"number\":4,\"end_time\":325,\"title\":\"Casa Grande\",\"start_time\":635},{\"artist\":\"Marcus Sch\",\"number\":5,\"end_time\":299,\"title\":\"The Last Pluck (Fluffy Mix)\",\"start_time\":960},{\"artist\":\"Arnej feat. Josie\",\"number\":6,\"end_time\":354,\"title\":\"Strangers We've Become (Vocal Mix)\",\"start_time\":1259},{\"artist\":\"Blake Jarrell\",\"number\":7,\"end_time\":234,\"title\":\"Punta Del Este (Arnej's Minimal Drum Dub)\",\"start_time\":1613},{\"artist\":\"Mat Zo\",\"number\":8,\"end_time\":279,\"title\":\"Rush\",\"start_time\":1847},{\"artist\":\"Danjo & Styles\",\"number\":9,\"end_time\":332,\"title\":\"Duende (La Vuelta) (Danjo's Kanova Remix)\",\"start_time\":2126},{\"artist\":\"Hodel & Hornblad\",\"number\":10,\"end_time\":427,\"title\":\"Hydrogen (Sundriver Remix) [Tune Of The Week]\",\"start_time\":2458},{\"artist\":\"Hydroid\",\"number\":11,\"end_time\":398,\"title\":\"The Eternal (Re:Locate Remix)\",\"start_time\":2885},{\"artist\":\"Vengeance\",\"number\":12,\"end_time\":441,\"title\":\"Explain (Reminder Remix)\",\"start_time\":3283},{\"artist\":\"Sied van Riel\",\"number\":13,\"end_time\":260,\"title\":\"Riel People Know (W&W Remix)\",\"start_time\":3724},{\"artist\":\"Heatbeat\",\"number\":14,\"end_time\":352,\"title\":\"Paradise Garage (DJ Eco Remix)\",\"start_time\":3984},{\"artist\":\"FKN feat. Jahala\",\"number\":15,\"end_time\":424,\"title\":\"Still Time? (Aly & Fila Remix)\",\"start_time\":4336},{\"artist\":\"Andy Prinz\",\"number\":16,\"end_time\":331,\"title\":\"Provision (Philippe El Sisi Remix) [Future Favorite]\",\"start_time\":4760},{\"artist\":\"Kandi & Neumann\",\"number\":17,\"end_time\":348,\"title\":\"Lovin' Feeling\",\"start_time\":5091},{\"artist\":\"Ferry Corsten vs. Prodigy\",\"number\":18,\"end_time\":292,\"title\":\"Radio Crash vs. Smack My Bitch Up (Armin van Buuren Mashup)\",\"start_time\":5439},{\"artist\":\"Michael Tsukerman\",\"number\":19,\"end_time\":313,\"title\":\"My Name Is Sawtooth (Sebastian Brandt Remix)\",\"start_time\":5731},{\"artist\":\"Duderstadt vs. Store N Forward\",\"number\":20,\"end_time\":362,\"title\":\"Broken (Nitrous Oxide Remix)\",\"start_time\":6044},{\"artist\":\"8 Wonders\",\"number\":21,\"end_time\":389,\"title\":\"Eventuality\",\"start_time\":6406},{\"artist\":\"Luc Poublon\",\"number\":22,\"end_time\":396,\"title\":\"Quantum Leap [ASOT Radio Classic]\",\"start_time\":6795}],\"file\":\"ASOT-374.mp3\",\"medium_image\":\"http://s3.amazonaws.com/mugasha/images/1/medium.jpg?1229726763\",\"track_likes\":11,\"description\":\"\",\"show_id\":1,\"likes\":2}]}";
        JsonCursor cursor = MockJsonFactory.createFromString(json, "sets", true, "id",
                new String[] {
                    "tracks"
                });
        provider.registerMappedCursor(cursor, Uri.parse("content://sets"));
        JsonCursor fcursor = (JsonCursor)UriCache.getInstance().get(
                Uri.parse("content://sets/1/tracks"));
        assertTrue(fcursor.moveToFirst());
        assertEquals(fcursor.getString(fcursor.getColumnIndexOrThrow("artist")), "A State of Trance");
    }
    
    public void testCursorMappingAndCachingWithItemURI() throws Exception {
        String json = "{\"sets\":[{\"set_date\":\"2008-10-16T00:00:00Z\",\"show_artist\":\"Armin Van Buuren\",\"title\":\"374\",\"track_plays\":463,\"show_title\":\"A State of Trance\",\"show_alias\":\"ASOT\",\"id\":1,\"small_image\":\"http://s3.amazonaws.com/mugasha/images/1/small.jpg?1229726763\",\"tracks\":[{\"artist\":\"A State of Trance\",\"number\":1,\"end_time\":44,\"title\":\"Intro\",\"start_time\":0},{\"artist\":\"Simon Patterson\",\"number\":2,\"end_time\":266,\"title\":\"Different Feeling\",\"start_time\":44},{\"artist\":\"Luke Chable\",\"number\":3,\"end_time\":325,\"title\":\"Melbourne (Maor Levi's Big Room Mix)\",\"start_time\":310},{\"artist\":\"EDX\",\"number\":4,\"end_time\":325,\"title\":\"Casa Grande\",\"start_time\":635},{\"artist\":\"Marcus Sch\",\"number\":5,\"end_time\":299,\"title\":\"The Last Pluck (Fluffy Mix)\",\"start_time\":960},{\"artist\":\"Arnej feat. Josie\",\"number\":6,\"end_time\":354,\"title\":\"Strangers We've Become (Vocal Mix)\",\"start_time\":1259},{\"artist\":\"Blake Jarrell\",\"number\":7,\"end_time\":234,\"title\":\"Punta Del Este (Arnej's Minimal Drum Dub)\",\"start_time\":1613},{\"artist\":\"Mat Zo\",\"number\":8,\"end_time\":279,\"title\":\"Rush\",\"start_time\":1847},{\"artist\":\"Danjo & Styles\",\"number\":9,\"end_time\":332,\"title\":\"Duende (La Vuelta) (Danjo's Kanova Remix)\",\"start_time\":2126},{\"artist\":\"Hodel & Hornblad\",\"number\":10,\"end_time\":427,\"title\":\"Hydrogen (Sundriver Remix) [Tune Of The Week]\",\"start_time\":2458},{\"artist\":\"Hydroid\",\"number\":11,\"end_time\":398,\"title\":\"The Eternal (Re:Locate Remix)\",\"start_time\":2885},{\"artist\":\"Vengeance\",\"number\":12,\"end_time\":441,\"title\":\"Explain (Reminder Remix)\",\"start_time\":3283},{\"artist\":\"Sied van Riel\",\"number\":13,\"end_time\":260,\"title\":\"Riel People Know (W&W Remix)\",\"start_time\":3724},{\"artist\":\"Heatbeat\",\"number\":14,\"end_time\":352,\"title\":\"Paradise Garage (DJ Eco Remix)\",\"start_time\":3984},{\"artist\":\"FKN feat. Jahala\",\"number\":15,\"end_time\":424,\"title\":\"Still Time? (Aly & Fila Remix)\",\"start_time\":4336},{\"artist\":\"Andy Prinz\",\"number\":16,\"end_time\":331,\"title\":\"Provision (Philippe El Sisi Remix) [Future Favorite]\",\"start_time\":4760},{\"artist\":\"Kandi & Neumann\",\"number\":17,\"end_time\":348,\"title\":\"Lovin' Feeling\",\"start_time\":5091},{\"artist\":\"Ferry Corsten vs. Prodigy\",\"number\":18,\"end_time\":292,\"title\":\"Radio Crash vs. Smack My Bitch Up (Armin van Buuren Mashup)\",\"start_time\":5439},{\"artist\":\"Michael Tsukerman\",\"number\":19,\"end_time\":313,\"title\":\"My Name Is Sawtooth (Sebastian Brandt Remix)\",\"start_time\":5731},{\"artist\":\"Duderstadt vs. Store N Forward\",\"number\":20,\"end_time\":362,\"title\":\"Broken (Nitrous Oxide Remix)\",\"start_time\":6044},{\"artist\":\"8 Wonders\",\"number\":21,\"end_time\":389,\"title\":\"Eventuality\",\"start_time\":6406},{\"artist\":\"Luc Poublon\",\"number\":22,\"end_time\":396,\"title\":\"Quantum Leap [ASOT Radio Classic]\",\"start_time\":6795}],\"file\":\"ASOT-374.mp3\",\"medium_image\":\"http://s3.amazonaws.com/mugasha/images/1/medium.jpg?1229726763\",\"track_likes\":11,\"description\":\"\",\"show_id\":1,\"likes\":2}]}";
        JsonCursor cursor = MockJsonFactory.createFromString(json, "sets", true, "id",
                new String[] {
                    "tracks"
                });
        provider.registerMappedCursor(cursor, Uri.parse("content://sets/1"));
        JsonCursor fcursor = (JsonCursor)UriCache.getInstance().get(
                Uri.parse("content://sets/1/tracks"));
        assertTrue(fcursor.moveToFirst());
        assertEquals(fcursor.getString(fcursor.getColumnIndexOrThrow("artist")), "A State of Trance");
    }

    private class MyRestProvider extends MockRESTProvider {

        @Override
        public ResponseHandler<? extends AbstractCursor> getQueryHandler(Uri uri) {
            return new MyCursor("");
        }

        @Override
        public HttpUriRequest queryRequest(Uri uri, String[] projection, String selection,
                String[] selectionArgs, String sortOrder) {
            return new HttpGet("http://google.com");
        }

        @Override
        protected void registerMappedCursor(Cursor cursor, Uri uri) {
            super.registerMappedCursor(cursor, uri);
        }
    }

    private class MyCursor extends JsonCursor {

        private String value;

        public MyCursor(String value) {
            this.value = value;
            // this.setArray()

        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public String getString(int column) {
            return value;
        }

        @Override
        public String[] getForeignFields() {
            return new String[] {
                "f1"
            };
        }

        @Override
        public JsonCursor getForeignCursor(String string) {
            return super.getForeignCursor(string);
        }
    }

}
