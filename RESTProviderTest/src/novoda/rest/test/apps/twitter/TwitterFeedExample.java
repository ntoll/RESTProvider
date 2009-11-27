package novoda.rest.test.apps.twitter;

import android.app.ListActivity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

public class TwitterFeedExample extends ListActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Mu g = new Mu(this.getContentResolver());
		g.startQuery(1, null, Uri.parse("content://com.test.new"), null, null,
				null, null);
	}

	private class Mu extends AsyncQueryHandler {
		public Mu(ContentResolver cr) {
			super(cr);
		}

		@Override
		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
			super.onQueryComplete(token, cookie, cursor);
			while (cursor.moveToNext()) {
//
//				Log.i("test", j.hasMessages(12)
//						+ cursor.getString(cursor
//								.getColumnIndexOrThrow("from_user")));
			}
			getMainLooper().quit();
		}
	}
	
	
}
