package kr.co.easterbunny.wonderple.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import kr.co.easterbunny.wonderple.R;
import kr.co.easterbunny.wonderple.adapter.TagItemAdapter;
import kr.co.easterbunny.wonderple.databinding.ActivityPostDetailBinding;
import kr.co.easterbunny.wonderple.library.ParentActivity;
import kr.co.easterbunny.wonderple.library.WonderpleLib;
import kr.co.easterbunny.wonderple.library.util.NetworkUtil;
import kr.co.easterbunny.wonderple.model.CustomBitmapPool;
import kr.co.easterbunny.wonderple.model.LoadPostResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailActivity extends ParentActivity {

    private ActivityPostDetailBinding binding;

    private List<LoadPostResult.TagItem> tagItems = new ArrayList<>();

    String username = null;
    String profileImgPath = null;
    String uid = null;
    String iid = null;
    String imageUrl = null;
    double positionHeight = 0.0;

    String wonderpleCount = null;
    String wonderCount = null;
    String commentCount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_detail);

        initView();

        loadPostData(uid, iid);

    }

    private void initView() {
        binding.imgDot.setVisibility(View.GONE);
        binding.tvWonderple.setVisibility(View.GONE);
        binding.tvWonder.setVisibility(View.GONE);
        binding.tvComment.setVisibility(View.GONE);

        Intent intent = getIntent();

        username = intent.getStringExtra("username");
        profileImgPath = intent.getStringExtra("profileImgPath");
        uid = intent.getStringExtra("uid");
        iid = intent.getStringExtra("iid");
        imageUrl = intent.getStringExtra("imgUrl");
        positionHeight = intent.getDoubleExtra("positionHeight", 1.0);

        if (profileImgPath != null) {
            Glide.with(this)
                    .load(profileImgPath)
                    .bitmapTransform(new CropCircleTransformation(new CustomBitmapPool()))
                    .into(binding.imgProfile);
            binding.tvUsername.setText(username);
        }

        binding.imgPost.setHeightRatio(positionHeight);
        if (imageUrl != null) {
            Glide.with(this)
                    .load(imageUrl)
                    .thumbnail(0.1f)
                    .into(binding.imgPost);
        }
    }

    private void loadPostData(String uid, String iid) {

        Call<LoadPostResult> loadPostResultCall = NetworkUtil.getInstace().loadPost(iid, uid);
        loadPostResultCall.enqueue(new Callback<LoadPostResult>() {
            @Override
            public void onResponse(Call<LoadPostResult> call, Response<LoadPostResult> response) {
                LoadPostResult loadPostResult = response.body();
                String message = loadPostResult.getMessage().toString();

                if ("the data load succeeded".equals(message)) {
                    wonderpleCount = loadPostResult.getPostData().getWonderpleCount();
                    wonderCount = loadPostResult.getPostData().getWonderCount();
                    commentCount = loadPostResult.getPostData().getCommentCount();

                    if (!"0".equals(wonderpleCount) || !"0".equals(wonderCount) || !"0".equals(commentCount)) {
                        binding.imgDot.setVisibility(View.VISIBLE);
                    }
                    if (!"0".equals(wonderpleCount)) {
                        binding.tvWonderple.setText(getString(R.string.str_post_detail_wonderple_count, wonderpleCount));
                        binding.tvWonderple.setVisibility(View.VISIBLE);
                    }
                    if (!"0".equals(wonderCount)) {
                        binding.tvWonder.setText(getString(R.string.str_post_detail_wonder_count, wonderCount));
                        binding.tvWonder.setVisibility(View.VISIBLE);
                    }
                    if (!"0".equals(commentCount)) {
                        binding.tvComment.setText(getString(R.string.str_post_detail_comment_count, commentCount));
                        binding.tvComment.setVisibility(View.VISIBLE);
                    }

                    if (loadPostResult.getTagItems() != null) {
                        tagItems = loadPostResult.getTagItems();

                        binding.rvTagItem.setAdapter(new TagItemAdapter(PostDetailActivity.this, tagItems));
                        binding.rvTagItem.setLayoutManager(new LinearLayoutManager(PostDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    }
                }



            }

            @Override
            public void onFailure(Call<LoadPostResult> call, Throwable t) {

            }
        });
    }
}
