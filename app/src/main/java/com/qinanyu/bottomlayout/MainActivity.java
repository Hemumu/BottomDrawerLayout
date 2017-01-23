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

    }
}
