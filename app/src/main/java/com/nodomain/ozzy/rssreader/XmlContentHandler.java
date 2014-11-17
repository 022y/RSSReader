package com.nodomain.ozzy.rssreader;

import android.util.Log;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XmlContentHandler extends DefaultHandler {

    private static final String LOG_TAG = "XmlContentHandler";

    // used to track of what tags are we
    private boolean inItem = false;

    // accumulate the values
    private StringBuilder mStringBuilder = new StringBuilder();

    // new object
    private RssItem mParsedDataSet = new RssItem();

    // the list of data
    private List<RssItem> mParsedDataSetList = new ArrayList<RssItem>();

    /*
     * Called when parsed data is requested.
     */
    public List<RssItem> getParsedData() {
        Log.v(LOG_TAG, "Returning mParsedDataSetList");
        return this.mParsedDataSetList;
    }

    // Methods below are built in, we just have to do the tweaks.

    /*
     * @Receive notification of the start of an element.
     *
     * @Called in opening tags such as <Owner>
     */
    @Override
    public void startElement(String namespaceURI, String localName,
                             String qName, Attributes atts) throws SAXException {

        if (localName.equals("item")) {
            // meaning new data object will be made
            this.mParsedDataSet = new RssItem();
            this.inItem = true;
        }
    }

    /*
     * @Receive notification of the end of an element.
     *
     * @Called in end tags such as </Owner>
     */
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {

        // Item
        if (this.inItem && localName.equals("item")) {
            this.mParsedDataSetList.add(mParsedDataSet);
            //mParsedDataSet.setParentTag("Owners");
            this.inItem = false;
        }

        else if (this.inItem && localName.equals("title")) {
            mParsedDataSet.setTitle(mStringBuilder.toString().trim());
        }

        else if (this.inItem && localName.equals("description")) {
            mParsedDataSet.setDescription(mStringBuilder.toString().trim());
        }

        else if (this.inItem && localName.equals("pubDate")) {
            mParsedDataSet.setPubDate(new Date(mStringBuilder.toString().trim()));
        }

        // empty our string builder
        mStringBuilder.setLength(0);
    }

    /*
     * @Receive notification of character data inside an element.
     *
     * @Gets be called on the following structure: <tag>characters</tag>
     */
    @Override
    public void characters(char ch[], int start, int length) {
        // append the value to our string builder
        mStringBuilder.append(ch, start, length);
    }
}