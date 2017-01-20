package kr.co.easterbunny.wonderple.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;


import java.util.Arrays;

import kr.co.easterbunny.wonderple.R;
import kr.co.easterbunny.wonderple.adapter.PostImageRecyclerViewAdapter;
import kr.co.easterbunny.wonderple.databinding.ActivityMainBinding;
import kr.co.easterbunny.wonderple.fragment.HomeFragment;
import kr.co.easterbunny.wonderple.library.ParentActivity;
import kr.co.easterbunny.wonderple.library.util.JSLog;
import kr.co.easterbunny.wonderple.model.Cheeses;

public class MainActivity extends ParentActivity {

    public static final String TAG = MainActivity.class.getSimpleName();


    private ActivityMainBinding binding;


    private Fragment homeFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    }


    public void switchContent(Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();

        JSLog.D("back stack cnt:" + fm.getBackStackEntryCount(),new Throwable());
        for (Fragment fragment1 : fm.getFragments()) {
            if (fragment1 != null && fragment1.getTag().equals(tag)) {
                //이렇게 해도 직전 프래그먼트만 pop됨 ㅡㅡ
                boolean result = fm.popBackStackImmediate(tag,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                JSLog.D(tag +" , rst:"+result + ","+fm.getBackStackEntryCount(),new Throwable());
                break;
            }
        }

        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(tag);
        ft.replace(R.id.container, fragment, tag).commit();
    }
}
