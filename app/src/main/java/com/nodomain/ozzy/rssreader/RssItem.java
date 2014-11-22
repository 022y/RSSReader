package com.nodomain.ozzy.rssreader;
/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Simple struct class to hold the data for one rss item --
 * title, link, description.
 */
public class RssItem  {
    private CharSequence mTitle;
    private CharSequence mLink;
    private CharSequence mRssLink;
    private Date pubDate;
    private CharSequence mDescription;
    private static final String LOG_TAG = "RSSItem";

    public RssItem() {
        mTitle = "";
        mLink = "";
        pubDate = null;
        mDescription = "";
    }

    public RssItem(CharSequence title, CharSequence description, Date pDate, CharSequence link, CharSequence rssLink) {
        mTitle = title;
        mLink = link;
        pubDate = pDate;
        mRssLink = rssLink;
        mDescription = description;
    }
    public CharSequence getDescription() {
        return mDescription;
    }
    public void setDescription(CharSequence description) {
        mDescription = description;
    }
    public CharSequence getLink() {
        return mLink;
    }
    public void setLink(CharSequence link) {
        mLink = link;
    }
    public CharSequence getTitle() {
        return mTitle;
    }
    public CharSequence getRssLink() {
        return mRssLink;
    }
    public void setTitle(CharSequence title) {
        mTitle = title;
    }
    public Date getPubDate()
    {
        return pubDate;
    }
    public void setPubDate(Date date)
    {
        pubDate=date;
    }
    public void setRssLink(CharSequence rssLink)
    {
        mRssLink=rssLink;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd - hh:mm:ss");
        return (getTitle() + "  ( " + sdf.format(this.getPubDate()) + " )");
    }


}