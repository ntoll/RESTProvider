package novoda.rest.test.apps.twitter;

import android.app.ListActivity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class TwitterFeedExample2 extends ListActivity {

	private static final String TAG = TwitterFeedExample2.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CPAsyncHandler g = new CPAsyncHandler(getContentResolver());

		// it actually don't work with the query options yet
		g.startQuery(1, null, Uri.parse("content://novoda.rest.test.twitter2"),
				null, "q=?", new String[] { "droidcon" }, null);
	}

	private class CPAsyncHandler extends AsyncQueryHandler {

		public CPAsyncHandler(ContentResolver cr) {
			super(cr);
		}

		@Override
		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
			super.onQueryComplete(token, cookie, cursor);
			setListAdapter(new SimpleCursorAdapter(TwitterFeedExample2.this,
					android.R.layout.simple_list_item_2, cursor, new String[] {
							"from_user", "text" }, new int[] {
							android.R.id.text1, android.R.id.text2 }));
		}
	}
}
