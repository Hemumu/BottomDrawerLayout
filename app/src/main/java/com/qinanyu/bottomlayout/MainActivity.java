package com.qinanyu.bottomlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private BottomLayout mBottomLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomLayout = (BottomLayout)findViewById(R.id.bottom_layout);
        mBottomLayout.setTtitle("这是一个普通话新闻个普通话新闻个普通话新闻的标题");

        mBottomLayout.setContent("zheshi yi shh hs卡号升到发货发是一个普通话新闻" +
                "个普通话新闻个普通话是一个普通话新闻个普通话新闻个普通话是一个普通话新闻个普通话" +
                "号升到发是安徽我是安徽卡货发货我是安徽卡号升到发是安徽我是安徽卡货发货我是安徽卡号升到发是安徽" +
                "号升到发是安徽我是安徽卡货发货我是安徽卡号升到发是安徽我是安徽卡货发货我是安徽卡号升到发是安徽" +
                "号升到发是安徽我是安徽卡货发货我是安徽卡号升到发是安徽我是安徽卡货发货我是安徽卡号升到发是安徽" +
                "我是安徽卡货发货我是安徽卡号升到发是安徽我是安徽卡货发货我是安徽卡号升到发是安徽我是安徽卡货发货" +
                "我是安徽卡号升到发是安徽我是安徽卡号就我  ");
        ((TextView)findViewById(R.id.test_tev)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomLayout.setContent("zheshi yi shh hsj 上升号就我 就升到发货我是安徽卡号就我 就升到发货我是安徽卡号就我 就就我 就是一啊婚纱哈啊哈哈啊 ");
                mBottomLayout.invalidate();
            }
        });
    }
}
