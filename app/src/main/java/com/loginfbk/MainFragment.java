package com.loginfbk;

/**
 * Created by Simpli5d on 14/05/16.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

    private CallbackManager callbackManager;
    //private TextView textView;

    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    private String id;
    private String imgUrl;
    private String email;
    private String birthday;
    private String name;
    private String gender;
    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.v("LoginActivity", response.toString());

                            // Application code
                            try {
                                email = object.getString("email");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                id = object.getString("id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                birthday = object.getString("birthday"); // 01/31/1980 format
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                name = object.getString("name"); // 01/31/1980 format
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                gender = object.getString("gender"); // 01/31/1980 format
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            imgUrl = "https://graph.facebook.com/" + id + "/picture?type=large";
                            openProfileInfo(email, id, birthday, name, gender, imgUrl);
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender,birthday, picture");
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {
            // App code
            Log.v("LoginActivity", "cancel");

        }

        @Override
        public void onError(FacebookException e) {
            Log.v("LoginActivity", e.getCause().toString());
        }
    };

    public MainFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                displayMessage(newProfile);
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        //textView = (TextView) view.findViewById(R.id.textView);

        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, callback);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    private void displayMessage(Profile profile){
        if(profile != null){
            Log.d("chk", profile.toString());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        displayMessage(profile);
    }

    private void openProfileInfo(String email, String id, String birthday, String name, String gender, String imgUrl){
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("email", email);
        hashMap.put("id", id);
        hashMap.put("birthday", birthday);
        hashMap.put("name", name);
        hashMap.put("gender", gender);
        hashMap.put("imgurl", imgUrl);
        Intent intent = new Intent(getActivity(), ShowProfileInformation.class);
        intent.putExtra("hashMap", hashMap);
        startActivity(intent);
    }

    private void getCoverPhoto(){
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "...?fields={fieldname_of_type_CoverPhoto}",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                      /* handle the result */
                        Log.d("chk", response.toString());
                    }
                }
        ).executeAsync();
    }
}

