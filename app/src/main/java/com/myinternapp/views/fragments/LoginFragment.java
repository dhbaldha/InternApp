package com.myinternapp.views.fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.myinternapp.InternApplication;
import com.myinternapp.R;
import com.myinternapp.interfaces.ToolBarButtonClickListener;
import com.myinternapp.networking.response.ResponseMovies;
import com.myinternapp.utils.BaseFragment;
import com.myinternapp.utils.SharedPrefUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


public class LoginFragment extends BaseFragment implements ToolBarButtonClickListener, View.OnClickListener {

    @BindView(R.id.edEmailID)
    EditText edEmail;

    @BindView(R.id.edPassword)
    EditText edPassword;

    @BindView(R.id.btnLogIn)
    Button btnLogin;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private String[] DUMMYCREDENTIALS = new String[]{"dhbaldha@gmail.com:testpassword"};

    @BindView(R.id.login_button)
    LoginButton loginButton;

    //FB Callback Manager
    private CallbackManager callbackManager;

    public static String FB_RESPONSE = "fb_responce";



    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        btnLogin.setOnClickListener(this);
        setTitleView(true, false, "From AppApi", null, View.VISIBLE, this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkLoginStatus();

    }

    private boolean checkLoginStatus() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.isExpired()) {
            replaceMovieListFragment();
            return true;
        } else if (SharedPrefUtils.getSharedPref(getActivity()).getCredentials(SharedPrefUtils.CREDENTIALS) != null) {
            replaceMovieListFragment();
            return true;
        }

        loginToFacebook();

        return false;
    }

    private void replaceMovieListFragment() {
        replaceFragment(R.id.container, MovieListFragment.newInstance(null), true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogIn:
                checkPermmissionForContact();
        }
    }

    void attemptLogin(){

        progressBar.setVisibility(View.VISIBLE);

        View focusView = null;
        boolean cancel = false;

        edEmail.setError(null);
        edPassword.setError(null);

        String email = edEmail.getText().toString();
        String password = edPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            edEmail.setError("Email field is required.");
            focusView = edEmail;
            cancel = true;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edEmail.setError("Please enter valid email id.");
            focusView = edEmail;
            cancel = true;
        }

        if(TextUtils.isEmpty(password)){
            edPassword.setError("Password field is required.");
            focusView = edPassword;
            cancel = true;
        } else if(password.length()<4){
            edPassword.setError("Please enter valid password.");
            focusView = edPassword;
            cancel = true;
        }

        if(cancel) {
            focusView.requestFocus();
        }else {

            saveCredentials(email, password);

            fetchMovieListData();
        }
    }

    private void saveCredentials(String email, String password) {
        SharedPrefUtils.getSharedPref(getActivity()).saveCredentials(SharedPrefUtils.CREDENTIALS, email, password);
    }

    @Override
    public void onToolbarButtonClickListener() {
        Toast.makeText(getActivity(), "Login Toolbar Icon Clicked.", Toast.LENGTH_SHORT).show();
    }


    @TargetApi(Build.VERSION_CODES.M)
    boolean checkPermmissionForContact(){

        if(getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS) == PERMISSION_GRANTED){
            attemptLogin();
            return true;
        }

        if(shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)){
            Snackbar.make(edEmail, "Contact permission require for further process.", BaseTransientBottomBar.LENGTH_INDEFINITE)
                    .setAction("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
                        }
                    }).show();
        }else {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults[0] == PERMISSION_GRANTED){
                    attemptLogin();
                }
        }
    }

    private void loginToFacebook(){

        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        // If you are using in a fragment, call loginButton.setFragment(this);

        loginButton.setFragment(this);

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender, birthday, friends, picture");

                        GraphRequest graphRequest = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {

                                        Log.v("LoginActivity Response ", response.toString());

                                            Bundle data = new Bundle();
                                            data.putString(FB_RESPONSE, object.toString());

                                            fetchMovieListData();

                                    }
                                });

                        graphRequest.setParameters(parameters);
                        graphRequest.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getActivity(), "AppApi Cancel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getActivity(), "AppApi Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchMovieListData() {
        progressBar.setVisibility(View.VISIBLE);

        InternApplication.getApp().getNetworkSetup().setupLogin().getGitHubData().enqueue(new Callback<ArrayList<ResponseMovies>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseMovies>> call, Response<ArrayList<ResponseMovies>> response) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("RESPONSEDATA", response.body() );

                replaceFragment(R.id.container,MovieListFragment.newInstance(bundle), true);

                progressBar.setVisibility(View.GONE);

                showSnackBar(getView()!=null ? getView() : progressBar, R.string.login_successfully);
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseMovies>> call, Throwable t) {

                showSnackBar(progressBar, R.string.something_problamatic);

                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
