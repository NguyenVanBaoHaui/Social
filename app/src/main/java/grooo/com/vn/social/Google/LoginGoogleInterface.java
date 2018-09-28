package grooo.com.vn.social.Google;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by BaoNV on 22/05/2018.
 */

public interface LoginGoogleInterface {
    void OnLoginGoogleResult(boolean isSuccess, GoogleSignInAccount data);
}
