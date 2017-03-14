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
import kr.co.easterbunny.wonderple.databinding.ViewPostItemBinding;
import kr.co.easterbunny.wonderple.library.util.JSLog;

/**
 * Created by scona on 2017-01-19.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.BindingHolder> {


    private List<String> posts;
    private List<String> usernames;

    private Context context;



    public class BindingHolder extends RecyclerView.ViewHolder  {
        private final ViewPostItemBinding binding;

        public String mBoundString;

        public BindingHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
        
    }


    public PostAdapter(Context context, List<String> posts, List<String> usernames) {
        this.context = context;
        this.posts = posts;
        this.usernames = usernames;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_post_item, parent, false);
        return new BindingHolder(view);
    }

    @Override
    public void onBindViewHolder(PostAdapter.BindingHolder holder, int position) {

        JSLog.D("posts ::::: "+posts.get(position), new Throwable());
        JSLog.D("usernames :::::"+usernames.get(position), new Throwable());

        holder.mBoundString = usernames.get(position);
        Glide.with(context)
                .load(posts.get(position))
                .thumbnail(0.1f)
                .into(holder.binding.imgPost);
        holder.binding.txtUsername.setText(usernames.get(position));
    }


    @Override
    public int getItemCount() {
        if (usernames == null) {
            return 0;
        }
        return usernames.size();
    }


}
