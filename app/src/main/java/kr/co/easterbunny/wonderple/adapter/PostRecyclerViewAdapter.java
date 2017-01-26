package kr.co.easterbunny.wonderple.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import kr.co.easterbunny.wonderple.R;
import kr.co.easterbunny.wonderple.databinding.PostListItemBinding;

/**
 * Created by scona on 2017-01-19.
 */

public class PostRecyclerViewAdapter extends RecyclerView.Adapter<PostRecyclerViewAdapter.BindingHolder> {


    private List<Integer> posts;
    private List<String> items;

    private Context context;



    public class BindingHolder extends RecyclerView.ViewHolder  {
        private final PostListItemBinding binding;

        public String mBoundString;

        public BindingHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
        
    }


    public PostRecyclerViewAdapter(Context context, List<Integer> posts, List<String> items) {
        this.context = context;
        this.posts = posts;
        this.items = items;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_list_item, parent, false);
        return new BindingHolder(view);
    }

    @Override
    public void onBindViewHolder(PostRecyclerViewAdapter.BindingHolder holder, int position) {
        holder.mBoundString = items.get(position);
        Glide.with(context)
                .load(posts.get(position))
                .thumbnail(0.1f)
                .into(holder.binding.imgPost);
        holder.binding.txtUsername.setText(items.get(position));
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


}
