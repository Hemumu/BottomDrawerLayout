package com.qinanyu.bottomlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class TestTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_text);
        TextView te = (TextView) findViewById(R.id.test_te);
        te.setMovementMethod(new ScrollingMovementMethod());
    }
}
