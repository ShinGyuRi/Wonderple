package kr.co.easterbunny.wonderple.model;

import android.content.Context;
import android.graphics.Color;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import kr.co.easterbunny.wonderple.library.util.Toaster;

/**
 * Created by scona on 2017-03-30.
 */

public class Hashtag extends ClickableSpan{

    Context context;
    TextPaint textPaint;
    public Hashtag(Context ctx) {
        super();
        context = ctx;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        textPaint = ds;
        ds.setColor(ds.linkColor);
        ds.bgColor = Color.WHITE;
//        ds.setARGB(255, 30, 144, 255);
    }

    @Override
    public void onClick(View widget) {
        TextView tv = (TextView) widget;
        Spanned s = (Spanned) tv.getText();
        int start = s.getSpanStart(this);
        int end = s.getSpanEnd(this);
        String theWord = s.subSequence(start + 1, end).toString();
        Toaster.shortToast(String.format("Tags for tags: %s", theWord));
    }
}
