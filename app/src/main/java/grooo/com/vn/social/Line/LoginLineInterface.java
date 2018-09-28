package grooo.com.vn.social.Line;

import com.linecorp.linesdk.LineProfile;

/**
 * Created by BaoNV on 22/05/2018.
 */

public interface LoginLineInterface {
    void OnLoginLineResult(boolean isSuccess, LineProfile lineProfile);
}
