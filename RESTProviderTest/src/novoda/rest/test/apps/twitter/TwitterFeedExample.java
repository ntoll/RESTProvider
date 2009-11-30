package novoda.rest.test.apps.twitter;

import android.app.ListActivity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class TwitterFeedExample extends ListActivity {

	private static final String TAG = TwitterFeedExample.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CPAsyncHandler g = new CPAsyncHandler(getContentResolver());
		g.startQuery(1, null, Uri.parse("content://novoda.rest.test.twitter"), null,
				"q=?", new String[] { "droidcon" }, null);
	}

	private class CPAsyncHandler extends AsyncQueryHandler {

		public CPAsyncHandler(ContentResolver cr) {
			super(cr);
		}

		@Override
		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
			super.onQueryComplete(token, cookie, cursor);
			while (cursor.moveToNext()) {
				Log.i(TAG, cursor.getString(cursor
						.getColumnIndexOrThrow("text")));
			}
		}
	}
}
