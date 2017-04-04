package kr.co.easterbunny.wonderple.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.bumptech.glide.Glide;
import com.github.fobid.linkabletext.view.OnLinkClickListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import kr.co.easterbunny.wonderple.R;
import kr.co.easterbunny.wonderple.adapter.CommentAdapter;
import kr.co.easterbunny.wonderple.adapter.TagItemAdapter;
import kr.co.easterbunny.wonderple.adapter.UsersAdapter;
import kr.co.easterbunny.wonderple.databinding.ActivityPostDetailBinding;
import kr.co.easterbunny.wonderple.library.ParentActivity;
import kr.co.easterbunny.wonderple.library.util.NetworkUtil;
import kr.co.easterbunny.wonderple.library.util.Toaster;
import kr.co.easterbunny.wonderple.listener.RecyclerItemClickListener;
import kr.co.easterbunny.wonderple.mention.Mentions;
import kr.co.easterbunny.wonderple.mention.MentionsLoaderUtils;
import kr.co.easterbunny.wonderple.mention.QueryListener;
import kr.co.easterbunny.wonderple.mention.SuggestionsListener;
import kr.co.easterbunny.wonderple.model.CustomBitmapPool;
import kr.co.easterbunny.wonderple.model.LoadCommentResult;
import kr.co.easterbunny.wonderple.model.LoadMentionUserListResult;
import kr.co.easterbunny.wonderple.model.LoadPostResult;
import kr.co.easterbunny.wonderple.model.Mention;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailActivity extends ParentActivity implements OnLinkClickListener, QueryListener, SuggestionsListener{

    private ActivityPostDetailBinding binding;

    private List<LoadPostResult.TagItem> tagItems = new ArrayList<>();
    private List<LoadCommentResult.Comment> comments = new ArrayList<>();

    private Mentions mentions;
    private MentionsLoaderUtils mentionsLoaderUtils;

    String username = null;
    String profileImgPath = null;
    String uid = null;
    String iid = null;
    String imageUrl = null;
    double positionHeight = 0.0;

    String wonderpleCount = null;
    String wonderCount = null;
    String commentCount = null;

    private UsersAdapter usersAdapter;
    private CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_detail);
        binding.setPostDetail(this);
        binding.layoutComment.setContentComment(this);

        initView();
        loadPostData(uid, iid);
        setMentionsList();
        setCommentsList();
        setSendButtonTextWatcher();

    }

    private void initView() {
        binding.imgDot.setVisibility(View.GONE);
        binding.tvWonderple.setVisibility(View.GONE);
        binding.tvWonder.setVisibility(View.GONE);
        binding.tvDesc.setVisibility(View.GONE);
        binding.layoutComment.childLayoutComment.setVisibility(View.GONE);
        binding.btnMention.setVisibility(View.GONE);
        binding.btnHashtag.setVisibility(View.GONE);
        binding.layoutComment.tvPreComment.setVisibility(View.GONE);

        Intent intent = getIntent();

        username = intent.getStringExtra("username");
        profileImgPath = intent.getStringExtra("profileImgPath");
        uid = intent.getStringExtra("uid");
        iid = intent.getStringExtra("iid");
        imageUrl = intent.getStringExtra("imgUrl");
        positionHeight = intent.getDoubleExtra("positionHeight", 1.0);

        mentions = new Mentions.Builder(this, binding.layoutCommentField.etInputComment)
                .suggestionsListener(this)
                .queryListener(this)
                .build();
        mentionsLoaderUtils = new MentionsLoaderUtils(this, iid, uid);

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
                    String descText = loadPostResult.getPostData().getDescText();
                    String uploadTime = loadPostResult.getPostData().getUploadTime();

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
                        binding.layoutComment.childLayoutComment.setVisibility(View.VISIBLE);
                        binding.layoutComment.layoutCommentHeader.tvComment.setText(getString(R.string.str_post_detail_comment_count, commentCount));
                        binding.layoutComment.tvPreComment.setVisibility(View.VISIBLE);
                    }

                    if (!"".equals(descText)) {
                        binding.tvDesc.setVisibility(View.VISIBLE);
                        binding.tvDesc.setText(descText);
                        binding.tvDesc.setOnLinkClickListener(PostDetailActivity.this);
                    }

                    if (loadPostResult.getTagItems() != null) {
                        tagItems = loadPostResult.getTagItems();

                        binding.rvTagItem.setAdapter(new TagItemAdapter(PostDetailActivity.this, tagItems));
                        binding.rvTagItem.setLayoutManager(new LinearLayoutManager(PostDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    }

                    if (!"".equals(loadPostResult.getPostData().getUploadTime())) {
                        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Date date = null;
                        try {
                            date = inputFormat.parse(uploadTime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        DateFormat outputFormat = new SimpleDateFormat("yyyy년 M월 d일 a h:mm");
                        String outputString = outputFormat.format(date);

                        binding.tvDate.setText(outputString);
                    }
                }

            }

            @Override
            public void onFailure(Call<LoadPostResult> call, Throwable t) {

            }
        });
    }


    private void setMentionsList() {
        binding.layoutUserList.rvMentionList.setLayoutManager(new LinearLayoutManager(this));
        usersAdapter = new UsersAdapter(this);
        binding.layoutUserList.rvMentionList.setAdapter(usersAdapter);

        binding.layoutUserList.rvMentionList.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                final LoadMentionUserListResult.Users user = usersAdapter.getItem(position);
                /*
                 * We are creating a mentions object which implements the
                 * <code>Mentionable</code> interface this allows the library to set the offset
                 * and length of the mention.
                 */
                if (user != null) {
                    final Mention mention = new Mention();
                    mention.setMentionName("@"+user.getName());
                    mentions.insertMention(mention);
                }
            }
        }));
    }

    private void setCommentsList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        binding.layoutComment.rvComment.setLayoutManager(layoutManager);

