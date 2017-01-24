package kr.co.easterbunny.wonderple.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.List;

import kr.co.easterbunny.wonderple.R;

/**
 * Created by scona on 2017-01-19.
 */

public class PostImageRecyclerViewAdapter extends RecyclerView.Adapter<PostImageRecyclerViewAdapter.ViewHolder> {


    private List<Integer> posts;
    private List<String> items;

    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public String mBoundString;

        public final View mView;
        public final ImageView imgPost;
        public final TextView txtUsername;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imgPost = (ImageView) view.findViewById(R.id.img_post) ;
            txtUsername = (TextView) view.findViewById(R.id.txt_username);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + txtUsername.getText();
        }
    }


    public PostImageRecyclerViewAdapter(Context context, List<Integer> posts, List<String> items) {
        this.context = context;
        this.posts = posts;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mBoundString = items.get(position);
        Glide.with(context)
                .load(posts.get(position))
                .thumbnail(0.1f)
                .into(holder.imgPost);
        holder.txtUsername.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
