package com.nodomain.ozzy.rssreader;

import java.util.ArrayList;
import java.util.List;

public class RSSFeed {
    public List<RssItem> feed;

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
    public void add(RSSFeed items)
    {
        for(RssItem item: items.feed)
            feed.add(item);
    }
}
