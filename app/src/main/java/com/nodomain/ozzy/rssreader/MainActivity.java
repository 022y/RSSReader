package com.nodomain.ozzy.rssreader;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends ListActivity {

    private static String LOG_TAG = "MainActivity";
    RetrieveFeedTask task;
    static RSSFeedTable db;
    static RSSListTable rssList;
    RssListAdapter adapter;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mContext = this;

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewGroup actionBarLayout = (ViewGroup) inflater.inflate(R.layout.main_menu, null);
        RelativeLayout.LayoutParams linnear_lay = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ActionBar.LayoutParams ActBarParams = new ActionBar.LayoutParams(linnear_lay);
        getActionBar().setCustomView(actionBarLayout, ActBarParams);
        getActionBar().setDisplayShowCustomEnabled(true);
        ImageView btnAdd = (ImageView)actionBarLayout.findViewById(R.id.addRss);
        btnAdd.setOnClickListener(AddRss);

        rssList = new RSSListTable(this);
        //initData();
        db = new RSSFeedTable(this);
        updateLists(null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void saveFeed(RSSFeed rssFeed)
    {
        db.open();
        db.delAllRec();
        for (RssItem rss: rssFeed.feed)
        {
            db.addRec(rss.getTitle().toString(), rss.getDescription().toString(),rss.getRssLink().toString());
        }
        db.close();
        setListView();
    }

    void updateLists(String link)
    {
        rssList.open();
        task = new RetrieveFeedTask();
        Cursor rssLst;
        if(link== null) {
             rssLst = rssList.getAllData();
        }
        else {
             rssLst = rssList.getData(link);
        }
        task.execute(rssLst);
        try {
            saveFeed(task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void initData()
    {
        rssList.open();
        rssList.delAllRec();
        rssList.addRec("Yandex", "http://news.yandex.ru/index.rss");
        rssList.addRec("Lenta", "http://lenta.ru/rss");
        //rssList.addRec("Picabu", "http://pikabu.ru/xmlfeeds.php?cmd=popular");
        //rssList.addRec("TASS","http://tass.ru/rss/v2.xml");
        rssList.close();
    }

    void setListView()
    {
        rssList.open();
        Cursor cur = rssList.getAllData();
        List<RssItem> List = new ArrayList<RssItem>();
        while (cur.moveToNext())
        {
            RssItem item = new RssItem();
            item.setTitle(cur.getString(cur.getColumnIndex(RSSListTable.COLUMN_TITLE)));
            item.setLink(cur.getString(cur.getColumnIndex(RSSListTable.COLUMN_LINK)));
            List.add(item);
        }
        rssList.close();

        adapter = new RssListAdapter(this, List);

        // Bind to our new adapter.
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // Получение элемента, который был нажат
        Intent intent = new Intent(this, RssItemContent.class);
        intent.putExtra("ID",adapter.getItem(position).getLink());
        startActivity(intent);
    }

    View.OnClickListener AddRss = new View.OnClickListener()
    {
        @Override
        public void onClick (View view)
        {
            final Dialog dialog = new Dialog(mContext);
            dialog.setTitle("Enter RSS URL");
            dialog.setContentView(R.layout.add_rss_dialog);
            dialog.show();
            final StringBuilder builderURL = new StringBuilder();
            final EditText txt1 = (EditText) dialog.findViewById(R.id.rssLink);
            final StringBuilder builderTitle = new StringBuilder();
            final EditText txt2 = (EditText) dialog.findViewById(R.id.rssTitle);

            Button ok = (Button)dialog.findViewById(R.id.addOk);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builderURL.append(txt1.getText());
                    builderTitle.append(txt2.getText());
                    Log.d(LOG_TAG, "Builder " + builderURL.toString());
                    addRss(builderTitle.toString(),builderURL.toString());
                    dialog.dismiss();
                }
            });
        }
    };

    void addRss(String title,String link)
    {
        rssList.open();
        rssList.addRec(title, link);
        rssList.close();
        setListView();
        updateLists(link);
    }

}
