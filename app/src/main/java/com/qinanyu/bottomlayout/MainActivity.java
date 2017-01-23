package com.qinanyu.bottomlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private BottomLayout mBottomLayout;
    int y = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomLayout = (BottomLayout)findViewById(R.id.bottom_layout);
        mBottomLayout.setTtitle("这是一个普通话新闻的标题");
        mBottomLayout.setContent("zheshi yi shh hsj 上升到发货我是安徽卡号升到发货我是安徽卡号就我 就升到发货我是安徽卡号就我 就升到发货我是安徽卡号就我 就升到发货我是安徽卡号就我 就升到发货我是安徽卡号就我 就升到发货我是安徽卡号就我 就升到发货我是安徽卡号就我 就升到发货我是安徽卡号就我 就升到发货我是安徽卡号就我 就升到发货我是安徽卡号就我 就升到发货我是安徽卡号就我 就升到发货我是安徽卡号就我 就升到发货我是安徽卡号就我 就升到发货我是安徽卡号就我 就升到发货我是安徽卡号就我 就升到发货我是安徽卡号就我 就升到发货我是安徽卡号就我 就就我 就是一啊婚纱哈啊哈哈啊 ");
    }
}
