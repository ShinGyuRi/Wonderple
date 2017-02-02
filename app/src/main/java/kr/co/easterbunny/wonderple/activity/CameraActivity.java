package kr.co.easterbunny.wonderple.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.HashSet;

import kr.co.easterbunny.wonderple.R;
import kr.co.easterbunny.wonderple.adapter.ViewPagerAdapter;
import kr.co.easterbunny.wonderple.databinding.ActivityCameraBinding;
import kr.co.easterbunny.wonderple.fragment.CapturePhotoFragment;
import kr.co.easterbunny.wonderple.fragment.GalleryPickerFragment;
import kr.co.easterbunny.wonderple.library.ParentActivity;
import kr.co.easterbunny.wonderple.listener.CaturePhotoFragmentListener;
import kr.co.easterbunny.wonderple.listener.GalleryPickerFragmentListener;
import kr.co.easterbunny.wonderple.model.SourceType;

public class CameraActivity extends ParentActivity implements CaturePhotoFragmentListener, GalleryPickerFragmentListener {

    private ActivityCameraBinding binding;

    private HashSet<SourceType> mSourceTypeSet = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_camera);

        mSourceTypeSet.add(SourceType.Gallery);
        mSourceTypeSet.add(SourceType.Photo);

        initViews();
    }

    private void initViews()    {

        final ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getListFragment());
        binding.mMainViewPager.setAdapter(pagerAdapter);

        binding.mMainTabLayout.addOnTabSelectedListener(getViewPagerOnTabSelectedListener());
        binding.mMainViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.mMainTabLayout));

        binding.mMainViewPager.setCurrentItem(0);
    }

    private ArrayList<Fragment> getListFragment()   {
        ArrayList<Fragment> fragments = new ArrayList<>();

        if (mSourceTypeSet.contains(SourceType.Gallery)) {
            fragments.add(GalleryPickerFragment.newInstance());
            binding.mMainTabLayout.addTab(binding.mMainTabLayout.newTab().setText(R.string.tab_gallery));
        }

        if (mSourceTypeSet.contains(SourceType.Photo)) {
            fragments.add(CapturePhotoFragment.newInstance());
            binding.mMainTabLayout.addTab(binding.mMainTabLayout.newTab().setText(R.string.tab_photo));
        }

        return fragments;
    }

    private TabLayout.ViewPagerOnTabSelectedListener getViewPagerOnTabSelectedListener()    {
        return new TabLayout.ViewPagerOnTabSelectedListener(binding.mMainViewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
//                displayTitleByTab(tab);
//                initNextButtonByTab(tab.getPosition());
            }
        };
    }

    private void displayTitleByTab(TabLayout.Tab tab) {
        if (tab.getText() != null) {
            String title = tab.getText().toString();
        }
    }

    private void initNextButtonByTab(int position) {
    }

    private void openPhotoEditor() {
        startActivity(new Intent(this, EditorActivity.class));
    }

    @Override
    public void openEditor() {
        openPhotoEditor();
    }
}
