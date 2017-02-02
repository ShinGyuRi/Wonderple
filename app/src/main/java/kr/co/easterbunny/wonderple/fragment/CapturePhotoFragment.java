package kr.co.easterbunny.wonderple.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.co.easterbunny.wonderple.R;
import kr.co.easterbunny.wonderple.library.ParentFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CapturePhotoFragment extends ParentFragment {


    public CapturePhotoFragment() {
        // Required empty public constructor
    }

    public static CapturePhotoFragment newInstance() {
        CapturePhotoFragment frag = new CapturePhotoFragment();
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_capture_photo, container, false);
    }

}
