package grooo.com.vn.social.Linkedln;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by BaoNV on 23/05/2018.
 */

public class LinkedlnUserInfoObject {

    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("headline")
    @Expose
    private String headline;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("siteStandardProfileRequest")
    @Expose
    private SiteStandardProfileRequestObject siteStandardProfileRequest;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public SiteStandardProfileRequestObject getSiteStandardProfileRequest() {
        return siteStandardProfileRequest;
    }

    public void setSiteStandardProfileRequest(SiteStandardProfileRequestObject siteStandardProfileRequest) {
        this.siteStandardProfileRequest = siteStandardProfileRequest;
    }
}
