package kr.co.easterbunny.wonderple.fragment;


import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kr.co.easterbunny.wonderple.R;
import kr.co.easterbunny.wonderple.adapter.PostAdapter;
import kr.co.easterbunny.wonderple.databinding.FragmentHomeBinding;
import kr.co.easterbunny.wonderple.library.ParentFragment;
import kr.co.easterbunny.wonderple.library.util.JSLog;
import kr.co.easterbunny.wonderple.library.util.NetworkUtil;
import kr.co.easterbunny.wonderple.model.LoadMainResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends ParentFragment {


    private FragmentHomeBinding binding;

    private String uid;

    List<LoadMainResult.PostImage> postImages = new ArrayList<>();


    public HomeFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public HomeFragment(String uid) {
        this.uid = uid;
    }


    public static HomeFragment newInstance(String uid) {
        HomeFragment frag = new HomeFragment(uid);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View view = binding.getRoot();
        binding.setHome(this);

        binding.tvFollowingGuide.setVisibility(View.GONE);
        binding.line.setVisibility(View.GONE);
        binding.imgLogo.setVisibility(View.GONE);
        binding.btnBrowse.setVisibility(View.GONE);
        binding.tvBrowse.setVisibility(View.GONE);

        loadPostImage();

        return view;
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_browse:
                SearchFragment searchFragment = SearchFragment.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment, "SEARCH").addToBackStack("SEARCH").commit();
                break;
        }
    }
//
    private void loadPostImage() {
        Call<LoadMainResult> loadMainResultCall = NetworkUtil.getInstace().loadMain(uid);
        loadMainResultCall.enqueue(new Callback<LoadMainResult>() {
            @Override
            public void onResponse(Call<LoadMainResult> call, Response<LoadMainResult> response) {
                LoadMainResult loadMainResult = response.body();

                String message = loadMainResult.getMessage().toString().replace("\"", "");

                if ("the main load succeeded".equals(message)) {
                    if (loadMainResult.getPostImages() != null) {

                        postImages = loadMainResult.getPostImages();

                        binding.recyclerviewPostImage.setAdapter(new PostAdapter(getContext(), postImages));
                        binding.recyclerviewPostImage.setLayoutManager(new LinearLayoutManager(getActivity()));

                    } else {
                        binding.tvFollowingGuide.setVisibility(View.VISIBLE);
                        binding.line.setVisibility(View.VISIBLE);
                        binding.imgLogo.setVisibility(View.VISIBLE);
                        binding.btnBrowse.setVisibility(View.VISIBLE);
                        binding.tvBrowse.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoadMainResult> call, Throwable t) {
                JSLog.E("failed Load main ::::: "+t.getMessage(), new Throwable());
            }
        });
    }
}
