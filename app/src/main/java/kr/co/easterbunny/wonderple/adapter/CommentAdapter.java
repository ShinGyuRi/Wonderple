package kr.co.easterbunny.wonderple.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.fobid.linkabletext.view.OnLinkClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import kr.co.easterbunny.wonderple.R;
import kr.co.easterbunny.wonderple.databinding.ViewCommentItemBinding;
import kr.co.easterbunny.wonderple.library.util.TimeUtils;
import kr.co.easterbunny.wonderple.library.util.Toaster;
import kr.co.easterbunny.wonderple.model.CalloutLink;
import kr.co.easterbunny.wonderple.model.CustomBitmapPool;
import kr.co.easterbunny.wonderple.model.Hashtag;
import kr.co.easterbunny.wonderple.model.LoadCommentResult;

/**
 * Created by scona on 2017-03-20.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.BindingHolder> {

    private Context context;
    private List<LoadCommentResult.Comment> comments = new ArrayList<>();

    public class BindingHolder extends RecyclerView.ViewHolder {

        private ViewCommentItemBinding binding;

        public BindingHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public ViewCommentItemBinding getBinding() {
            return binding;
        }
    }

    public CommentAdapter(Context context, List<LoadCommentResult.Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @Override
    public CommentAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_comment_item, parent, false);
        return new BindingHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentAdapter.BindingHolder holder, int position) {
        Glide.with(context)
                .load(comments.get(position).getProfileImg())
                .bitmapTransform(new CropCircleTransformation(new CustomBitmapPool()))
                .into(holder.binding.imgProfile);

        int dDay = TimeUtils.dDay(comments.get(position).getDate());
        String beforeDay;

        if (dDay < 31 && dDay > 0)  {   beforeDay = context.getResources().getString(R.string.str_post_detail_comment_dday, String.valueOf(dDay));  }
        else if (dDay == 0)         {   beforeDay = "오늘";   }
        else                        {   beforeDay = comments.get(position).getDate();   }


        holder.binding.tvUsername.setText(comments.get(position).getName());
        holder.binding.tvBeforeDay.setText(beforeDay);
        holder.binding.tvComment.setText(comments.get(position).getDetail());

        holder.binding.tvComment.setOnLinkClickListener(new OnLinkClickListener() {
            @Override
            public void onHashtagClick(String hashtag) {
                Toaster.shortToast(hashtag);
            }

            @Override
            public void onMentionClick(String mention) {
                Toaster.shortToast(mention);
            }

            @Override
            public void onEmailAddressClick(String email) {

            }

            @Override
            public void onWebUrlClick(String url) {

            }

            @Override
            public void onPhoneClick(String phone) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if (comments == null) {
            return 0;
        }
        return comments.size();
    }

    public void add(List<LoadCommentResult.Comment> nextComments) {
        comments.addAll(nextComments);
        notifyDataSetChanged();
    }
}
