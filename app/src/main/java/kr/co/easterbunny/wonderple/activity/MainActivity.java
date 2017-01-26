package kr.co.easterbunny.wonderple.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import kr.co.easterbunny.wonderple.R;
import kr.co.easterbunny.wonderple.databinding.ActivityMainBinding;
import kr.co.easterbunny.wonderple.fragment.SearchFragment;
import kr.co.easterbunny.wonderple.fragment.HomeFragment;
import kr.co.easterbunny.wonderple.library.ParentActivity;
import kr.co.easterbunny.wonderple.library.util.JSLog;

public class MainActivity extends ParentActivity {

    public static final String TAG = MainActivity.class.getSimpleName();


    private ActivityMainBinding binding;


    public Fragment homeFragment, curationFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setSwitchContent(this);


        homeFragment = HomeFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment, "HOME").addToBackStack("HOME").commit();
        binding.btnHome.setEnabled(false);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getSupportFragmentManager().addOnBackStackChangedListener(backStackChangedListener);
    }

    public void bottomBtnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home:
                initNaviButton(v);
                if(homeFragment == null)
                    homeFragment = HomeFragment.newInstance();
                switchContent(homeFragment, "HOME");
                break;
            case R.id.btn_curation:
                initNaviButton(v);
                if (curationFragment == null)
                    curationFragment = SearchFragment.newInstance();
                switchContent(curationFragment, "CURATION");
                break;
        }
    }


    public void initNaviButton(View v) {
        binding.btnHome.setEnabled(true);
        binding.btnCuration.setEnabled(true);
        v.setEnabled(false);
    }

    public void switchContent(Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();

        JSLog.D("back stack cnt:" + fm.getBackStackEntryCount(),new Throwable());

        boolean fragmentPopped = fm.popBackStackImmediate(tag, 0);

        if (!fragmentPopped) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.addToBackStack(tag);
            ft.replace(R.id.container, fragment, tag).commit();
        }

    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        getSupportFragmentManager().removeOnBackStackChangedListener(backStackChangedListener);
    }

    private FragmentManager.OnBackStackChangedListener backStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            String currentTab = getSupportFragmentManager().findFragmentById(R.id.container).getTag();
            JSLog.D(currentTab, new Throwable());
            if(currentTab==null) return;
            switch(currentTab){
                case "HOME":
                    initNaviButton(binding.btnHome);
                    break;
                case "CURATION":
                    initNaviButton(binding.btnCuration);
                    break;
            }
        }
    };
}
