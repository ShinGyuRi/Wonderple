package kr.co.easterbunny.wonderple.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import kr.co.easterbunny.wonderple.R;
import kr.co.easterbunny.wonderple.databinding.ViewTagItemBinding;
import kr.co.easterbunny.wonderple.model.LoadPostResult;

/**
 * Created by scona on 2017-03-15.
 */

public class TagItemAdapter extends RecyclerView.Adapter<TagItemAdapter.BindingHolder> {

    private ViewTagItemBinding binding;

    private Context context;

    private List<LoadPostResult.TagItem> tagItems = new ArrayList<>();

    public class BindingHolder extends RecyclerView.ViewHolder {
        public BindingHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public ViewTagItemBinding getBinding() {
            return binding;
        }
    }

    public TagItemAdapter(Context context, List<LoadPostResult.TagItem> tagItems) {
        this.context = context;
        this.tagItems = tagItems;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_tag_item, parent, false);
        return new BindingHolder(view);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        Glide.with(context)
                .load(tagItems.get(position).getImage())
                .thumbnail(0.1f)
                .into(binding.imgTagItem);
    }

    @Override
    public int getItemCount() {
        if (tagItems == null) {
            return 0;
        }
        return tagItems.size();
    }


}
