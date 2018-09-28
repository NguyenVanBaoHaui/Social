package grooo.com.vn.social.Twitter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.User;

import retrofit2.Call;

/**
 * Created by BaoNV on 22/05/2018.
 */

public class LoginTwitterManager {
    public static String TAG = LoginTwitterManager.class.getSimpleName();
    private LoginTwitterInterface mLoginTwitterInterface;
    private Context mContext;
    private String TWITTER_CONSUMER_KEY;
    private String TWITTER_CONSUMER_SECRET;
    private TwitterAuthClient mTwitterAuthClient;

    public LoginTwitterManager(Context context, String consumerKey, String consumerSecret) {
        this.mContext = context;
        this.TWITTER_CONSUMER_KEY = consumerKey;
        this.TWITTER_CONSUMER_SECRET = consumerSecret;
        this.mLoginTwitterInterface = (LoginTwitterInterface) this.mContext;
        this.Init();
    }

    protected void Init() {
        // Init config.
        TwitterConfig config = new TwitterConfig.Builder(this.mContext)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(this.TWITTER_CONSUMER_KEY, this.TWITTER_CONSUMER_SECRET))
                .debug(true)
                .build();
        Twitter.initialize(config);
        // Init twitter auth client.
        this.mTwitterAuthClient = new TwitterAuthClient();
    }

    public void OnTwitterActivityResult(int requestCode, int resultCode, Intent data) {
        if (this.mTwitterAuthClient != null)
            // Handler callback requests authorization.
            this.mTwitterAuthClient.onActivityResult(requestCode, resultCode, data);
    }

    public void LoginWithTwitter() {
        this.mTwitterAuthClient.authorize((Activity) this.mContext, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> twitterSessionResult) {
                Log.e(TAG, "Get twitter session success");
                // Get section success, call get user info.
                GetUserInfo();
            }

            @Override
            public void failure(TwitterException e) {
                Log.e(TAG, "Get twitter session failure ");
                // Callback result: get session false.
                mLoginTwitterInterface.OnLoginTwitterResult(false, null);
            }
        });
    }

    protected void GetUserInfo() {
        // Get current section.
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        // User twitter core get account service.
        Call<User> verifyRequest = TwitterCore.getInstance().getApiClient(session).getAccountService()
                .verifyCredentials(false, false, true);

        verifyRequest.enqueue(new Callback<User>() {
            @Override
            public void success(Result<User> result) {
                Log.e(TAG, "Get user info success");
                // Callback result: get user info success.
                mLoginTwitterInterface.OnLoginTwitterResult(true, result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.e(TAG, "Get user info failure");
                // Callback result: get user info failure.
                mLoginTwitterInterface.OnLoginTwitterResult(false, null);
            }
        });
    }
}
