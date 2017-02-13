package kr.co.easterbunny.wonderple.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.zomato.photofilters.imageprocessors.Filter;

import java.lang.ref.WeakReference;

import kr.co.easterbunny.wonderple.databinding.ViewEffetItemBinding;
import kr.co.easterbunny.wonderple.listener.EffectItemViewListener;
import kr.co.easterbunny.wonderple.model.Thumbnail;
import kr.co.easterbunny.wonderple.modules.ReboundModule;
import kr.co.easterbunny.wonderple.modules.ReboundModuleDelegate;

/**
 * Created by scona on 2017-02-13.
 */

public class EffectItemView extends LinearLayout implements ReboundModuleDelegate{

    private ViewEffetItemBinding binding;


    private ReboundModule mReboundModule = ReboundModule.getInstance(this);
    private WeakReference<EffectItemViewListener> mWrListener;
    private Filter mFilter;

    public EffectItemView(Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = ViewEffetItemBinding.inflate(inflater, this, true);
    }

    public void bind(Thumbnail thumbnail) {
        mReboundModule.init(binding.mEffectTypeView);
        binding.mEffectName.setText(thumbnail.name);

        // TODO change text color if isSelected

        binding.mEffectTypeView.setImageBitmap(thumbnail.image);
        mFilter = thumbnail.filter;
    }

    public void setListener(EffectItemViewListener listener) {
        this.mWrListener = new WeakReference<>(listener);
    }

    @Override
    public void onTouchActionUp() {
        mWrListener.get().onClickEffectType(mFilter);
    }
}
