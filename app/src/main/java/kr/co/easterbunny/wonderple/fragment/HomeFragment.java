package kr.co.easterbunny.wonderple.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

import kr.co.easterbunny.wonderple.R;
import kr.co.easterbunny.wonderple.adapter.PostAdapter;
import kr.co.easterbunny.wonderple.databinding.FragmentHomeBinding;
import kr.co.easterbunny.wonderple.library.ParentFragment;
import kr.co.easterbunny.wonderple.model.Cheeses;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends ParentFragment {


    private FragmentHomeBinding binding;



    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        HomeFragment frag = new HomeFragment();
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding = FragmentHomeBinding.bind(getView());
        binding.recyclerviewPostImage.setAdapter(new PostAdapter(getContext(), Arrays.asList(Cheeses.postImage), Arrays.asList(Cheeses.sCheeseStrings)));
        binding.recyclerviewPostImage.setLayoutManager(new LinearLayoutManager(getActivity()));


    }
}
