package kr.co.easterbunny.wonderple.activity;

import android.content.Intent;
import android.os.Bundle;

import kr.co.easterbunny.wonderple.R;
import kr.co.easterbunny.wonderple.library.ParentActivity;
import kr.co.easterbunny.wonderple.library.gcm.GcmUtil;
import kr.co.easterbunny.wonderple.library.gcm.services.RegistrationIntentService;
import kr.co.easterbunny.wonderple.library.util.Definitions.PREFKEY;
import kr.co.easterbunny.wonderple.library.util.JSLog;
import kr.co.easterbunny.wonderple.library.util.PrefUtil;
import kr.co.easterbunny.wonderple.library.util.TextUtil;
import kr.co.easterbunny.wonderple.library.util.Util;

public class SplashActivity extends ParentActivity {

    private final static String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //Receive Token
        if (GcmUtil.checkPlayServices(this)) {  startService(new Intent(this, RegistrationIntentService.class));    }


        //Test
        Util.runDelay(2000, new Runnable() {
            @Override
            public void run() {
                moveLoginActivity();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    public void checkVisitInfo()    {
        boolean isVisited = PrefUtil.getInstance().getBooleanPreference(PREFKEY.IS_VISIT_EXPERIENCE_BOOL);
        if (isVisited) {

            moveLoginActivity();

        } else {

            PrefUtil.getInstance().putPreference(PREFKEY.IS_VISIT_EXPERIENCE_BOOL, true);

        }
    }

    private void moveLoginActivity() {


        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();

    }



    private void moveMainActivity() {


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
