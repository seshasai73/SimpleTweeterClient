package com.codepath.apps.mysimpletwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by seshasa on 2/8/15.
 */
public class User implements Serializable{
    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;



    private String tagline;
    private long following;
    private long followers;

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getTagline() {
        return tagline;
    }

    public long getFollowing() {
        return following;
    }

    public long getFollowers() {
        return followers;
    }

    public static User fromJSON(JSONObject jsonObject)
    {
        User user = new User();
        try {
            user.name = jsonObject.getString("name");
            user.uid = jsonObject.getLong("id");
            user.screenName =jsonObject.getString("screen_name");
            user.profileImageUrl = jsonObject.getString("profile_image_url");
            user.tagline = jsonObject.getString("description");
            user.followers = jsonObject.getLong("followers_count");
            user.following = jsonObject.getLong("friends_count");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }
}

