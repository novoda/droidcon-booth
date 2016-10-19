package com.novoda.canvas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.TextView;

import com.novoda.canvas.base.NovodaActivityTest;

import java.util.Random;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class HaxActivityTest extends NovodaActivityTest {

    private ViewGroup parent;
    private TextView codeTextView;
    private String codeClass;
    private int pointer = 0;

    @Override
    public void startTestFor(Activity activity) {
        parent = getParent();
        codeTextView = new TextView(activity);
        codeTextView.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        codeTextView.setTextColor(Color.BLACK);
        codeTextView.setTypeface(Typeface.MONOSPACE);
        parent.addView(codeTextView);

        codeClass = getCodeClass(NovodaActivity.RANDOM);

        addCode();
    }

    private void addCode() {
        parent.postDelayed(addMoarCode(), 10);
    }

    private Runnable addMoarCode() {
        return new Runnable() {
            @SuppressLint("SetTextI18n") // I don't play by the roolz
            @Override
            public void run() {
                int amount = NovodaActivity.RANDOM.nextInt(5);

                if (pointer + amount > codeClass.length()) {
                    return;
                }

                codeTextView.setText(codeTextView.getText() + codeClass.substring(pointer, pointer += amount));

                addCode();
            }
        };
    }

    private static String getCodeClass(Random random) {
        switch (random.nextInt(6)) {
            case 0:
                return CODE;
            case 1:
                return CODE_2;
            case 2:
                return CODE_3;
            case 4:
                return CODE_4;
            case 5:
            default:
                return CODE_5;
        }
    }

    private static final String CODE =
            "public class LogoActivityTest extends NovodaActivityTest {\n" +
                    "\n" +
                    "    private static final int DURATION_MILLIS = 1000;\n" +
                    "    private static final float FROM_X = 0.8f;\n" +
                    "    private static final float TO_X = 1f;\n" +
                    "    private static final float FROM_Y = 0.8f;\n" +
                    "    private static final float TO_Y = 1f;\n" +
                    "    private static final float PIVOT_X = 0.5f;\n" +
                    "    private static final float PIVOT_Y = 0.5f;\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public void startTestFor(Activity activity) {\n" +
                    "        ViewGroup parent = getParent();\n" +
                    "        ImageView logo = createNovodaLogo(parent);\n" +
                    "\n" +
                    "        Animation pulse = createPulseAnimation();\n" +
                    "        logo.startAnimation(pulse);\n" +
                    "    }\n" +
                    "\n" +
                    "    private ImageView createNovodaLogo(ViewGroup parent) {\n" +
                    "        ImageView logo = new ImageView(parent.getContext());\n" +
                    "        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);\n" +
                    "        params.gravity = Gravity.CENTER;\n" +
                    "        logo.setLayoutParams(params);\n" +
                    "        logo.setScaleType(ImageView.ScaleType.CENTER);\n" +
                    "        logo.setImageResource(R.drawable.novoda_logo);\n" +
                    "        parent.addView(logo);\n" +
                    "        return logo;\n" +
                    "    }\n" +
                    "\n" +
                    "    private Animation createPulseAnimation() {\n" +
                    "        Animation pulse = new ScaleAnimation(\n" +
                    "                FROM_X, TO_X,\n" +
                    "                FROM_Y, TO_Y,\n" +
                    "                Animation.RELATIVE_TO_SELF, PIVOT_X,\n" +
                    "                Animation.RELATIVE_TO_SELF, PIVOT_Y\n" +
                    "        );\n" +
                    "        pulse.setDuration(DURATION_MILLIS);\n" +
                    "        pulse.setRepeatCount(ObjectAnimator.INFINITE);\n" +
                    "        pulse.setRepeatMode(ObjectAnimator.REVERSE);\n" +
                    "        pulse.setInterpolator(new OvershootInterpolator());\n" +
                    "        return pulse;\n" +
                    "    }\n" +
                    "}";

    private static final String CODE_2 =
            "public class DroidconTwitterSearchActivityTest extends NovodaActivityTest {\n" +
                    "    private static final String FAILURE_TEXT = \"Tweet to #DroidconUK15 and it won't be shown here...because I've thrown an exception.\";\n" +
                    "\n" +
                    "    private Activity activity;\n" +
                    "    private TextView tweetView;\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public void startTestFor(Activity activity) {\n" +
                    "        this.activity = activity;\n" +
                    "\n" +
                    "        tweetView = new TextView(activity);\n" +
                    "        tweetView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);\n" +
                    "        tweetView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));\n" +
                    "        tweetView.setVisibility(View.GONE);\n" +
                    "        tweetView.setGravity(Gravity.CENTER);\n" +
                    "        RandomColorFactory randomColorFactory = new RandomColorFactory(RANDOM);\n" +
                    "        tweetView.setTextColor(randomColorFactory.getColor());\n" +
                    "        getParent().addView(tweetView);\n" +
                    "\n" +
                    "        RandomAnimationFactory randomAnimationFactory = new RandomAnimationFactory(activity.getApplicationContext(), RANDOM);\n" +
                    "        new SearchOnTwitter(RANDOM, randomAnimationFactory).execute(\"%23droidconuk15\");\n" +
                    "    }\n" +
                    "\n" +
                    "    private class SearchOnTwitter extends AsyncTask<String, Void, Integer> {\n" +
                    "\n" +
                    "        private static final String TWIT_CONS_KEY = \"af5LHUx0kN0IeUE3yescOYRdE\"; // Made just for this event, will be deleted 1/11/2015\n" +
                    "        private static final String TWIT_CONS_SEC_KEY = \"RRnX5uFbfN87eQJM7BtfkLcFgbjY4eNa5atm4j1p5ezC1opdbt\";\n" +
                    "        private static final int SUCCESS = 0;\n" +
                    "        private static final int FAILURE = SUCCESS + 1;\n" +
                    "\n" +
                    "        private final List<Tweet> tweets = new ArrayList<>();\n" +
                    "\n" +
                    "        private final Random random;\n" +
                    "        private final RandomAnimationFactory animationFactory;\n" +
                    "\n" +
                    "        private ProgressDialog dialog;\n" +
                    "\n" +
                    "        public SearchOnTwitter(Random random, RandomAnimationFactory animationFactory) {\n" +
                    "            this.random = random;\n" +
                    "            this.animationFactory = animationFactory;\n" +
                    "        }\n" +
                    "\n" +
                    "        @Override\n" +
                    "        protected void onPreExecute() {\n" +
                    "            super.onPreExecute();\n" +
                    "            dialog = ProgressDialog.show(activity, \"\", \"Searching #DroidconUK15\");\n" +
                    "        }\n" +
                    "\n" +
                    "        @Override\n" +
                    "        protected Integer doInBackground(String... params) {\n" +
                    "            try {\n" +
                    "                Twitter twitter = getTwitter();\n" +
                    "                QueryResult result = search(twitter, params[0]);\n" +
                    "                List<twitter4j.Status> tweets = result.getTweets();\n" +
                    "                if (tweets == null || tweets.isEmpty()) {\n" +
                    "                    return FAILURE;\n" +
                    "                }\n" +
                    "                parse(tweets);\n" +
                    "                return SUCCESS;\n" +
                    "            } catch (Exception e) {\n" +
                    "                return FAILURE;\n" +
                    "            }\n" +
                    "        }\n" +
                    "\n" +
                    "        private Twitter getTwitter() throws TwitterException {\n" +
                    "            ConfigurationBuilder builder = new ConfigurationBuilder();\n" +
                    "            builder.setApplicationOnlyAuthEnabled(true);\n" +
                    "            builder.setOAuthConsumerKey(TWIT_CONS_KEY);\n" +
                    "            builder.setOAuthConsumerSecret(TWIT_CONS_SEC_KEY);\n" +
                    "            Twitter twitter = new TwitterFactory(builder.build()).getInstance();\n" +
                    "            OAuth2Token token = twitter.getOAuth2Token();\n" +
                    "            builder = new ConfigurationBuilder();\n" +
                    "            builder.setApplicationOnlyAuthEnabled(true);\n" +
                    "            builder.setOAuthConsumerKey(TWIT_CONS_KEY);\n" +
                    "            builder.setOAuthConsumerSecret(TWIT_CONS_SEC_KEY);\n" +
                    "            builder.setOAuth2TokenType(token.getTokenType());\n" +
                    "            builder.setOAuth2AccessToken(token.getAccessToken());\n" +
                    "            return twitter;\n" +
                    "        }\n" +
                    "\n" +
                    "        private QueryResult search(Twitter twitter, String param) throws TwitterException {\n" +
                    "            Query query = new Query(param);\n" +
                    "            query.setCount(30);\n" +
                    "            return twitter.search(query);\n" +
                    "        }\n" +
                    "\n" +
                    "        private void parse(List<twitter4j.Status> tweets) {\n" +
                    "            for (twitter4j.Status tweet : tweets) {\n" +
                    "                this.tweets.add(new Tweet(tweet.getUser().getScreenName(), tweet.getText()));\n" +
                    "            }\n" +
                    "        }\n" +
                    "\n" +
                    "        @Override\n" +
                    "        protected void onPostExecute(Integer result) {\n" +
                    "            super.onPostExecute(result);\n" +
                    "            dialog.dismiss();\n" +
                    "            if (result == SUCCESS) {\n" +
                    "                Log.d(\"XXX\", \"success \" + tweets);\n" +
                    "                tweetView.setText(tweets.get(random.nextInt(tweets.size())).toString());\n" +
                    "            } else {\n" +
                    "                Log.d(\"XXX\", \"failure \" + tweets);\n" +
                    "                tweetView.setText(FAILURE_TEXT);\n" +
                    "            }\n" +
                    "            tweetView.setAnimation(animationFactory.getAnimation());\n" +
                    "            tweetView.setVisibility(View.VISIBLE);\n" +
                    "        }\n" +
                    "    }\n" +
                    "\n" +
                    "    private class Tweet {\n" +
                    "        private final String tweetBy;\n" +
                    "        private final String tweet;\n" +
                    "\n" +
                    "        public Tweet(String tweetBy, String tweet) {\n" +
                    "            this.tweetBy = tweetBy;\n" +
                    "            this.tweet = tweet;\n" +
                    "        }\n" +
                    "\n" +
                    "        public String getTweetBy() {\n" +
                    "            return tweetBy;\n" +
                    "        }\n" +
                    "\n" +
                    "        public String getTweet() {\n" +
                    "            return tweet;\n" +
                    "        }\n" +
                    "\n" +
                    "        @Override\n" +
                    "        public String toString() {\n" +
                    "            return \"@\" + getTweetBy() + \" : \" + getTweet();\n" +
                    "        }\n" +
                    "    }";

    private static final String CODE_3 =
            "public class MinesweeperTest extends NovodaActivityTest {\n" +
                    "\n" +
                    "    public static final int DELAY_MILLIS_BETWEEN_MOVES = 400;\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public void startTestFor(Activity activity) {\n" +
                    "        final ViewGroup root = getParent();\n" +
                    "        View view = LayoutInflater.from(activity).inflate(R.layout.main, root, true);\n" +
                    "\n" +
                    "        BoardView boardView = (BoardView) view.findViewById(R.id.board);\n" +
                    "\n" +
                    "        final IGame game = GameFactory.create(8, 8, 8);\n" +
                    "        BoardController controller = new BoardController(game, boardView);\n" +
                    "        GameTimer gameTimer = new GameTimer(game);\n" +
                    "        BombsController bombsController = new BombsController(game, (CounterView) view.findViewById(R.id.count));\n" +
                    "        TimerController timerController = new TimerController(game, (CounterView) view.findViewById(R.id.time));\n" +
                    "\n" +
                    "        final AutoPlayer autoPlayer = new AutoPlayer(game, true);\n" +
                    "\n" +
                    "        final Handler handler = new Handler();\n" +
                    "        handler.postDelayed(new Runnable() {\n" +
                    "            @Override\n" +
                    "            public void run() {\n" +
                    "                if (!game.isRunning() && game.isStarted())\n" +
                    "                    return;\n" +
                    "                autoPlayer.doNextMove();\n" +
                    "                handler.postDelayed(this, DELAY_MILLIS_BETWEEN_MOVES);\n" +
                    "            }\n" +
                    "        }, DELAY_MILLIS_BETWEEN_MOVES);\n" +
                    "\n" +
                    "    }\n" +
                    "}";

    private static final String CODE_4 =
            "public class QrCodeActivityTest extends NovodaActivityTest {\n" +
                    "\n" +
                    "    private static final String[] listOfUrls = {\n" +
                    "            \"https://novoda.com\",\n" +
                    "            \"http://droidcon.co.uk\",\n" +
                    "            \"http://sd.keepcalm-o-matic.co.uk/i/keep-calm-and-submit-a-pr-7.png\",\n" +
                    "            \"https://www.youtube.com/watch?v=dQw4w9WgXcQ\"\n" +
                    "    };\n" +
                    "    private static final int[] listOfColors = {\n" +
                    "        Color.BLACK, Color.GREEN, Color.argb(255,17,63,84), Color.DKGRAY\n" +
                    "    };\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public void startTestFor(Activity activity) {\n" +
                    "        ViewGroup view = getParent();\n" +
                    "        ImageView qrCodeImageView = new ImageView(activity);\n" +
                    "        view.addView(qrCodeImageView);\n" +
                    "\n" +
                    "        int randomIndex = RANDOM.nextInt(listOfUrls.length);\n" +
                    "        String url = listOfUrls[randomIndex];\n" +
                    "        int color = listOfColors[randomIndex];\n" +
                    "        int qrCodeSizeInPixels = 1000;\n" +
                    "\n" +
                    "        try {\n" +
                    "            BitMatrix qrCode = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, qrCodeSizeInPixels, qrCodeSizeInPixels);\n" +
                    "            int width = qrCode.getWidth();\n" +
                    "            int height = qrCode.getHeight();\n" +
                    "            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);\n" +
                    "            for (int x = 0; x < width; x++) {\n" +
                    "                for (int y = 0; y < height; y++) {\n" +
                    "                    bmp.setPixel(x, y, qrCode.get(x, y) ? color : Color.WHITE);\n" +
                    "                }\n" +
                    "            }\n" +
                    "            qrCodeImageView.setImageBitmap(bmp);\n" +
                    "        } catch (WriterException e) {\n" +
                    "           assertThat(e).isNull();\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";

    private static final String CODE_5 =
            "public class AnimatedVectorActivityTest extends NovodaActivityTest {\n" +
                    "\n" +
                    "    enum Theme {\n" +
                    "        BLUE(R.drawable.vector_animated_novoda_blue_logo, R.color.vector_white),\n" +
                    "        WHITE(R.drawable.vector_animated_novoda_white_logo, R.color.vector_blue);\n" +
                    "\n" +
                    "        private final int vectorDrawableRes;\n" +
                    "        private final int backgroundColorRes;\n" +
                    "\n" +
                    "        Theme(@DrawableRes int vectorDrawableRes, @ColorRes int backgroundColorRes) {\n" +
                    "            this.vectorDrawableRes = vectorDrawableRes;\n" +
                    "            this.backgroundColorRes = backgroundColorRes;\n" +
                    "        }\n" +
                    "    }\n" +
                    "\n" +
                    "    public static final Handler MAIN_THREAD = new Handler(Looper.getMainLooper());\n" +
                    "    public static final int INITIAL_DELAY_MILLIS = 2000;\n" +
                    "    public static final int EXPLOSION_DELAY_MILLIS = 8500;\n" +
                    "\n" +
                    "    private ExplosionField explosionField;\n" +
                    "    private ImageView imageView;\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public void startTestFor(Activity activity) {\n" +
                    "        Theme theme = NovodaActivity.RANDOM.nextBoolean() ? Theme.BLUE : Theme.WHITE;\n" +
                    "\n" +
                    "        setBackground(activity, theme);\n" +
                    "\n" +
                    "        imageView = createImageView(activity, theme);\n" +
                    "        getParent().addView(imageView);\n" +
                    "\n" +
                    "        explosionField = ExplosionField.attach2Window(activity);\n" +
                    "\n" +
                    "        delayInitialAnimation();\n" +
                    "        delayExplosion();\n" +
                    "    }\n" +
                    "\n" +
                    "    private void setBackground(Activity activity, Theme theme) {\n" +
                    "        @ColorInt int color = activity.getResources().getColor(theme.backgroundColorRes);\n" +
                    "        ColorDrawable background = new ColorDrawable(color);\n" +
                    "        getParent().setBackground(background);\n" +
                    "    }\n" +
                    "\n" +
                    "    private ImageView createImageView(Context context, Theme theme) {\n" +
                    "        ImageView imageView = new ImageView(context);\n" +
                    "\n" +
                    "        int padding = context.getResources().getDimensionPixelSize(R.dimen.image_padding);\n" +
                    "        imageView.setPadding(padding, padding, padding, padding);\n" +
                    "        imageView.setScaleType(CENTER_INSIDE);\n" +
                    "\n" +
                    "        AnimatedVectorDrawableCompat vectorDrawable = AnimatedVectorDrawableCompat.create(context, theme.vectorDrawableRes);\n" +
                    "        imageView.setImageDrawable(vectorDrawable);\n" +
                    "        return imageView;\n" +
                    "    }\n" +
                    "\n" +
                    "    private void delayInitialAnimation() {\n" +
                    "        MAIN_THREAD.postDelayed(\n" +
                    "                new Runnable() {\n" +
                    "                    @Override\n" +
                    "                    public void run() {\n" +
                    "                        Drawable drawable = imageView.getDrawable();\n" +
                    "                        ((Animatable) drawable).start();\n" +
                    "                    }\n" +
                    "                }, INITIAL_DELAY_MILLIS\n" +
                    "        );\n" +
                    "    }\n" +
                    "\n" +
                    "    private void delayExplosion() {\n" +
                    "        MAIN_THREAD.postDelayed(\n" +
                    "                new Runnable() {\n" +
                    "                    @Override\n" +
                    "                    public void run() {\n" +
                    "                        explosionField.explode(imageView);\n" +
                    "                    }\n" +
                    "                }, EXPLOSION_DELAY_MILLIS\n" +
                    "        );\n" +
                    "    }\n" +
                    "\n" +
                    "}";
}
