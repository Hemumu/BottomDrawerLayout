package com.qinanyu.bottomlayout;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
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
    private LinearLayout mBao;
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

        mBao = (LinearLayout)mView.findViewById(R.id.bao_layout2);

        mContentView.setMovementMethod(new ScrollingMovementMethod());

        mTitleView.setText(title);
//        mContentView.setText(content);
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
//        ViewGroup.LayoutParams layout = mScroll.getLayoutParams();
//        layout.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        layout.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        mScroll.setLayoutParams(layout);



        mIsUpdate = true;
        mContentView.setText(content);
        mTitleView.removeCallbacks(mRun);
        onTouch=false;

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (mIsUpdate) {
                    mIsUpdate = false;
                    int scHeight = mContentView.getHeight();
                    bottomtop = getTop();
                    bottomtop2=mBao.getTop();

                    bottombot = getBottom();
                    bottombot2=mBao.getBottom();

                    maxBottom = getBottom();
                    if (scHeight > pixelsToDp(context,100)) {
                        bottomtop = getTop() + (mContentView.getHeight() - MINHRIGHT);
                        bottombot = getBottom() + (mContentView.getHeight() - MINHRIGHT);

                        bottomtop2 = mBao.getTop() + (mContentView.getHeight() - MINHRIGHT);
                        bottombot2 = mBao.getBottom() + (mContentView.getHeight() - MINHRIGHT);
                    }

//                    mBao.removeView(mContentView);
//                    mContentView = new TextView(context);
//                    mContentView.setMaxHeight(200);
//                    mContentView.setMinHeight(100);
//                    mContentView.setTextSize(15);
//                    mContentView.setPadding(5,5,5,5);
//                    mContentView.setTextColor(Color.WHITE);
//                    mContentView.setScrollBarStyle(mContentView.getScrollBarStyle());
//                    mContentView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
//                            LayoutParams.WRAP_CONTENT));
//                    mBao.addView(mContentView);

                    mBao.layout(getLeft(), bottomtop2, getRight(), bottombot2);

                    mTitleView.postDelayed(mRun =new Runnable() {
                        @Override
                        public void run() {
                            layout(getLeft(), bottomtop, getRight(), bottombot);
                            mBao.layout(getLeft(), 0, getRight(), mBao.getHeight());
                            onTouch=true;
                            Log.e("*****", bottomtop + "---" + bottombot);
                        }
                    }, 800);
                }
            }
        });
//        if (scHeight == 0) {
//            return;
//        }
//        maxBottom = getBottom();
//        if (scHeight > MINHRIGHT) {
//            bottomtop = lastTop + (scHeight > 400 ? 200 : scHeight - MINHRIGHT);
//            bottombot = getBottom() + (scHeight > 400 ? 200 : scHeight - MINHRIGHT);
//            lastTop = getTop();
//            Log.e("mScroll2-----", "gettop" + getTop() + " getHeight" + mScroll.getHeight() + "  scHeight():" + scHeight + "  getBottom():" + getBottom());
////                    Log.e("mScroll2-----", "bottomtop :" + mScroll.getHeight() + "  bottombot :" + bottombot + "  getTop():" + getTop() + "  getBottom():" + getBottom());
////                    layout(getLeft(), bottomtop, getRight(), bottombot);
//            if (scHeight > 400) {
//                ViewGroup.LayoutParams layout = mScroll.getLayoutParams();
//                layout.height = MINHRIGHT * 2;
//                layout.width = ViewGroup.LayoutParams.MATCH_PARENT;
//                mScroll.setLayoutParams(layout);
//            } else {
//                ViewGroup.LayoutParams layout = mScroll.getLayoutParams();
//                layout.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//                layout.width = ViewGroup.LayoutParams.MATCH_PARENT;
//                mScroll.setLayoutParams(layout);
//            }
//        } else {
//            lastTop = getTop();
//            Log.e("******", getTop() + "");
//            ViewGroup.LayoutParams layout = mScroll.getLayoutParams();
//            layout.height = MINHRIGHT;
//            layout.width = ViewGroup.LayoutParams.MATCH_PARENT;
//            mScroll.setLayoutParams(layout);
//            bottombot = 0;
//            maxBottom = getBottom();

//        }


    }




    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

//        Log.e("onLayout***", changed + "***" + l + "***" + t + "***" + r + "***" + b);


//        if (mIsUpdate) {
//            mIsUpdate = false;
//            int scHeight = mScroll.getHeight();
//            Log.e("***--***","scHeight: "+scHeight+" getTop"+getTop());
//            bottomtop=getTop();
//            bottombot = getBottom();
//            maxBottom = getBottom();
//            if(scHeight>200){
//                bottomtop = getTop() + (mScroll.getHeight() - MINHRIGHT);
//                bottombot = getBottom() + (mScroll.getHeight() - MINHRIGHT);
//                t=bottomtop;
//                b=bottombot;
//            }
//
//        }

//        if (mIsUpdate) {
//            mIsUpdate = false;
//            final int scHeight = mScroll.getHeight();
//            Log.e("onLayout","****"+scHeight);
//            maxBottom = getBottom();
//            if (scHeight > MINHRIGHT) {
//                if (scHeight > 400) {
//                    ViewGroup.LayoutParams layout = mScroll.getLayoutParams();
//                    layout.height = MINHRIGHT * 2;
//                    layout.width = ViewGroup.LayoutParams.MATCH_PARENT;
//                    mScroll.setLayoutParams(layout);
//                } else {
//                    bottomtop = getTop() + (mScroll.getHeight() - MINHRIGHT);
//                    bottombot = getBottom() + (mScroll.getHeight() - MINHRIGHT);
//
////                    mScroll.postDelayed(new Runnable() {
////                        @Override
////                        public void run() {
////                            layout(getLeft(), bottomtop, getRight(), bottombot);
////                        }
////                    }, 300);
//                }
//            } else {
//                ViewGroup.LayoutParams layout = mScroll.getLayoutParams();
//                layout.height = MINHRIGHT;
//                layout.width = ViewGroup.LayoutParams.MATCH_PARENT;
//                mScroll.setLayoutParams(layout);
//                bottombot = 0;
//                maxBottom = getBottom();
//            }
//        }
    }

    /**
     * 拦截事件
     *
     * @param ev
     * @return
     */

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


    public  int dpToPixels(Context c, int dp) {
        return (int)(c.getResources().getDisplayMetrics().density * dp);
    }

    public  int pixelsToDp(Context c, int pixel) {
        return (int)((float)pixel / c.getResources().getDisplayMetrics().density);
    }

}
