package grooo.com.vn.social.Linkedln;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

/**
 * Created by BaoNV on 23/05/2018.
 */

public class LoginLinkedlnManager {
    public static String TAG = LoginLinkedlnManager.class.getSimpleName();
    private static String url = "https://api.linkedin.com/v1/people/~?format=json";
    private LoginLinkedlnInterface mLoginLinkedlnInterface;
    private Activity mContext;

    public LoginLinkedlnManager(Activity context) {
        this.mContext = context;
        this.mLoginLinkedlnInterface = (LoginLinkedlnInterface) this.mContext;
    }

    public void LoginWithLinkedln() {
        LISessionManager.getInstance(this.mContext.getApplicationContext()).init(this.mContext, BuildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {
                // Authentication was successful.  You can now do
                // other calls with the SDK.
                Log.e(TAG, "Authentication was successful");
                GetBaseProfile();
            }

            @Override
            public void onAuthError(LIAuthError error) {
                // Handle authentication errors
                Log.e(TAG, "Authentication was errors");
                mLoginLinkedlnInterface.OnLoginLineResult(false, null);
            }
        }, true);
    }

    public void OnLinkedlnActivityResult(int requestCode, int resultCode, Intent data) {
        LISessionManager.getInstance(this.mContext.getApplicationContext()).onActivityResult(this.mContext, requestCode, resultCode, data);
    }

    // Build the list of member permissions our LinkedIn session requires
    private static Scope BuildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE);
    }

    // REST API call retrieve basic profile data for the user.
    private void GetBaseProfile() {
        APIHelper apiHelper = APIHelper.getInstance(this.mContext.getApplicationContext());
        apiHelper.getRequest(this.mContext, url, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse apiResponse) {
                // Success!
                Log.e(TAG, "Get user info was success");
                if (apiResponse.getStatusCode() == 200 && apiResponse.getResponseDataAsJson() != null) {
                    Gson gson = new Gson();
                    LinkedlnUserInfoObject response = gson.fromJson(String.valueOf(apiResponse.getResponseDataAsJson()), LinkedlnUserInfoObject.class);
                    mLoginLinkedlnInterface.OnLoginLineResult(true, response);
                } else {
                    mLoginLinkedlnInterface.OnLoginLineResult(false, null);
                }
            }

            @Override
            public void onApiError(LIApiError liApiError) {
                // Error making GET request!
                Log.e(TAG, "Get user info was error");
                mLoginLinkedlnInterface.OnLoginLineResult(false, null);
            }
        });
    }

}
