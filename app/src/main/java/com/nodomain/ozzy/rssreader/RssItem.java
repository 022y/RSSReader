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
import android.util.Log;

import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Simple struct class to hold the data for one rss item --
 * title, link, description.
 */
public class RssItem  {
    private CharSequence mTitle;
    private CharSequence mLink;
    private Date pubDate;
    private CharSequence mDescription;

    private static String LOG_TAG = "RSSItem";

    public RssItem() {
        mTitle = "";
        mLink = "";
        pubDate = null;
        mDescription = "";
    }

    public RssItem(CharSequence title, CharSequence description, Date pDate, CharSequence link) {
        mTitle = title;
        mLink = link;
        pubDate = pDate;
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
    public void setTitle(CharSequence title) {
        mTitle = title;
    }

    public Date getPubDate()
    {
        return pubDate;
    }

// If we made this class Parcelable, the code would look like...
//    public void writeToParcel(Parcel parcel) {
//        parcel.writeString(mTitle.toString());
//        parcel.writeString(mLink.toString());
//        parcel.writeString(mDescription.toString());
//    }
//
//
//    public static Object createFromParcel(Parcel parcel) {
//        return new RssItem(
//                parcel.readString(),
//                parcel.readString(),
//                parcel.readString());
//    }



    @Override
    public String toString() {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd - hh:mm:ss");

        String result = getTitle() + "  ( " + sdf.format(this.getPubDate()) + " )";
        return result;
    }


}