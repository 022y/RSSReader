package com.nodomain.ozzy.rssreader;

import android.app.Activity;
import android.content.Context;
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
        if(convertView==null){
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.rss_list_item, parent, false);
            viewHolder = new HeaderViewHolder();
            ButterKnife.inject(viewHolder, convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (HeaderViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(list.get(position).getTitle());
        viewHolder.link.setText(list.get(position).getLink());
        return convertView;
    }
}
