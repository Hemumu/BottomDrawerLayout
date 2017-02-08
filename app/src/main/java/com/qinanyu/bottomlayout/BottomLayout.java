package com.qinanyu.bottomlayout;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by helin on 2017/1/19.
 */

public class BottomLayout extends LinearLayout {
    private View mView;

    private DisplayMetrics displayMetrics;
    private float lastX = 0;
    private float lastY = 0;
    private int screenWidth = 0;
    private int screenHeight = 0;
    private int left;
    private int top;
    private int right;
    private int bottom;
    private boolean isFirst = true;
    private final String TAG = "BottomLayout";
    private Context context;
    private ScrollView mScroll;

    private final int MINHRIGHT = 200;
    private TextView mTitleView;
    private TextView mContentView;

    private int bottomtop = 0;
    private int bottombot = 0;
    private int maxBottom;

    private boolean mIsUpdate = false;

    public BottomLayout(Context context) {
        super(context);
        initView(context, null);
    }

    public BottomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public BottomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.bot_arr);

        String title = ta.getString(R.styleable.bot_arr_bot_title);
        String content = ta.getString(R.styleable.bot_arr_bot_content);
        ta.recycle();

        LayoutInflater inflater = LayoutInflater.from(context);
        mView = inflater.inflate(R.layout.bottom_layout, this, true);
        mScroll = (ScrollView) mView.findViewById(R.id.scroll_view);
        mTitleView = (TextView) mView.findViewById(R.id.bot_title_text);
        mContentView = (TextView) mView.findViewById(R.id.bot_content);

        mTitleView.setText(title);
        mContentView.setText(content);
        this.context = context;
    }

    public void setTtitle(String title) {
        mTitleView.setText(title);
    }

    public void setContent(String content) {
        ViewGroup.LayoutParams layout = mScroll.getLayoutParams();
        layout.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layout.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mScroll.setLayoutParams(layout);
        mContentView.setText(content);
        mIsUpdate = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int lastHeight;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mIsUpdate) {
            mIsUpdate = false;
            final int scHeight = mScroll.getHeight();
            lastHeight = scHeight;
            maxBottom = getBottom();
            if (scHeight > MINHRIGHT) {
                setVisibility(View.INVISIBLE);
                if (scHeight > 400) {
                    ViewGroup.LayoutParams layout = mScroll.getLayoutParams();
                    layout.height = MINHRIGHT * 2;
                    layout.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    mScroll.setLayoutParams(layout);
                    invalidate();
                }
                mScroll.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bottomtop = getTop() + (mScroll.getHeight() - MINHRIGHT);
                        bottombot = getBottom() + (mScroll.getHeight() - MINHRIGHT);
                        layout(getLeft(), bottomtop, getRight(), bottombot);
                        setVisibility(View.VISIBLE);
//                        Log.e("postDelayed", mScroll.getHeight() + "****" + bottombot);
                    }
                }, 200);
            }else{
                bottombot=0;
                maxBottom = getBottom();
            }
            Log.e("bianjjie", bottombot + "****" + maxBottom);
        }
    }


    /**
     * 拦截事件
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 分发事件
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        if (isFirst) {
            // 得到屏幕的宽
            displayMetrics = getResources().getDisplayMetrics();
            screenWidth = displayMetrics.widthPixels;
            // 得到标题栏和状态栏的高度
            Rect rect = new Rect();
            Window window = ((Activity) context).getWindow();
            int statusBarHeight = rect.top;
            int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
            int titleBarHeight = contentViewTop - statusBarHeight;
            // 得到屏幕的高
            screenHeight = displayMetrics.heightPixels - (statusBarHeight + titleBarHeight);
            isFirst = false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getRawX();
                lastY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                //移动的距离
                float distanceX = event.getRawX() - lastX;
                float distanceY = event.getRawY() - lastY;
                //移动后控件的坐标
                left = (int) (getLeft() + distanceX);
                top = (int) (getTop() + distanceY);
                right = (int) (getRight() + distanceX);
                bottom = (int) (getBottom() + distanceY);
                //处理拖出屏幕的情况
                if (left < 0) {
                    left = 0;
                    right = getWidth();
                }
                if (right > screenWidth) {
                    right = screenWidth;
                    left = screenWidth - getWidth();
                }
                if (top < 0) {
                    top = 0;
                    bottom = getHeight();
                }
                if (bottom > bottombot) {
                    bottom = bottombot;
                    top = bottombot - getHeight();
                }

                if (bottom < maxBottom) {
                    bottom = maxBottom;
                    top = maxBottom - getHeight();
                }

                layout(getLeft(), top, getRight(), bottom);
                lastX = event.getRawX();
                lastY = event.getRawY();

                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

}
