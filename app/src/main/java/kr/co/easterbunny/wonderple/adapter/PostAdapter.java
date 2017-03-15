package kr.co.easterbunny.wonderple.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.List;

import kr.co.easterbunny.wonderple.R;
import kr.co.easterbunny.wonderple.databinding.ViewPostItemBinding;
import kr.co.easterbunny.wonderple.library.util.ImageUtil;
import kr.co.easterbunny.wonderple.library.util.JSLog;
import kr.co.easterbunny.wonderple.model.LoadMainResult;

/**
 * Created by scona on 2017-01-19.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.BindingHolder> {


    private List<LoadMainResult.PostImage> postImages;

    private Context context;



    public class BindingHolder extends RecyclerView.ViewHolder  {
        private final ViewPostItemBinding binding;

        public BindingHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
        
    }


    public PostAdapter(Context context, List<LoadMainResult.PostImage> postImages) {
        this.context = context;
        this.postImages = postImages;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_post_item, parent, false);
        return new BindingHolder(view);
    }

    @Override
    public void onBindViewHolder(PostAdapter.BindingHolder holder, int position) {

        double positionHeight = ImageUtil.getPositionRatio(position, Double.parseDouble(postImages.get(position).getRatio()));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int gravity = "1".equals(postImages.get(position).getRandomNum()) ? Gravity.RIGHT : Gravity.LEFT;
        params.gravity = gravity;

        holder.binding.layoutPost.setLayoutParams(params);

        holder.binding.imgPost.setHeightRatio(positionHeight);
        Glide.with(context)
                .load(postImages.get(position).getImageUrl())
                .thumbnail(0.1f)
                .centerCrop()
                .into(holder.binding.imgPost);
        holder.binding.txtUsername.setText(postImages.get(position).getUser().getName());
    }


    @Override
    public int getItemCount() {
        if (postImages == null) {
            return 0;
        }
        return postImages.size();
    }


}
