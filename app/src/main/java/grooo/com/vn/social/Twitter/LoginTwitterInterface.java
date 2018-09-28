package grooo.com.vn.social.Twitter;

import com.twitter.sdk.android.core.models.User;

/**
 * Created by BaoNV on 22/05/2018.
 */

public interface LoginTwitterInterface {
    void OnLoginTwitterResult(boolean isSuccess, User data);
}
