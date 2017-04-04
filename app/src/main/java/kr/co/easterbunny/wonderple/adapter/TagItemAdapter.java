package kr.co.easterbunny.wonderple.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import kr.co.easterbunny.wonderple.R;
import kr.co.easterbunny.wonderple.databinding.ViewTagItemBinding;
import kr.co.easterbunny.wonderple.model.CustomBitmapPool;
import kr.co.easterbunny.wonderple.model.LoadPostResult;

/**
 * Created by scona on 2017-03-15.
 */

public class TagItemAdapter extends RecyclerView.Adapter<TagItemAdapter.BindingHolder> {

    private ViewTagItemBinding binding;

    private Context context;

    private List<LoadPostResult.TagItem> tagItems = new ArrayList<>();
    private List<LoadPostResult.WonderCategory> wonderCategories = new ArrayList<>();

    public class BindingHolder extends RecyclerView.ViewHolder {
        public BindingHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public ViewTagItemBinding getBinding() {
            return binding;
        }
    }

    public TagItemAdapter(Context context, List<LoadPostResult.TagItem> tagItems, List<LoadPostResult.WonderCategory> wonderCategories) {
        this.context = context;
        this.tagItems = tagItems;
        this.wonderCategories = wonderCategories;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_tag_item, parent, false);
        return new BindingHolder(view);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        binding.imgDot.setVisibility(View.GONE);

        Glide.with(context)
                .load(tagItems.get(position).getImage())
                .thumbnail(0.1f)
                .into(binding.imgTagItem);

        int r = Integer.parseInt(tagItems.get(position).getR());
        int g = Integer.parseInt(tagItems.get(position).getG());
        int b = Integer.parseInt(tagItems.get(position).getB());

        Glide.with(context)
                .load(R.drawable.circle_black)
                .bitmapTransform(new ColorFilterTransformation(new CustomBitmapPool(), Color.rgb(r, g, b)), new CropCircleTransformation(new CustomBitmapPool()))
                .into(binding.imgColor);

        if (wonderCategories != null && wonderCategories.size()>=position+1) {
            if ((""+(position+1)).equals(wonderCategories.get(position).getWonderCategory())) {
                binding.imgDot.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (tagItems == null) {
            return 0;
        }
        return tagItems.size();
    }


}
