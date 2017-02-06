package kr.co.easterbunny.wonderple.fragment;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kr.co.easterbunny.wonderple.R;
import kr.co.easterbunny.wonderple.adapter.GalleryRecyclerViewAdapter;
import kr.co.easterbunny.wonderple.bus.RxBusNext;
import kr.co.easterbunny.wonderple.databinding.FragmentGalleryPickerBinding;
import kr.co.easterbunny.wonderple.library.BaseApplication;
import kr.co.easterbunny.wonderple.library.ParentFragment;
import kr.co.easterbunny.wonderple.library.util.FileUtil;
import kr.co.easterbunny.wonderple.listener.GalleryPickerFragmentListener;
import kr.co.easterbunny.wonderple.listener.RecyclerViewOnItemClickListener;
import kr.co.easterbunny.wonderple.model.Session;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryPickerFragment extends ParentFragment {


    private FragmentGalleryPickerBinding binding;

    private GridLayoutManager gridLayoutManager;
    public static GalleryRecyclerViewAdapter galleryRecyclerViewAdapter;

    private GalleryPickerFragmentListener listener;
    private Session mSession = Session.getInstance();
    private final RxBusNext mRxBus = RxBusNext.getInstance();

    private static final int MARGING_GRID = 2;


    public static GalleryPickerFragment newInstance()   {
        GalleryPickerFragment frag = new GalleryPickerFragment();
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventBus();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (GalleryPickerFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement GalleryPickerFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery_picker, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding = FragmentGalleryPickerBinding.bind(getView());
        

        binding.gallery.setHasFixedSize(true);

        List<Uri> images = getImagesFromGallary(getActivity());
        galleryRecyclerViewAdapter = new GalleryRecyclerViewAdapter(getContext(), images);

        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        binding.gallery.setLayoutManager(gridLayoutManager);
        binding.gallery.setAdapter(galleryRecyclerViewAdapter);
        binding.gallery.addItemDecoration(addItemDecoration());

        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) binding.mAppBarContainer.getLayoutParams();
        lp.height = getResources().getDisplayMetrics().widthPixels;
        binding.mAppBarContainer.setLayoutParams(lp);

        displayPreview(0);
        binding.gallery.addOnItemTouchListener(new RecyclerViewOnItemClickListener(getContext(), binding.gallery,
                new RecyclerViewOnItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        displayPreview(position);
                        binding.mAppBarContainer.setExpanded(true, true);
                    }

                    @Override
                    public void onItemLongClick(View v, int position) {

                    }
                }));

        binding.mPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.mAppBarContainer.setExpanded(true, true);
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        Glide.with(getContext()).pauseRequests();
        mRxBus.reset();
    }



    private RecyclerView.ItemDecoration addItemDecoration() {
        return new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view,
                                       RecyclerView parent, RecyclerView.State state) {
                outRect.left = MARGING_GRID;
                outRect.right = MARGING_GRID;
                outRect.bottom = MARGING_GRID;
                if (parent.getChildLayoutPosition(view) >= 0 && parent.getChildLayoutPosition(view) <= 3) {
                    outRect.top = MARGING_GRID;
                }
            }
        };
    }


    private void eventBus() {
        mRxBus.toObserverable()
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        mSession.setFileToUpload(saveBitmap(binding.mPreview.getCroppedImage(),
                                FileUtil.getNewFilePath()));
                        listener.openEditor();
                    }
                });
    }


    private File saveBitmap(Bitmap bitmap, String path) {
        File file = null;
        if (bitmap != null) {
            file = new File(path);
            try {
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(path);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.flush();
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }



    public List<Uri> getImagesFromGallary(Context context) {

        List<Uri> images = new ArrayList<Uri>();


        Cursor imageCursor = null;
        try {
            final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.ImageColumns.ORIENTATION};
            final String orderBy = MediaStore.Images.Media.DATE_ADDED + " DESC";


            imageCursor = BaseApplication.getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
            while (imageCursor.moveToNext()) {
                Uri uri = Uri.parse(imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                images.add(uri);


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (imageCursor != null && !imageCursor.isClosed()) {
                imageCursor.close();
            }
        }


        return images;

    }


    private void displayPreview(int position) {

        Uri uri = galleryRecyclerViewAdapter.getItem(position);

        Glide.with(getContext())
                .load(uri.toString())
                .thumbnail(0.1f)
                .override(350, 350)
                .placeholder(R.drawable.placeholder_media)
                .error(R.drawable.placeholder_error_media)
                .into(binding.mPreview);
    }

}
