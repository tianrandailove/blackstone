package com.kefan.blackstone.ui.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.kefan.blackstone.BaseActivity;
import com.kefan.blackstone.R;

public class CopyRightActivity extends BaseActivity {

    private LinearLayout actionBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_copy_right);
        actionBack= (LinearLayout) findViewById(R.id.activity_copy_right_linear_layout_action_back);
        actionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //overridePendingTransition(R.anim.in,R.anim.out);
            }
        });
    }
}
