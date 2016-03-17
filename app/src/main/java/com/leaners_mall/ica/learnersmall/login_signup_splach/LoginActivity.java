package com.leaners_mall.ica.learnersmall.login_signup_splach;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.leaners_mall.ica.learnersmall.R;
import com.leaners_mall.ica.learnersmall.navigation_drawer.MainActivity;
import com.leaners_mall.ica.learnersmall.utils.ConnectionCheck;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by prithaviraj on 3/10/2016.
 */
public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    EditText et_input_email,et_input_password;
    Button btn_login;
    ConnectionCheck _connectionCheck;

    ProgressDialog progressDialog;
    GoogleApiClient googleapiclient;
    GoogleSignInAccount acc;

    private static final int RC_SIGN_IN = 9001;
    private static final String LOGIN_URL = "http://lmsapi.learnersmall.com/api/AccountApi/Login";
    SharedPreferences.Editor editor, editor1;
    SharedPreferences pref;
    String flag = "", login_flag = "0", remember_flag = "0", name = "";
    String inputMail,inputPass,status;
    Intent goToMain;

    /*FACEBOOK*/
    CallbackManager callbackManager;
    LinearLayout llLoginActivityFB,llLoginActivityGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        googleapiclient = new GoogleApiClient.Builder(this).enableAutoManage(this /* FragmentActivity */,this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);

        initilize();
        onClick();

    }

    private void initilize() {

        et_input_email = (EditText)findViewById(R.id.et_input_email);
        et_input_password = (EditText)findViewById(R.id.et_input_password);
        btn_login = (Button)findViewById(R.id.btn_login);
        _connectionCheck = new ConnectionCheck(LoginActivity.this);
        llLoginActivityGoogle=(LinearLayout)findViewById(R.id.llLoginActivityGoogle);
        llLoginActivityFB = (LinearLayout)findViewById(R.id.llLoginActivityFB);
        goToMain=new Intent(getApplicationContext(),MainActivity.class);
    }

    private void onClick() {

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMail = et_input_email.getText().toString();
                Log.e("inputMail","inputMail");
                inputPass = et_input_password.getText().toString();
                Log.e("inputPass", "inpu.tPass");

                if (inputMail.equals("") && inputPass.equals("")){
                    Toast.makeText(getApplicationContext(), "Please enter a valid email id or password", Toast.LENGTH_LONG).show();
                }else {
                    executionLogin();
                    if (status.equals("T")){
                        Log.e("Login SUCCESS","Login SUCCESS");
                    }else if (status.equals("F")){
                        Log.e("Login FAIL","Login FAIL Registration need");

                    }
                }

            }
        });

        llLoginActivityFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onFblogin();
                executionLogin();
                if (status.equals("T")){
                    Log.e("Login SUCCESS","Login SUCCESS");
                }else if (status.equals("F")){
                    Log.e("Login FAIL","Login FAIL Registration need");

                }

            }
        });

        llLoginActivityGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onGooglelogin();
                executionLogin();
                if (status.equals("T")){
                    Log.e("Login SUCCESS","Login SUCCESS");
                    startActivity(goToMain);
                }else if (status.equals("F")){
                    Log.e("Login FAIL","Login FAIL Registration need");

                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    public void executionLogin(){

        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("UserName", inputMail));
        parameters.add(new BasicNameValuePair("Password", inputPass));
        parameters.add(new BasicNameValuePair("ProjectId ", "1"));
        Log.e("PARAM", parameters.toString());
        ExecuteRegistration executeRegistration=new ExecuteRegistration();
        executeRegistration.URL=LOGIN_URL;
        executeRegistration.params1=parameters;
        JSONObject returnedJsonObject=null;

        try {
            returnedJsonObject = executeRegistration.execute().get();
            Log.e("returned json ", returnedJsonObject.toString());
            status=returnedJsonObject.getString("Status");

        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        } catch (JSONException e1) {
            e1.printStackTrace();
        }


    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    /******ASYNCTASK FOR NORMAL LOGIN******/
    /*class ExecuteLogin extends AsyncTask<String,String,String>{

        JSONObject jsonObj;

        @Override
        protected String doInBackground(String... params) {


            jsonObj = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params1);
            Log.e("URL", jsonObj.toString());

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                if (jsonObj.getString("Status").equals("T")){
                    Log.e("Login SUCCESS","Login SUCCESS");
                }else if (jsonObj.getString("Status").equals("F")){
                    Log.e("Login FAIL","Login FAIL Registration need");

                }
                else{}
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }*/

                                                        /*FOR FACE BOOK*/
private void onFblogin()
{
    callbackManager = CallbackManager.Factory.create();
    LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, (Arrays.asList("public_profile", "user_friends", "user_birthday", "user_about_me", "email")));

    LoginManager.getInstance().registerCallback(callbackManager,new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.d("tag","FF fb onSuccess");

            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object,GraphResponse response) {
                    String email,id;
                    try {
                        String[] splited ;
                        JSONObject obj =  object.getJSONObject("picture").getJSONObject("data");

                        if (object.has("email"))
                        {
                            email =  object.getString("email");
                            Log.e("Email",email);
                        }
                        else
                        {
                            email = "";
                        }
                        id = object.getString("id");
                        Log.e("Facebook Id",id);


                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link,birthday,picture,email,gender");
            request.setParameters(parameters);
            request.executeAsync();


        }

        @Override
        public void onCancel() {
            Log.d("tag","fb onCancel");
            // App code
        }



        @Override
        public void onError(FacebookException exception) {
            Log.d("tag","fb onError");
            // App code
        }
    });
    }
    private void onGooglelogin()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleapiclient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if(result.isSuccess()){
            acc= result.getSignInAccount();
            inputMail=acc.getEmail();

            ///--------------------here we are sending google id as password in login API--------------------------///
            inputPass=acc.getId();

            Log.e("email",inputMail);
            Log.e("pass",inputPass);
        }

        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}
