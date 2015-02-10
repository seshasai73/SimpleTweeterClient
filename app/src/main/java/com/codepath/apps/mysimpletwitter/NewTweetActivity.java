package com.codepath.apps.mysimpletwitter;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.codepath.apps.mysimpletwitter.R;
import com.codepath.apps.mysimpletwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class NewTweetActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tweet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final TextView tvCharLeft = (TextView)findViewById(R.id.tvCharLeft);
        EditText etTweet = (EditText)findViewById(R.id.etTweet);
        etTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s != null && s.toString() != null) {
                    int charRemaining = 140 - s.toString().length();
                    String remainingString = charRemaining + " Remaining";
                    tvCharLeft.setText(remainingString);

                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_tweet, menu);
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
            onTweetButtonClicked(null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onTweetButtonClicked(View view)
    {
        EditText etTweet = (EditText)findViewById(R.id.etTweet);
        //Toast.makeText(this,etTweet.getText().toString(),Toast.LENGTH_SHORT).show();
        String status = etTweet.getText().toString();
        if(status.length() > 140)
        {
            Toast.makeText(this,"TOO LONG TO TWEET",Toast.LENGTH_SHORT).show();
            return;
        }
        TwitterClient client;
        client = TwitterApplication.getRestClient();
        client.postTweet(status,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                Toast.makeText(NewTweetActivity.this,"SUCCESSFULLY POSTED",Toast.LENGTH_SHORT).show();
                Intent i = new Intent();
                setResult(RESULT_OK,i);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("ERROR",errorResponse.toString());
                Toast.makeText(NewTweetActivity.this,"SORRY SOMETHING WRONG",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void onCancelButtonClicked(View view)
    {
        Intent i = new Intent();
        setResult(RESULT_CANCELED,i);
        finish();
    }
}
