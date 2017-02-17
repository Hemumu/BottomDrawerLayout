package com.qinanyu.bottomlayout;

/**
 * Created by Axx on 2017/2/16.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.bskim.maxheightscrollview.R;
import com.bskim.maxheightscrollview.UIUtils;

public class MaxHeightScrollView extends ScrollView {

    private int maxHeight;
    private final int defaultHeight = 200;

    public MaxHeightScrollView(Context context) {
        super(context);
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MaxHeightScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightScrollView);

        try {
            setMaxHeight(a.getDimensionPixelSize(R.styleable.MaxHeightScrollView_maxHeight, defaultHeight));
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void setMaxHeightDp(int maxHeightDp) {
        this.maxHeight = UIUtils.dpToPixels(getContext(), maxHeightDp);
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getMaxHeightDp() {
        return UIUtils.pixelsToDp(getContext(), maxHeight);
    }
}