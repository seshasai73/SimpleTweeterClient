package com.codepath.apps.mysimpletwitter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.mysimpletwitter.EndlessScrollListener;
import com.codepath.apps.mysimpletwitter.ProfileActivity;
import com.codepath.apps.mysimpletwitter.R;
import com.codepath.apps.mysimpletwitter.TweetsArrayAdaptor;
import com.codepath.apps.mysimpletwitter.models.Tweet;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by seshasa on 2/16/15.
 */
public class TweetsListFragment extends Fragment {
    public ArrayList<Tweet> getTweets() {
        return tweets;
    }

    protected ArrayList<Tweet> tweets;
    protected TweetsArrayAdaptor aTweets;
    protected ListView lvTweets;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_tweets_list,container,false);
        lvTweets = (ListView)v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                getMore();
            }
        });
        /*
        lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemClicked(aTweets.getItem(position));
            }
        });
        */
        refreshList();
        return v;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdaptor(getActivity(),tweets);

    }
    public void refreshList()
    {

    }
    public void getMore()
    {

    }
    public void itemClicked(Tweet tweet)
    {

        Toast.makeText(getActivity(),tweet.getBody()+" -- " + tweet.getUser().getName(),Toast.LENGTH_SHORT).show();
        //Toast.makeText(getActivity(),"CLICKED",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getActivity(),ProfileActivity.class);
        i.putExtra("user",tweet.getUser());
        startActivityForResult(i,104);

    }
}
