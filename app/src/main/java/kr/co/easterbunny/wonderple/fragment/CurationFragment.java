package kr.co.easterbunny.wonderple.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import com.etsy.android.grid.StaggeredGridView;

import java.util.ArrayList;

import kr.co.easterbunny.wonderple.R;
import kr.co.easterbunny.wonderple.adapter.CurationGridAdapter;
import kr.co.easterbunny.wonderple.databinding.FragmentCurationBinding;
import kr.co.easterbunny.wonderple.library.util.JSLog;
import kr.co.easterbunny.wonderple.model.SampleData;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurationFragment extends Fragment implements AbsListView.OnScrollListener, AbsListView.OnItemClickListener {


    private StaggeredGridView gridView;
    private boolean hasRequestedMore;
    private CurationGridAdapter adapter;

    private ArrayList<String> mData;

    private FragmentCurationBinding binding;


    public CurationFragment() {
        // Required empty public constructor
    }

    public static CurationFragment newInstance()    {
        CurationFragment frag = new CurationFragment();
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_curation, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding = FragmentCurationBinding.bind(getView());


        if (adapter == null) {
            adapter = new CurationGridAdapter(getActivity(), R.id.post_image);
        }

        if (mData == null) {
            mData = SampleData.generateSampleData();
        }

        for (String data : mData) {
            adapter.add(data);
        }

        binding.gridView.setAdapter(adapter);
        binding.gridView.setOnScrollListener(this);
        binding.gridView.setOnItemClickListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        JSLog.D("onScrollStateChanged:" + scrollState, new Throwable());
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        JSLog.D("onScroll firstVisibleItem:" + firstVisibleItem +
                " visibleItemCount:" + visibleItemCount +
                " totalItemCount:" + totalItemCount, new Throwable());


        // our handling
        if (!hasRequestedMore) {
            int lastInScreen = firstVisibleItem + visibleItemCount;
            if (lastInScreen >= totalItemCount) {
                JSLog.D("onScroll lastInScreen - so load more", new Throwable());
                hasRequestedMore = true;
                onLoadMoreItems();
            }
        }
    }


    private void onLoadMoreItems() {
        final ArrayList<String> sampleData = SampleData.generateSampleData();
        for (String data : sampleData) {
            adapter.add(data);
        }
        // stash all the data in our backing store
        mData.addAll(sampleData);
        // notify the adapter that we can update now
        adapter.notifyDataSetChanged();
        hasRequestedMore = false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
