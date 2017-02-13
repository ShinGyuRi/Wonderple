package kr.co.easterbunny.wonderple.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;

import kr.co.easterbunny.wonderple.R;
import kr.co.easterbunny.wonderple.databinding.ViewStaggeredgridItemBinding;
import kr.co.easterbunny.wonderple.library.util.JSLog;

/**
 * Created by scona on 2017-01-26.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.BindingHolder> {

    private List<Integer> posts;

    private Context context;

    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();

    private final Random mRandom;



    public class BindingHolder extends RecyclerView.ViewHolder  {
        private final ViewStaggeredgridItemBinding binding;

        public BindingHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

    }


    public SearchAdapter(Context context, List<Integer> posts) {
        this.context = context;
        this.posts = posts;
        mRandom = new Random();
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_staggeredgrid_item, parent, false);
        return new BindingHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.BindingHolder holder, int position) {

        double positionHeight = getPositionRatio(position);

        JSLog.D("getView position:" + position + " h:" + positionHeight, new Throwable());

        holder.binding.postImage.setHeightRatio(positionHeight);
        Glide.with(context)
                .load(posts.get(position))
                .thumbnail(0.1f)
                .centerCrop()
                .into(holder.binding.postImage);

    }


    @Override
    public int getItemCount() {
        return posts.size();
    }


    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
        }
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
            JSLog.D("getPositionRatio:" + position + " ratio:" + ratio, new Throwable());
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5 the width
    }
}
