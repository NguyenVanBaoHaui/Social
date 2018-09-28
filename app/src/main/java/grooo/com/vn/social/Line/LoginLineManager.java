package grooo.com.vn.social.Line;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.linecorp.linesdk.auth.LineLoginApi;
import com.linecorp.linesdk.auth.LineLoginResult;

/**
 * Created by BaoNV on 22/05/2018.
 */

public class LoginLineManager {
    public static String TAG = LoginLineManager.class.getSimpleName();
    private Activity mContext;
    private String CHANNEL_ID;
    private LoginLineInterface mLoginLineInterface;

    public LoginLineManager(Activity context, String channelID) {
        this.mContext = context;
        this.CHANNEL_ID = channelID;
        this.mLoginLineInterface = (LoginLineInterface) this.mContext;
    }

    public void LoginWithLine(int requestCode) {
        // App to App Login
        Intent LoginIntent = LineLoginApi.getLoginIntent(this.mContext, this.CHANNEL_ID);
        this.mContext.startActivityForResult(LoginIntent, requestCode);
    }

    public void OnLineActivityResult(Intent data) {
        LineLoginResult result = LineLoginApi.getLoginResultFromIntent(data);
        switch (result.getResponseCode()) {
            case SUCCESS:
                Log.e(TAG, "LineLoginResult was successful");
                this.mLoginLineInterface.OnLoginLineResult(true, result.getLineProfile());
                break;
            case CANCEL:
                Log.e(TAG, "LineLoginResult was cancel");
                this.mLoginLineInterface.OnLoginLineResult(false, null);
                break;
            default:
                Log.e(TAG, "LineLoginResult was error");
                this.mLoginLineInterface.OnLoginLineResult(false, null);
                break;
        }
    }
}
