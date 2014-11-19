package com.nodomain.ozzy.rssreader;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class RssListAdapter extends BaseAdapter {
    private static final String LOG_TAG = "RssListAdapter";
    static class HeaderViewHolder {
        @InjectView(R.id.text1)
        TextView title;
        @InjectView(R.id.text2)
        TextView link;
    }

    Context mContext;
    List<RssItem> list;

    public RssListAdapter(Context context, List<RssItem> rssList)
    {
        list = rssList;
        Log.d(LOG_TAG, "Constructor RssListAdapter");
        mContext = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public RssItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder viewHolder;
        // The convertView argument is essentially a "ScrapView" as described is Lucas post
        // http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
        // It will have a non-null value when ListView is asking you recycle the row layout.
        // So, when convertView is not null, you should simply update its contents instead of inflating a new row    layout.
        if(convertView==null){
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.rss_list_item, parent, false);
            // well set up the ViewHolder
            viewHolder = new HeaderViewHolder();
            ButterKnife.inject(viewHolder, convertView);
            // store the holder with the view.
            convertView.setTag(viewHolder);
        }else{
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (HeaderViewHolder) convertView.getTag();
        }
        // object item based on the position
        // assign values if the object is not null
        // get the TextView from the ViewHolder and then set the text (item name) and tag (item ID) values
        viewHolder.title.setText(list.get(position).getTitle());
        viewHolder.link.setText(list.get(position).getLink());
        return convertView;
    }
}
