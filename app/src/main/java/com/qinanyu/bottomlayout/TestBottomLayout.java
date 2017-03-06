package com.qinanyu.bottomlayout;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Crdeated by helin on 2017/1/19.
 * 仿网易新闻图片新闻详情底部Layout
 */

public class TestBottomLayout extends LinearLayout {
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

    private final int MINHRIGHT = 200;
    private TextView mTitleView;
    private TextView mContentView;

    private int bottomtop = 0;
    private int bottombot = 0;
    private int maxBottom;

    private boolean mIsUpdate = false;
    private TextView mPageNum;
    private LinearLayout mTitleLayout;
    private LinearLayout mOuterLayout;
    private int bottomtop2;
    private int bottombot2;
    private Runnable mRun;

    public TestBottomLayout(Context context) {
        super(context);
        initView(context, null);
    }

    public TestBottomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public TestBottomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    /**
     * 初始化View
     * @param context
     * @param attrs
     */
    private void initView(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.bot_arr);

        String title = ta.getString(R.styleable.bot_arr_bot_title);
        String content = ta.getString(R.styleable.bot_arr_bot_content);
        ta.recycle();

        LayoutInflater inflater = LayoutInflater.from(context);
        mView = inflater.inflate(R.layout.bottom_layout2, this, true);

        mTitleView = (TextView) mView.findViewById(R.id.bot_title_text);
        mContentView = (TextView) mView.findViewById(R.id.bot_content);
        mPageNum = (TextView) mView.findViewById(R.id.page_num);
        mTitleLayout = (LinearLayout) mView.findViewById(R.id.title_layout);

        mOuterLayout= (LinearLayout)mView.findViewById(R.id.outer_layout);
        mContentView.setText(content);
        //设置TextView可以滚动
        mContentView.setMovementMethod(new ScrollingMovementMethod());
        mTitleView.setText(title);
        this.context = context;
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTtitle(String title) {
        mTitleView.setText(title);
    }

    /**
     * 设置页数
     *
     * @param page
     */
    public void setPage(String page) {
        mPageNum.setText(page);
    }

    private int lastTop = 0;


    private boolean onTouch =true;

    /**
     * 设置内容
     *
     * @param content
     */
    public void setContent(final String content) {
        mIsUpdate = true;
        mContentView.setText(content);
        mTitleView.removeCallbacks(mRun);
        onTouch=false;
        //添加全局布局侦听器
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (mIsUpdate) {
                    mIsUpdate = false;
                    int scHeight = mContentView.getHeight();
                    bottomtop = getTop();
                    bottomtop2=mOuterLayout.getTop();
                    bottombot = getBottom();
                    bottombot2=mOuterLayout.getBottom();

                    maxBottom = getBottom();
                    if (scHeight > pixelsToDp(context,100)) {
                        bottomtop = getTop() + (mContentView.getHeight() - MINHRIGHT);
                        bottombot = getBottom() + (mContentView.getHeight() - MINHRIGHT);

                        bottomtop2 = mOuterLayout.getTop() + (mContentView.getHeight() - MINHRIGHT);
                        bottombot2 = mOuterLayout.getBottom() + (mContentView.getHeight() - MINHRIGHT);
                    }
                    mOuterLayout.layout(getLeft(), bottomtop2, getRight(), bottombot2);

                    mTitleView.postDelayed(mRun =new Runnable() {
                        @Override
                        public void run() {
                            layout(getLeft(), bottomtop, getRight(), bottombot);
                            mOuterLayout.layout(getLeft(), 0, getRight(), mOuterLayout.getHeight());
                            onTouch=true;
                        }
                    }, 800);
                }
            }
        });

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

    }

    /**
     * 分发事件
     *
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

        if(!onTouch){
            return true;
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
                //移动View
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

    //dp转px
    public  int dpToPixels(Context c, int dp) {
        return (int)(c.getResources().getDisplayMetrics().density * dp);
    }

    //px转dp
    public  int pixelsToDp(Context c, int pixel) {
        return (int)((float)pixel / c.getResources().getDisplayMetrics().density);
    }

}
