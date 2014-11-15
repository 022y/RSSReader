package com.nodomain.ozzy.rssreader;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity {

    private static String LOG_TAG = "MainActivity";
    RetrieveFeedTask task;
    static DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DB(this);
        getFeed();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        for (RssItem rss: rssFeed.feed)
        {
            db.addRec(rss.getTitle().toString(), rss.getDescription().toString());
        }

    }

    void getFeed()
    {
        task = new RetrieveFeedTask();
        task.execute("");
        try {
            saveFeed(task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }



}
