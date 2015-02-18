package com.codepath.apps.mysimpletwitter;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletwitter.R;
import com.codepath.apps.mysimpletwitter.fragments.HomeTimeLineFragment;
import com.codepath.apps.mysimpletwitter.fragments.MentionsTimeLineFragment;
import com.codepath.apps.mysimpletwitter.fragments.TweetsListFragment;
import com.codepath.apps.mysimpletwitter.models.Tweet;
import com.codepath.apps.mysimpletwitter.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends ActionBarActivity {


   // private TweetsListFragment fragmentTweetsList;
    private HomeTimeLineFragment homeTimeLineFragment;
    private MentionsTimeLineFragment mentionsTimeLineFragment;
    private User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ViewPager vpPager = (ViewPager)findViewById(R.id.viewpager);
        vpPager.setAdapter(new TweetsPagerAdaptor(getSupportFragmentManager()));
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        tabStrip.setViewPager(vpPager);
        getMyUserObject();
        //fragmentTweetsList = (TweetsListFragment)getSupportFragmentManager().findFragmentById(R.id.tweets_list_fragment);
        //if(fragmentTweetsList == null)
        //    Toast.makeText(this,"SOMETHING WENT WRONG",Toast.LENGTH_SHORT).show();
    }

    public void getMyUserObject()
    {
        TwitterClient client;
        client = TwitterApplication.getRestClient();
        client.getUserUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                user = User.fromJSON(response);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (errorResponse != null)
                    Log.d("ERROR", errorResponse.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new_tweet) {
            Intent i = new Intent(this,NewTweetActivity.class);
            startActivityForResult(i,102);
        }
        if(id == R.id.action_profile){
            Intent i = new Intent(this,ProfileActivity.class);
            if(user == null)
                Toast.makeText(this,"NOUSER",Toast.LENGTH_SHORT).show();
            else {
                i.putExtra("user", user);
                startActivityForResult(i, 104);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 102) {
            if(resultCode == RESULT_OK){
                homeTimeLineFragment.refreshList();
                mentionsTimeLineFragment.refreshList();
            }
             //   refreshTweets();
        }
    }
    public class TweetsPagerAdaptor extends FragmentPagerAdapter
    {
        private String tabTitles[] = {"HomeTimeLine","Mentions"};
        public TweetsPagerAdaptor(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0)
            {
                homeTimeLineFragment = new HomeTimeLineFragment();
                return homeTimeLineFragment;
            }
            else
            {
                mentionsTimeLineFragment = new MentionsTimeLineFragment();
                return mentionsTimeLineFragment;
            }
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}
