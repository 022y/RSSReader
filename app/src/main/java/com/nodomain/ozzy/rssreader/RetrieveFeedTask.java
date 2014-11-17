package com.nodomain.ozzy.rssreader;

import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import java.net.URL;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

class RetrieveFeedTask extends AsyncTask<Cursor, Void, RSSFeed> {

    private static final String LOG_TAG = "RetrieveFeedTask";

    private Exception exception;

    protected RSSFeed doInBackground(Cursor... cursor) {
        Cursor cur = cursor[0];
        RSSFeed feed = new RSSFeed();
        while(cur.moveToNext()) {
            try {
                 feed.add(getRssItems(cur.getString(cur.getColumnIndex(RSSListTable.COLUMN_LINK))));
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }
        return feed;
    }

    protected void onPostExecute(RSSFeed feed) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }


    public static RSSFeed getRssItems(String feedUrl) {

        RSSFeed rssItems = new RSSFeed();

        try {
            //open an URL connection make GET to the server and
            //take xml RSS data
            URL url = new URL(feedUrl);
            //HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputSource inputSource = new InputSource(url.openStream());

            // instantiate SAX parser
            SAXParserFactory saxParserFactory = SAXParserFactory
                    .newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();

            // get the XML reader
            XMLReader xmlReader = saxParser.getXMLReader();

            // prepare and set the XML content or data handler before
            // parsing
            XmlContentHandler xmlContentHandler = new XmlContentHandler();
            xmlReader.setContentHandler(xmlContentHandler);

            // parse the XML input source
            xmlReader.parse(inputSource);

            // put the parsed data to a List
            List<RssItem> parsedDataSet = xmlContentHandler
                    .getParsedData();
            rssItems.feed=parsedDataSet;
            for(RssItem item: rssItems.feed)
            {
                item.setRssLink(feedUrl);
            }
            Log.d(LOG_TAG, parsedDataSet.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rssItems;
    }
}