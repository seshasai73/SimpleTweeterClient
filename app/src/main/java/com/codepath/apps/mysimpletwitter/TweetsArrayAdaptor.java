package com.codepath.apps.mysimpletwitter;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletwitter.models.Tweet;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.Inflater;

/**
 * Created by seshasa on 2/8/15.
 */
public class TweetsArrayAdaptor extends ArrayAdapter<Tweet>{

    SimpleDateFormat sdf;
    public TweetsArrayAdaptor(Context context, ArrayList<Tweet> objects) {
        super(context, android.R.layout.simple_list_item_1 , objects);
        String twitterDateFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        sdf = new SimpleDateFormat(twitterDateFormat);
        sdf.setLenient(true);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }
        Tweet tweet = getItem(position);
        ImageView ivProfileImage = (ImageView)convertView.findViewById(R.id.ivProfileImage);
        TextView tvScreenName = (TextView)convertView.findViewById(R.id.tvScreenName);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvTimeOfTweet = (TextView)convertView.findViewById(R.id.tvTimeOfTweet);
        //TextView tvExp  = (TextView)convertView.findViewById(R.id.tv_Exp);

        Date dtCreatedAt = null;
        String timeOfTweet = null;

        try {
            dtCreatedAt = sdf.parse(tweet.getCreatedAt());
            if(dtCreatedAt != null)
                //timeOfTweet = (String) DateUtils.getRelativeDateTimeString(getContext(), dtCreatedAt.getTime(), DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0);
                timeOfTweet = (String) DateUtils.getRelativeTimeSpanString(dtCreatedAt.getTime(),System.currentTimeMillis(),DateUtils.SECOND_IN_MILLIS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(timeOfTweet == null) {
            tvTimeOfTweet.setText(tweet.getCreatedAt());
        }
        else
        {
            tvTimeOfTweet.setText(timeOfTweet);
        }
        tvScreenName.setText(tweet.getUser().getScreenName());
        tvUserName.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);
        ivProfileImage.setTag(R.id.ivProfileImage, tweet.getUser());
        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getContext(), ProfileActivity.class);
                i.putExtra("user", (Serializable)v.getTag(R.id.ivProfileImage));
                getContext().startActivity(i);
            }
        });

        //tvExp.setText(String.valueOf(tweet.getUid())+ "   "+ getCount());
        return convertView;

    }
}
