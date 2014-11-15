package com.nodomain.ozzy.rssreader;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Ozzy on 15.11.2014.
 */
class RetrieveFeedTask extends AsyncTask<String, Void, RSSFeed> {

    private Exception exception;

    protected RSSFeed doInBackground(String... urls) {
        try {
            return getRssItems("http://news.yandex.ru/index.rss");
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
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
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();

                DocumentBuilderFactory dbf = DocumentBuilderFactory
                        .newInstance();
                DocumentBuilder dBuilder = dbf.newDocumentBuilder();

                //using db (Document Builder) parse xml data and assign
                //it to Element
                Document document = dBuilder.parse(is);
                Element element = document.getDocumentElement();

                //take rss nodes to NodeList
                NodeList nodeList = element.getElementsByTagName("item");

                if (nodeList.getLength() > 0) {
                    for (int i = 0; i < nodeList.getLength(); i++) {

                        //take each entry (corresponds to <item></item> tags in
                        //xml data

                        Element entry = (Element) nodeList.item(i);

                        Element _titleE = (Element) entry.getElementsByTagName(
                                "title").item(0);
                        Element _descriptionE = (Element) entry
                                .getElementsByTagName("description").item(0);
                        Element _pubDateE = (Element) entry
                                .getElementsByTagName("pubDate").item(0);
                        Element _linkE = (Element) entry.getElementsByTagName(
                                "link").item(0);

                        String _title = _titleE.getFirstChild().getNodeValue();
                        String _description = _descriptionE.getFirstChild().getNodeValue();
                        Date _pubDate = new Date(_pubDateE.getFirstChild().getNodeValue());
                        String _link = _linkE.getFirstChild().getNodeValue();

                        //create RssItemObject and add it to the ArrayList
                        RssItem rssItem = new RssItem(_title, _description,
                                _pubDate, _link);


                        rssItems.add(rssItem);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Log.d(LOG_TAG, rssItems.toString());
        return rssItems;
    }
}