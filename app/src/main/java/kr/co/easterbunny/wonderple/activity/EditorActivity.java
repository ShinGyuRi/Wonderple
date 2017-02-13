package kr.co.easterbunny.wonderple.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.ColorOverlaySubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;

import java.util.List;

import kr.co.easterbunny.wonderple.R;
import kr.co.easterbunny.wonderple.adapter.EffectAdapter;
import kr.co.easterbunny.wonderple.databinding.ActivityEditorBinding;
import kr.co.easterbunny.wonderple.library.ParentActivity;
import kr.co.easterbunny.wonderple.library.util.JSLog;
import kr.co.easterbunny.wonderple.listener.EffectAdapterListener;
import kr.co.easterbunny.wonderple.manager.ThumbnailManager;
import kr.co.easterbunny.wonderple.model.Session;
import kr.co.easterbunny.wonderple.model.Thumbnail;
import kr.co.easterbunny.wonderple.view.ToolbarView;

public class EditorActivity extends ParentActivity implements ToolbarView.OnClickTitleListener,
        ToolbarView.OnClickNextListener, ToolbarView.OnClickBackListener,
        EffectAdapterListener {

    static {
        System.loadLibrary("NativeImageProcessor");
    }

    private ActivityEditorBinding binding;

    private Session mSession = Session.getInstance();

    private Filter mCurrentFilter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_editor);
        overridePendingTransition(R.anim.left, R.anim.zoom_out);
        initViews();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right, R.anim.zoom_out);
    }

    private void initViews() {
        binding.mEditorToolbar.setOnClickBackMenuListener(this)
                .setOnClickTitleListener(this)
                .setOnClickNextListener(this)
                .setTitle(getResources().getString(R.string.toolbar_title_editor))
                .showNext();

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) binding.mEffectPreview.getLayoutParams();
        lp.height = getResources().getDisplayMetrics().widthPixels;
        binding.mEffectPreview.setLayoutParams(lp);

        binding.mEffectChooserRecyclerView.setItemAnimator(new DefaultItemAnimator());
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.mEffectChooserRecyclerView.setLayoutManager(mLayoutManager);

        final EffectAdapter effectAdapter = new EffectAdapter(this);
        effectAdapter.setListener(this);
        binding.mEffectChooserRecyclerView.setAdapter(effectAdapter);

        Glide.with(this)
                .load(Uri.fromFile(mSession.getFileToUpload()))
                .into(binding.mEffectPreview);

        binding.mEffectPreview.setOnTouchListener(setOnTouchListener());

        effectAdapter.setItems(getFilters());


    }

    private List<Thumbnail> getFilters() {
        Bitmap bitmap = getBitmapFromFile();
        JSLog.D("bitmap::::::::::::"+bitmap.toString(), new Throwable());

        Thumbnail t1 = new Thumbnail();
        Thumbnail t2 = new Thumbnail();
        Thumbnail t3 = new Thumbnail();
        Thumbnail t4 = new Thumbnail();
        Thumbnail t5 = new Thumbnail();
        Thumbnail t6 = new Thumbnail();
        Thumbnail t7 = new Thumbnail();

        t1.image = bitmap;
        t2.image = bitmap;
        t3.image = bitmap;
        t4.image = bitmap;
        t5.image = bitmap;
        t6.image = bitmap;
        t7.image = bitmap;

        ThumbnailManager.clearThumbs();
        t1.name = "None";
        ThumbnailManager.addThumb(t1);

        t2.name = "StarLit";
        t2.filter = SampleFilters.getStarLitFilter();
        ThumbnailManager.addThumb(t2);

        t3.name = "BlueMess";
        t3.filter = SampleFilters.getBlueMessFilter();
        ThumbnailManager.addThumb(t3);

        t4.name = "AweStruckVibe";
        t4.filter = SampleFilters.getAweStruckVibeFilter();
        ThumbnailManager.addThumb(t4);

        t5.name = "Lime";
        t5.filter = SampleFilters.getLimeStutterFilter();
        ThumbnailManager.addThumb(t5);

        t6.name = "B&W";
        t6.filter = new Filter();
        t6.filter.addSubFilter(new SaturationSubfilter(-100f));
        ThumbnailManager.addThumb(t6);

        t7.name = "Sepia";
        t7.filter = new Filter();
        t7.filter.addSubFilter(new SaturationSubfilter(-100f));
        t7.filter.addSubFilter(new ColorOverlaySubfilter(1, 102, 51, 0));
        ThumbnailManager.addThumb(t7);

        return ThumbnailManager.processThumbs(this);
    }

    private View.OnTouchListener setOnTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int action = motionEvent.getAction();
                if (mCurrentFilter != null) {
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            binding.mEffectPreview.setImageBitmap(getBitmapFromFile());
                            break;
                        case MotionEvent.ACTION_UP:
                            binding.mEffectPreview.setImageBitmap(mCurrentFilter.processFilter(getBitmapFromFile()));
                            break;
                        default:
                            break;
                    }
                }

                return true;
            }
        };
    }

    private Bitmap getBitmapFromFile() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inMutable = true;
        JSLog.D("mSession.getFileToUpload()"+mSession.getFileToUpload().toString(), new Throwable());
        return BitmapFactory.decodeFile(mSession.getFileToUpload().getAbsolutePath(), options);
    }

    @Override
    public void onClickBack() {
        this.onBackPressed();
    }

    @Override
    public void onClickNext() {

    }

    @Override
    public void onClickTitle() {

    }

    @Override
    public void applyEffectType(Filter filter) {
        if (filter != mCurrentFilter) {
            mCurrentFilter = filter;
            binding.mEffectPreview.setImageBitmap(filter.processFilter(getBitmapFromFile()));
        }
    }
}
