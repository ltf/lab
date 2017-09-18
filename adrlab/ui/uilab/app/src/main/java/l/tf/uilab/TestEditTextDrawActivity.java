package l.tf.uilab;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Bundle;
import android.text.*;
import android.text.method.TransformationMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.LineBackgroundSpan;
import android.view.View;
import android.widget.EditText;

/**
 * @author ltf
 * @since 17/9/7, 下午6:34
 */

public class TestEditTextDrawActivity extends Activity implements TextWatcher {
    private int mStrokeColor;
    private String mLastText;
    protected EditText mEditTextContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_edittext_draw_layout);
        mEditTextContent = (EditText) findViewById(R.id.cover_sticker_content);
        mEditTextContent.setTransformationMethod(new Trans());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int selectionStart = mEditTextContent.getSelectionStart();
        int selectionEnd = mEditTextContent.getSelectionEnd();
        String txt = s.toString();
        if (txt.equals(mLastText)) return;
        mLastText = txt;
        SpannableStringBuilder ss = SpannableStringBuilder.valueOf(txt);
        ss.setSpan(new StrokeSpan(mStrokeColor), 0, s.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mEditTextContent.setText(ss);
        mEditTextContent.setSelection(selectionStart, selectionEnd);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    @SuppressLint("ParcelCreator")
    private static class StrokeSpan extends BackgroundColorSpan implements LineBackgroundSpan {
        private Paint mPaint;
        private Rect mRect = new Rect();
        private int mStrokeWidth;

        public StrokeSpan(int color) {
            super(0);
            mStrokeWidth = 20;
            mPaint = new Paint();
            mPaint.setColor(color);
            mPaint.setStrokeWidth(mStrokeWidth);
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaint.setAntiAlias(true);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
        }

        @Override
        public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
            p.getTextBounds(text.toString(), start, end, mRect);
            c.save();
            try {
                int l = left + mStrokeWidth;
                int t = top + mStrokeWidth;
                int r = l + mStrokeWidth + mRect.width();
                int b = t + mStrokeWidth + mRect.height();
                c.translate(-mStrokeWidth, -mStrokeWidth);
                c.clipRect(0, 0, r + mStrokeWidth, b + mStrokeWidth, Region.Op.REPLACE);
                c.drawRect(l, t, r, b, mPaint);
            } finally {
                c.restore();
            }
        }
    }

    private class SpanFactory extends Spannable.Factory {

        @Override
        public Spannable newSpannable(CharSequence source) {
            String txt;
            if (source != null) {
                txt = source.toString();
            } else {
                txt = "";
            }
            SpannableStringBuilder ssb = SpannableStringBuilder.valueOf(txt);
            ssb.setSpan(new StrokeSpan(mStrokeColor), 0, txt.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            return ssb;
        }
    }

    private class EditableFactory extends Editable.Factory {

        @Override
        public Editable newEditable(CharSequence source) {
            String txt;
            if (source != null) {
                txt = source.toString();
            } else {
                txt = "";
            }
            SpannableStringBuilder ssb = SpannableStringBuilder.valueOf(txt);
            ssb.setSpan(new StrokeSpan(mStrokeColor), 0, txt.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            return ssb;
        }
    }

    private class Trans implements TransformationMethod {

        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
           if (source == null) return "";
            String txt = source.toString();
            SpannableStringBuilder ssb = SpannableStringBuilder.valueOf(txt);
            ssb.setSpan(new StrokeSpan(mStrokeColor), 0, txt.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            return source;
        }

        @Override
        public void onFocusChanged(View view, CharSequence sourceText, boolean focused, int direction, Rect previouslyFocusedRect) {

        }
    }
}
