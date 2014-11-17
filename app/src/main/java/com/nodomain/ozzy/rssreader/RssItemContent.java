package com.nodomain.ozzy.rssreader;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

public class RssItemContent extends ListActivity {

    RSSFeedTable feed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feed = new RSSFeedTable(this);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onResume() {
        super.onResume();
        feed.open();
        String id = getIntent().getStringExtra("ID");
        Cursor cur = feed.getData(id);
        ListAdapter adapter = new SimpleCursorAdapter(
                this, // Context.
                android.R.layout.two_line_list_item,  // Specify the row template to use (here, two columns bound to the two retrieved cursor
                cur,                                              // Pass in the cursor to bind to.
                new String[] {RSSFeedTable.COLUMN_TITLE,RSSFeedTable.COLUMN_DESCRIPTION},           // Array of cursor columns to bind to.
                new int[] {android.R.id.text1,android.R.id.text2});  // Parallel array of which template objects to bind to those columns.

        // Bind to our new adapter.
        setListAdapter(adapter);

    }
}
