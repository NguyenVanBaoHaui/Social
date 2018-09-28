package grooo.com.vn.social.Facebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by BaoNV on 21/05/2018.
 */

public class LoginFacebookManager {
    public static String TAG = LoginFacebookManager.class.getSimpleName();
    public Context mContext;
    public LoginFacebookInterface mLoginFacebookInterface;
    private CallbackManager mFacebookCallbackManager;
    private LoginManager mLoginManager;
    private AccessTokenTracker mAccessTokenTracker;

    public LoginFacebookManager(Context context) {
        this.mContext = context;
        this.mLoginFacebookInterface = (LoginFacebookInterface) this.mContext;
        this.Init();
    }

    public void Init() {
        // Init FacebookSdk.
        FacebookSdk.sdkInitialize(this.mContext.getApplicationContext());
        // Init AccessTokenTracker.
        this.mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.e(TAG, "onCurrentAccessTokenChanged");
            }
        };

        // Init LoginManager and CallbackManager.
        this.mLoginManager = LoginManager.getInstance();
        this.mFacebookCallbackManager = CallbackManager.Factory.create();

        // Register Callback LoginManager.
        LoginManager.getInstance().registerCallback(this.mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e(TAG, "LoginResult was success");
                GetFacebookUserInfo(loginResult);
            }

            @Override
            public void onCancel() {
                Log.e(TAG, "LoginResult was cancel");
                mLoginFacebookInterface.OnLoginFacebookResult(false, null);
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, "LoginResult was error");
                mLoginFacebookInterface.OnLoginFacebookResult(false, null);
            }
        });
    }

    // User GraphRequest get user info.
    private void GetFacebookUserInfo(LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object,
                                            GraphResponse response) {
                        // Callback data login.
                        Log.e(TAG, "GraphRequest get user info was success");
                        mLoginFacebookInterface.OnLoginFacebookResult(true, object);
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, name, phone, first_name, last_name, age_range, link, gender, locale, picture, timezone, updated_time, verified");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void LoginWithFacebook() {
        if (AccessToken.getCurrentAccessToken() == null) {
            if (this.mLoginManager != null && this.mAccessTokenTracker != null) {
                this.mAccessTokenTracker.startTracking();
                this.mLoginManager.logInWithReadPermissions((Activity) this.mContext, Arrays.asList("email"));
            }
        } else {
            if (this.mLoginManager != null && this.mAccessTokenTracker != null) {
                this.mLoginManager.logOut();
                this.mAccessTokenTracker.startTracking();
                this.mLoginManager.logInWithReadPermissions((Activity) this.mContext, Arrays.asList("email"));
            }
        }
    }

    public void OnFacebookActivityResult(int requestCode, int resultCode, Intent data) {
        if (this.mFacebookCallbackManager != null)
            this.mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void LogoutFacebook() {
        if (AccessToken.getCurrentAccessToken() != null && this.mLoginManager != null) {
            this.mLoginManager.logOut();
        }
    }

}
