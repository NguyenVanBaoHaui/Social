package grooo.com.vn.social.Linkedln;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by BaoNV on 23/05/2018.
 */

public class SiteStandardProfileRequestObject {
    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
