package com.codepath.apps.mysimpletwitter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.util.Log;
import com.codepath.apps.mysimpletwitter.TwitterApplication;
import com.codepath.apps.mysimpletwitter.TwitterClient;
import com.codepath.apps.mysimpletwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by seshasa on 2/17/15.
 */
public class UserTimeLineFragment extends TweetsListFragment {
    private TwitterClient client;
    private long maxId = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        maxId = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void refreshList() {
        maxId = 0;
        tweets.clear();
        pupulateTimeline();
    }

    public void getMore() {
        maxId = tweets.get(tweets.size() - 1).getUid();
        pupulateTimeline();
    }

    private void pupulateTimeline() {
        String screenName = getArguments().getString("screen_name");
        client.getUserTimeline(screenName, maxId - 1, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("DEBUG", response.toString());
                aTweets.addAll(Tweet.fromJSONArray(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (errorResponse != null)
                    Log.d("ERROR", errorResponse.toString());
            }
        });
    }
    public static UserTimeLineFragment newInstance(String screenName) {
        UserTimeLineFragment userTimeLineFragment = new UserTimeLineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screenName);
        userTimeLineFragment.setArguments(args);
        return userTimeLineFragment;
    }
}