//        StikkyHeaderBuilder.stickTo(binding.scrollView)
//                .setHeader(R.id.child_layout_comment_header, (ViewGroup) getLayoutInflater().inflate(R.layout.content_comment_header, null))
//                .minHeightHeaderDim(R.dimen.post_detail_comment_header_height)
//                .build();

        loadComment();

    }

    private void loadComment() {
        Call<LoadCommentResult> loadCommentResultCall = NetworkUtil.getInstace().loadComment(iid, uid);
        loadCommentResultCall.enqueue(new Callback<LoadCommentResult>() {
            @Override
            public void onResponse(Call<LoadCommentResult> call, Response<LoadCommentResult> response) {
                LoadCommentResult loadCommentResult = response.body();
                String message = loadCommentResult.getMessage();

                if ("comment load done".equals(message)) {
                    comments = loadCommentResult.getComments();

                    if (comments != null) {
                        commentAdapter = new CommentAdapter(PostDetailActivity.this, comments);
                        binding.layoutComment.rvComment.setAdapter(commentAdapter);
                    }

                }
            }

            @Override
            public void onFailure(Call<LoadCommentResult> call, Throwable t) {

            }
        });
    }

    private void loadNextComment() {
        Call<LoadCommentResult> loadCommentResultCall = NetworkUtil.getInstace().loadNextComment(iid, uid, "1");
        loadCommentResultCall.enqueue(new Callback<LoadCommentResult>() {
            @Override
            public void onResponse(Call<LoadCommentResult> call, Response<LoadCommentResult> response) {
                LoadCommentResult loadCommentResult = response.body();
                String message = loadCommentResult.getMessage();

                if ("next comment load done".equals(message)) {
                    if (loadCommentResult.getComments() != null) {
                        commentAdapter.add(loadCommentResult.getComments());
                        binding.layoutComment.tvPreComment.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoadCommentResult> call, Throwable t) {

            }
        });
    }

    private void setSendButtonTextWatcher() {
        final int hashtagColor = ContextCompat.getColor(PostDetailActivity.this, R.color.link);

        binding.layoutCommentField.etInputComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    binding.layoutCommentField.btnSend.setTextColor(hashtagColor);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_pre_comment:
                loadNextComment();
                break;
        }
    }

    @Override
    public void onHashtagClick(String hashtag) {
        Toaster.shortToast("Clicked hashtag is " + hashtag);
    }

    @Override
    public void onMentionClick(String mention) {
        Toaster.shortToast("Mention");
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

    @Override
    public void onQueryReceived(String query) {
        final List<LoadMentionUserListResult.Users> users = mentionsLoaderUtils.searchUsers(query);
        if (users != null && !users.isEmpty()) {
            usersAdapter.clear();
            usersAdapter.setCurrentQuery(query);
            usersAdapter.addAll(users);
            showMentionsList(true);
        } else {
            showMentionsList(false);
        }
    }

    @Override
    public void displaySuggestions(boolean display) {
        if (display) {
            binding.layoutUserList.childLayoutUserList.setVisibility(View.VISIBLE);
        } else {
            binding.layoutUserList.childLayoutUserList.setVisibility(View.GONE);
        }

    }

    private void showMentionsList(boolean display) {
        binding.layoutUserList.childLayoutUserList.setVisibility(View.VISIBLE);
        if (display) {
            binding.layoutUserList.rvMentionList.setVisibility(View.VISIBLE);
        } else {
            binding.layoutUserList.rvMentionList.setVisibility(View.GONE);
        }
    }
}
