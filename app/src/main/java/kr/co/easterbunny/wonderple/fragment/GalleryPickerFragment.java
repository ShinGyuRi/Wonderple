package kr.co.easterbunny.wonderple.fragment;


import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import kr.co.easterbunny.wonderple.R;
import kr.co.easterbunny.wonderple.adapter.GalleryRecyclerViewAdapter;
import kr.co.easterbunny.wonderple.databinding.FragmentGalleryPickerBinding;
import kr.co.easterbunny.wonderple.library.BaseApplication;
import kr.co.easterbunny.wonderple.library.ParentFragment;
import kr.co.easterbunny.wonderple.library.util.PermissionModule;
import kr.co.easterbunny.wonderple.listener.RecyclerViewOnItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryPickerFragment extends ParentFragment {


    private FragmentGalleryPickerBinding binding;

    private GridLayoutManager gridLayoutManager;

    public static GalleryRecyclerViewAdapter galleryRecyclerViewAdapter;


    public GalleryPickerFragment() {
        // Required empty public constructor
    }


    public static GalleryPickerFragment newInstance()   {
        GalleryPickerFragment frag = new GalleryPickerFragment();
        return frag;
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

        PermissionModule permissionModule = new PermissionModule(getContext());
        permissionModule.checkPermissions();

        binding.gallery.setHasFixedSize(true);

        List<Uri> images = getImagesFromGallary(getActivity());
        galleryRecyclerViewAdapter = new GalleryRecyclerViewAdapter(getContext(), images);

        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        binding.gallery.setLayoutManager(gridLayoutManager);
        binding.gallery.setAdapter(galleryRecyclerViewAdapter);
        imageLoad(0);
        binding.gallery.addOnItemTouchListener(new RecyclerViewOnItemClickListener(getContext(), binding.gallery,
                new RecyclerViewOnItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        imageLoad(position);
                    }

                    @Override
                    public void onItemLongClick(View v, int position) {

                    }
                }));
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


    private void imageLoad(int position) {

        Uri uri = galleryRecyclerViewAdapter.getItem(position);

        Glide.with(getContext())
                .load(uri.toString())
                .thumbnail(0.1f)
                .into(binding.imgPreview);
    }
}
