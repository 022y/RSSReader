package com.nodomain.ozzy.rssreader;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import java.net.URL;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class RssItemContent extends ListActivity {
    RSSFeedTable feed;
    RSSListTable list;
    RetrieveFeed task;
    String RSSFeedID;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feed = new RSSFeedTable(this);
        list = new RSSListTable(this);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        updateLists();
    }

    void updateLists()
    {
        if(!Functions.isOnline(this)) return;
        task = new RetrieveFeed();
        task.execute();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    class RetrieveFeed extends AsyncTask<Void, Void, RSSFeed> {
        private static final String LOG_TAG = "RetrieveFeed";
        protected RSSFeed doInBackground(Void... voids) {
            list.open();
            RSSFeedID = getIntent().getStringExtra("ID");
            Cursor cur = list.getData(RSSFeedID);
            RSSFeed feed = new RSSFeed();
            while (cur.moveToNext()) {
                try {
                    feed.add(getRssItems(cur.getString(cur.getColumnIndex(RSSListTable.COLUMN_LINK))));
                } catch (Exception e) {
                    return null;
                }
            }
            list.close();
            saveFeed(feed);
            return feed;
        }

        protected void onPostExecute(RSSFeed mFeed) {
            feed.open();
            String id = getIntent().getStringExtra("ID");
            Cursor cur = feed.getData(id);
            progressBar.setVisibility(View.GONE);
            ListAdapter adapter = new SimpleCursorAdapter(
                    RssItemContent.this, // Context.
                    android.R.layout.two_line_list_item,
                    cur,
                    new String[]{RSSFeedTable.COLUMN_TITLE, RSSFeedTable.COLUMN_DESCRIPTION},
                    new int[]{android.R.id.text1, android.R.id.text2},
                    SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER) {
            };
            setListAdapter(adapter);
        }

        void saveFeed(RSSFeed rssFeed)
        {
            feed.open();
            feed.delAllRec();
            for (RssItem rss: rssFeed.feed)
                feed.addRec(rss.getTitle().toString(), rss.getDescription().toString(),rss.getRssLink().toString());
            feed.close();
        }

        public  RSSFeed getRssItems(String feedUrl) {
            RSSFeed rssItems = new RSSFeed();
            try {
                URL url = new URL(feedUrl);
                InputSource inputSource = new InputSource(url.openStream());
                SAXParserFactory saxParserFactory = SAXParserFactory
                        .newInstance();
                SAXParser saxParser = saxParserFactory.newSAXParser();
                XMLReader xmlReader = saxParser.getXMLReader();
                XmlContentHandler xmlContentHandler = new XmlContentHandler();
                xmlReader.setContentHandler(xmlContentHandler);
                xmlReader.parse(inputSource);
                List<RssItem> parsedDataSet = xmlContentHandler
                        .getParsedData();
                rssItems.feed = parsedDataSet;
                for (RssItem item : rssItems.feed) {
                    item.setRssLink(feedUrl);
                }
                Log.d(LOG_TAG, parsedDataSet.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return rssItems;
        }
    }
}
