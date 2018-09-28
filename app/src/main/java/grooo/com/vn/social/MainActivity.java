package grooo.com.vn.social;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.linecorp.linesdk.LineProfile;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONObject;

import grooo.com.vn.social.Facebook.LoginFacebookInterface;
import grooo.com.vn.social.Facebook.LoginFacebookManager;
import grooo.com.vn.social.Google.LoginGoogleInterface;
import grooo.com.vn.social.Google.LoginGoogleManager;
import grooo.com.vn.social.Line.LoginLineInterface;
import grooo.com.vn.social.Line.LoginLineManager;
import grooo.com.vn.social.Linkedln.LinkedlnUserInfoObject;
import grooo.com.vn.social.Linkedln.LoginLinkedlnInterface;
import grooo.com.vn.social.Linkedln.LoginLinkedlnManager;
import grooo.com.vn.social.Twitter.LoginTwitterInterface;
import grooo.com.vn.social.Twitter.LoginTwitterManager;

public class MainActivity extends Activity implements View.OnClickListener, LoginFacebookInterface, LoginTwitterInterface, LoginGoogleInterface, LoginLineInterface, LoginLinkedlnInterface {

    private Button btnFacebook;
    private Button btnTwitter;
    private Button btnGoogle;
    private Button btnLine;
    private Button btnLinkedln;
    private LoginFacebookManager mFacebookManager;
    private LoginTwitterManager mLoginTwitterManager;
    private LoginGoogleManager mGoogleManager;
    private LoginLineManager mLoginLineManager;
    private LoginLinkedlnManager mLoginLinkedlnManager;


    private static String TWITTER_CONSUMER_KEY = "SLj230YJZlrqfss9H4Tnk3043";
    private static String TWITTER_CONSUMER_SECRET = "Ait6UA9Z1410v8by4trZn4zkIOdqlHRe03Il6of7laVV5hh7Zy";
    private static String LINE_CHANGE_ID = "1582609406";

    private static int CODE_REQUEST_GOOGLE = 1;
    private static int CODE_REQUEST_LINE = 2;

    public enum TypeLogin {Facebook, Google, Twitter, Line, Linkedln}

    public TypeLogin TypeLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Add StrictMode. (twitter request)
        if (android.os.Build.VERSION.SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        this.btnFacebook = findViewById(R.id.btnFacebook);
        this.btnFacebook.setOnClickListener(this);
        this.btnTwitter = findViewById(R.id.btnTwitter);
        this.btnTwitter.setOnClickListener(this);
        this.btnGoogle = findViewById(R.id.btnGoogle);
        this.btnGoogle.setOnClickListener(this);
        this.btnLine = findViewById(R.id.btnLine);
        this.btnLine.setOnClickListener(this);
        this.btnLinkedln = findViewById(R.id.btnLinkedln);
        this.btnLinkedln.setOnClickListener(this);

        this.mFacebookManager = new LoginFacebookManager(this);
        this.mLoginTwitterManager = new LoginTwitterManager(this, this.TWITTER_CONSUMER_KEY, this.TWITTER_CONSUMER_SECRET);
        this.mGoogleManager = new LoginGoogleManager(this);
        this.mLoginLineManager = new LoginLineManager(this, this.LINE_CHANGE_ID);
        this.mLoginLinkedlnManager = new LoginLinkedlnManager(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (this.TypeLogin != null && this.TypeLogin == TypeLogin.Facebook) {
            this.mFacebookManager.OnFacebookActivityResult(requestCode, resultCode, data);
        } else if (this.TypeLogin != null && this.TypeLogin == TypeLogin.Twitter) {
            this.mLoginTwitterManager.OnTwitterActivityResult(requestCode, resultCode, data);
        } else if (this.TypeLogin != null && this.TypeLogin == TypeLogin.Google) {
            this.mGoogleManager.OnGoogleActivityResult(data);
        } else if (this.TypeLogin != null && this.TypeLogin == TypeLogin.Line) {
            this.mLoginLineManager.OnLineActivityResult(data);
        } else if (this.TypeLogin != null && this.TypeLogin == TypeLogin.Linkedln) {
            this.mLoginLinkedlnManager.OnLinkedlnActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnFacebook:
                this.TypeLogin = TypeLogin.Facebook;
                this.mFacebookManager.LoginWithFacebook();
                break;
            case R.id.btnTwitter:
                this.TypeLogin = TypeLogin.Twitter;
                this.mLoginTwitterManager.LoginWithTwitter();
                break;
            case R.id.btnGoogle:
                this.TypeLogin = TypeLogin.Google;
                this.mGoogleManager.LoginWithGoogle(CODE_REQUEST_GOOGLE);
                break;
            case R.id.btnLine:
                this.TypeLogin = TypeLogin.Line;
                this.mLoginLineManager.LoginWithLine(CODE_REQUEST_LINE);
                break;
            case R.id.btnLinkedln:
                this.TypeLogin = TypeLogin.Linkedln;
                this.mLoginLinkedlnManager.LoginWithLinkedln();
                break;
            default:
                break;
        }
    }

    @Override
    public void OnLoginFacebookResult(boolean isSuccess, JSONObject data) {
        Toast.makeText(this, "Login facebook: " + isSuccess, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnLoginTwitterResult(boolean isSuccess, User data) {
        Toast.makeText(this, "Login twitter: " + isSuccess, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnLoginGoogleResult(boolean isSuccess, GoogleSignInAccount data) {
        Toast.makeText(this, "Login google: " + isSuccess, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnLoginLineResult(boolean isSuccess, LineProfile lineProfile) {
        Toast.makeText(this, "Login line: " + isSuccess, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnLoginLineResult(boolean isSuccess, LinkedlnUserInfoObject lineProfile) {
        Toast.makeText(this, "Login linkedln: " + isSuccess, Toast.LENGTH_SHORT).show();
    }
}
