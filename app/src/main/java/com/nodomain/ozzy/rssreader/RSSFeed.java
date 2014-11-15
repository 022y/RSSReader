package com.nodomain.ozzy.rssreader;

import java.util.ArrayList;

/**
 * Created by Ozzy on 15.11.2014.
 */
public class RSSFeed {
    public ArrayList<RssItem> feed;

    public RSSFeed()
    {
        feed = new ArrayList<RssItem>();
    }
    public RSSFeed(ArrayList<RssItem> RSSfeed)
    {
        feed=RSSfeed;
    }

    public void add(RssItem item)
    {
        feed.add(item);
    }
}
