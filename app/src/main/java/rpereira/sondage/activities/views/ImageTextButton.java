package rpereira.sondage.activities.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import rpereira.sondage.R;
import rpereira.sondage.util.ViewUtils;

/**
 * Created by Romain on 10/04/2017.
 */

public class ImageTextButton extends LinearLayout {

    private ImageView imageView;
    private FitTextView textView;

    public ImageTextButton(Context context) {
        super(context);
        this.init(null, 0);
    }

    public ImageTextButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(attrs, 0);
    }

    public ImageTextButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {

        //prepare layout
        this.setOrientation(LinearLayout.HORIZONTAL);

        //instantiate components
        this.imageView = new ImageView(this.getContext());
        this.textView = new FitTextView(this.getContext());
        LayoutParams layoutParams;

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ImageTextButton, defStyle, 0);
        String text = a.getString(R.styleable.ImageTextButton_text);
        Drawable drawable = a.getDrawable(R.styleable.ImageTextButton_image);
        a.recycle();

        //image view setup
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.imageView.setLayoutParams(layoutParams);
        drawable.setCallback(this);
        this.setImage(drawable);

        //textview setup
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        int px = ViewUtils.convertDPToPX(this.getContext(), 32.0f);
        layoutParams.setMargins(0, px, 0, 0);
        this.textView.setLayoutParams(layoutParams);
        this.textView.setGravity(Gravity.CENTER_VERTICAL);
        this.textView.setText(text);

        //add views
        this.addView(this.imageView);
        this.addView(this.textView);
    }

    public void setImage(int resID) {
        this.imageView.setImageResource(resID);
    }

    public void setImage(Drawable res) {
        this.imageView.setImageDrawable(res);
    }

    public void setText(int resID) {
        this.textView.setText(resID);
    }

    public void setText(String str) {
        this.textView.setText(str);
    }

    class FitTextView extends android.support.v7.widget.AppCompatTextView {

        public FitTextView(Context context) {
            this(context, null);
        }

        public FitTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        private void refitText(String text, int textWidth) {
            if (textWidth > 0) {
                float availableWidth = textWidth - this.getPaddingLeft()
                        - this.getPaddingRight();

                TextPaint tp = getPaint();
                Rect rect = new Rect();
                tp.getTextBounds(text, 0, text.length(), rect);
                float size = rect.width();

                if (size > availableWidth)
                    setTextScaleX(availableWidth / size);
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
            int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
            refitText(this.getText().toString(), parentWidth);
            this.setMeasuredDimension(parentWidth, parentHeight);
        }

        @Override
        protected void onTextChanged(final CharSequence text, final int start,
                                     final int before, final int after) {
            refitText(text.toString(), this.getWidth());
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            if (w != oldw) {
                refitText(this.getText().toString(), w);
            }
        }
    }
}
