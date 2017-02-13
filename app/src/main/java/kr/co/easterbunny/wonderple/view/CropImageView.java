package kr.co.easterbunny.wonderple.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.target.SquaringDrawable;

import kr.co.easterbunny.wonderple.library.util.JSLog;

public class CropImageView extends TouchImageView {

    public CropImageView(Context context) {
        super(context);
    }

    public CropImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Bitmap getCroppedImage() {

        final Drawable drawable = getDrawable();
        JSLog.D("getDrawable:::::::"+drawable.toString(), new Throwable());
        if (drawable == null || !(drawable instanceof Drawable)) {
            JSLog.D("return null", new Throwable());
            return null;
        }

        final float[] matrixValues = new float[9];
        getImageMatrix().getValues(matrixValues);

        final float scaleX = matrixValues[Matrix.MSCALE_X];
        final float scaleY = matrixValues[Matrix.MSCALE_Y];
        final float transX = matrixValues[Matrix.MTRANS_X];
        final float transY = matrixValues[Matrix.MTRANS_Y];

        final float bitmapLeft = (transX < 0) ? Math.abs(transX) : 0;
        final float bitmapTop = (transY < 0) ? Math.abs(transY) : 0;

        final Bitmap originalBitmap = ((GlideBitmapDrawable) drawable.getCurrent()).getBitmap();

        JSLog.D("originalBitmap::::::"+originalBitmap.toString(), new Throwable());

        final float cropX = (bitmapLeft + getLeft()) / scaleX;
        final float cropY = (bitmapTop + getTop()) / scaleY;

        final float cropWidth = Math.min(getWidth() / scaleX, originalBitmap.getWidth() - cropX);
        final float cropHeight = Math.min(getHeight() / scaleY, originalBitmap.getHeight() - cropY);

        JSLog.D("Bitmap::::::"+Bitmap.createBitmap(originalBitmap,
                (int) cropX,
                (int) cropY,
                (int) cropWidth,
                (int) cropHeight).toString(), new Throwable());

        return Bitmap.createBitmap(originalBitmap,
                (int) cropX,
                (int) cropY,
                (int) cropWidth,
                (int) cropHeight);
    }

}