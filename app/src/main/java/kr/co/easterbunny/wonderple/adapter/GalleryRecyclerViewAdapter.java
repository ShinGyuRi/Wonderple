package kr.co.easterbunny.wonderple.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Objects;

import kr.co.easterbunny.wonderple.R;
import kr.co.easterbunny.wonderple.databinding.ListItemImageBinding;
import kr.co.easterbunny.wonderple.databinding.MediaItemViewBinding;
import kr.co.easterbunny.wonderple.library.util.JSLog;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by scona on 2017-02-02.
 */

public class GalleryRecyclerViewAdapter extends RecyclerView.Adapter<GalleryRecyclerViewAdapter.BindingHolder> {


    private Context context;

    private List<Uri> images;


    public class BindingHolder extends RecyclerView.ViewHolder  {
        private final MediaItemViewBinding binding;

        protected Uri uri;

        public BindingHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

    }

    public GalleryRecyclerViewAdapter(Context context, List<Uri> images) {
        this.context = context;
        this.images = images;
    }


    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.media_item_view, parent, false);
        return new BindingHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryRecyclerViewAdapter.BindingHolder holder, int position) {

        final Uri mUri = images.get(position);
        boolean isSelected = images.contains(mUri);


        JSLog.D("getView position:" + position, new Throwable());


        if (holder.uri == null || !holder.uri.equals(mUri)) {


            Glide.with(context)
                    .load(mUri.toString())
                    .thumbnail(0.1f)
                    .dontAnimate()
                    .centerCrop()
                    .into(holder.binding.imgMediaThumb);

            holder.uri = mUri;
        }


    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public Uri getItem(int position) {
        return images.get(position);
    }
}
