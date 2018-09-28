package grooo.com.vn.social.Facebook;

import org.json.JSONObject;

/**
 * Created by BaoNV on 21/05/2018.
 */

public interface LoginFacebookInterface {
    void OnLoginFacebookResult(boolean isSuccess, JSONObject data);
}
