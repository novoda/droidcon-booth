package com.novoda.canvas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.novoda.canvas.base.NovodaActivityTest;
import com.novoda.canvas.base.RandomAnimationFactory;
import com.novoda.canvas.base.RandomColorFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.ConfigurationBuilder;

import static com.novoda.canvas.NovodaActivity.RANDOM;

public class DroidconTwitterSearchActivityTest extends NovodaActivityTest {
    private static final String FAILURE_TEXT = "Tweet to #DroidconUK15 and it won't be shown here...because I've thrown an exception.";

    private Activity activity;
    private TextView tweetView;

    @Override
    public void startTestFor(Activity activity) {
        this.activity = activity;

        tweetView = new TextView(activity);
        tweetView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        tweetView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tweetView.setVisibility(View.GONE);
        tweetView.setGravity(Gravity.CENTER);
        RandomColorFactory randomColorFactory = new RandomColorFactory(RANDOM);
        tweetView.setTextColor(randomColorFactory.getColor());
        getParent(activity).addView(tweetView);

        RandomAnimationFactory randomAnimationFactory = new RandomAnimationFactory(activity.getApplicationContext(), RANDOM);
        new SearchOnTwitter(RANDOM, randomAnimationFactory).execute("%23droidconuk15");
    }

    private class SearchOnTwitter extends AsyncTask<String, Void, Integer> {

        private static final String TWIT_CONS_KEY = "af5LHUx0kN0IeUE3yescOYRdE"; // Made just for this event, will be deleted 1/11/2015
        private static final String TWIT_CONS_SEC_KEY = "RRnX5uFbfN87eQJM7BtfkLcFgbjY4eNa5atm4j1p5ezC1opdbt";
        private static final int SUCCESS = 0;
        private static final int FAILURE = SUCCESS + 1;

        private final List<Tweet> tweets = new ArrayList<>();

        private final Random random;
        private final RandomAnimationFactory animationFactory;

        private ProgressDialog dialog;

        public SearchOnTwitter(Random random, RandomAnimationFactory animationFactory) {
            this.random = random;
            this.animationFactory = animationFactory;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(activity, "", "Searching #DroidconUK15");
        }

        @Override
        protected Integer doInBackground(String... params) {
            try {
                Twitter twitter = getTwitter();
                QueryResult result = search(twitter, params[0]);
                List<twitter4j.Status> tweets = result.getTweets();
                if (tweets == null || tweets.isEmpty()) {
                    return FAILURE;
                }
                parse(tweets);
                return SUCCESS;
            } catch (Exception e) {
                return FAILURE;
            }
        }

        private Twitter getTwitter() throws TwitterException {
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setApplicationOnlyAuthEnabled(true);
            builder.setOAuthConsumerKey(TWIT_CONS_KEY);
            builder.setOAuthConsumerSecret(TWIT_CONS_SEC_KEY);
            Twitter twitter = new TwitterFactory(builder.build()).getInstance();
            OAuth2Token token = twitter.getOAuth2Token();
            builder = new ConfigurationBuilder();
            builder.setApplicationOnlyAuthEnabled(true);
            builder.setOAuthConsumerKey(TWIT_CONS_KEY);
            builder.setOAuthConsumerSecret(TWIT_CONS_SEC_KEY);
            builder.setOAuth2TokenType(token.getTokenType());
            builder.setOAuth2AccessToken(token.getAccessToken());
            return twitter;
        }

        private QueryResult search(Twitter twitter, String param) throws TwitterException {
            Query query = new Query(param);
            query.setCount(30);
            return twitter.search(query);
        }

        private void parse(List<twitter4j.Status> tweets) {
            for (twitter4j.Status tweet : tweets) {
                this.tweets.add(new Tweet(tweet.getUser().getScreenName(), tweet.getText()));
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if (result == SUCCESS) {
                Log.d("XXX", "success " + tweets);
                tweetView.setText(tweets.get(random.nextInt(tweets.size())).toString());
            } else {
                Log.d("XXX", "failure " + tweets);
                tweetView.setText(FAILURE_TEXT);
            }
            tweetView.setAnimation(animationFactory.getAnimation());
            tweetView.setVisibility(View.VISIBLE);
        }
    }

    private class Tweet {
        private final String tweetBy;
        private final String tweet;

        public Tweet(String tweetBy, String tweet) {
            this.tweetBy = tweetBy;
            this.tweet = tweet;
        }

        public String getTweetBy() {
            return tweetBy;
        }

        public String getTweet() {
            return tweet;
        }

        @Override
        public String toString() {
            return "@" + getTweetBy() + " : " + getTweet();
        }
    }

}
