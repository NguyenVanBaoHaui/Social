package grooo.com.vn.social.Google;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by BaoNV on 22/05/2018.
 */

public class LoginGoogleManager {
    public static String TAG = LoginGoogleManager.class.getSimpleName();
    private Activity mContext;
    private GoogleSignInClient mGoogleSignInClient;
    private LoginGoogleInterface mLoginGoogleInterface;

    public LoginGoogleManager(Activity context) {
        this.mContext = context;
        this.mLoginGoogleInterface = (LoginGoogleInterface) this.mContext;
        this.Init();
    }

    private void Init() {
        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [START build_client]
        // Build a GoogleSignInClient with the options specified by gso.
        this.mGoogleSignInClient = GoogleSignIn.getClient(this.mContext, gso);
    }

    // [START logout]
    public void LogOutGoogle() {
        if (this.mGoogleSignInClient != null) {
            this.mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this.mContext, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.e(TAG, "Logout success");
                        }
                    });
        }
    }

    public void OnGoogleActivityResult(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            Log.e(TAG, "Get user account success");
            GoogleSignInAccount account = task.getResult(ApiException.class);
            this.mLoginGoogleInterface.OnLoginGoogleResult(true, account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e(TAG, "ApiException: " + e.toString());
            this.mLoginGoogleInterface.OnLoginGoogleResult(false, null);
        }
    }

    public void LoginWithGoogle(int requestCode) {
        if (this.mGoogleSignInClient != null) {
            Intent signInIntent = this.mGoogleSignInClient.getSignInIntent();
            this.mContext.startActivityForResult(signInIntent, requestCode);
        }
    }
}
