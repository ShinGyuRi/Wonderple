package kr.co.easterbunny.wonderple.activity;

import android.content.Intent;
import android.os.Bundle;

import kr.co.easterbunny.wonderple.R;
import kr.co.easterbunny.wonderple.library.ParentActivity;
import kr.co.easterbunny.wonderple.library.gcm.services.RegistrationIntentService;

public class SplashActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //Receive Token
        startService(new Intent(this, RegistrationIntentService.class));
    }
}
