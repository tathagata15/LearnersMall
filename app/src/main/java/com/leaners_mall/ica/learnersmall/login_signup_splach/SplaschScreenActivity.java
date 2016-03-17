package com.leaners_mall.ica.learnersmall.login_signup_splach;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.leaners_mall.ica.learnersmall.R;
import com.leaners_mall.ica.learnersmall.utils.ConnectionCheck;

/**
 * Created by prithaviraj on 3/10/2016.
 */
public class SplaschScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 5000;
    ConnectionCheck _connectionCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        _connectionCheck = new ConnectionCheck(SplaschScreenActivity.this);


        if (_connectionCheck.isNetworkAvailable()) {

            new Handler().postDelayed(new Runnable() {


                @Override
                public void run() {

                   /* Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
                    startActivity(i);*/
                    Intent i = new Intent(SplaschScreenActivity.this, LoginActivity.class);
                    startActivity(i);

                    finish();
                }
            }, SPLASH_TIME_OUT);

        }else {

            Toast.makeText(getApplicationContext(),"Check your internet connection",Toast.LENGTH_LONG).show();
        }

    }
}
