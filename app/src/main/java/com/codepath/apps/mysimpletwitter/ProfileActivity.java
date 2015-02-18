package com.codepath.apps.mysimpletwitter;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.util.Log;
import com.codepath.apps.mysimpletwitter.R;
import com.codepath.apps.mysimpletwitter.fragments.HomeTimeLineFragment;
import com.codepath.apps.mysimpletwitter.fragments.UserTimeLineFragment;
import com.codepath.apps.mysimpletwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class ProfileActivity extends ActionBarActivity {
    private TwitterClient client;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user = (User) getIntent().getSerializableExtra("user");
        if(user == null) {
            client = TwitterApplication.getRestClient();
            client.getUserUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    user = User.fromJSON(response);
                    getSupportActionBar().setTitle("@" + user.getScreenName());
                    populateProfileHeader(user);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    if (errorResponse != null)
                        Log.d("ERROR", errorResponse.toString());
                }
            });
        }
        else {
            populateProfileHeader(user);
        }
        if (savedInstanceState == null) {
            String screenName = ((User) getIntent().getSerializableExtra("user")).getScreenName();
            UserTimeLineFragment userTimeLineFragment = UserTimeLineFragment.newInstance(screenName);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, userTimeLineFragment);
            ft.commit();

        }
    }

    public void populateProfileHeader(User user) {
        TextView tvName = (TextView)findViewById(R.id.tvName);
        TextView tvTagline = (TextView)findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView)findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView)findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView)findViewById(R.id.ivProfileImage);
        tvName.setText(user.getName());
        tvTagline.setText(user.getTagline());
        tvFollowers.setText(user.getFollowers() + " Followers");
        tvFollowing.setText(user.getFollowing() + " Following");
        Picasso.with(this).load(user.getProfileImageUrl()).into(ivProfileImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
