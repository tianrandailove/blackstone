package com.hdu.myship.blackstone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import org.litepal.crud.DataSupport;

import java.util.List;

import database.Record;

public class AddNotesActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG="AddNotesActivity";

    private EditText notes;

    private LinearLayout actionBack;
    private int speciesId;

    private List<Record> records;
    private Record record;

    private Intent data;
    private int groupPosition;
    private int childPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_notes);
        initData();
        initView();
        initEvents();

    }

    private void initData() {

        groupPosition=getIntent().getIntExtra("groupPosition",0);
        childPosition=getIntent().getIntExtra("childPosition",0);
        Log.d(TAG, "initData: "+groupPosition+":"+childPosition);
        data=new Intent();
    }

    private void initView() {
        notes= (EditText) findViewById(R.id.add_notes_editText_notes);
        actionBack= (LinearLayout) findViewById(R.id.activity_add_notes_linear_layout_action_back);
        notes.setText(AddRecordFragment.records.get(groupPosition).get(childPosition).getRemark());
        notes.setSelection(notes.getText().length());
    }

    private void initEvents() {
        actionBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.add_notes_editText_notes:
                break;

            case R.id.activity_add_notes_linear_layout_action_back:actionBack();
                break;
        }
    }



    private void actionBack() {
        String notesContent=notes.getText().toString();
        Log.d(TAG, "actionBack: "+notesContent);
        if(!notesContent.equals(""))
        {
            AddRecordFragment.records.get(groupPosition).get(childPosition).setRemark(notesContent);
            AddRecordFragment.records.get(groupPosition).get(childPosition).setRemarkIsNull(false);
            AddRecordFragment.records.get(groupPosition).get(childPosition).save();
           // data.putExtra("Remark",notesContent);
            //data.putExtra("isNull",false);
            //this.setResult(2,data);
            this.finish();
        }else
        {
            AddRecordFragment.records.get(groupPosition).get(childPosition).setRemark(notesContent);
            AddRecordFragment.records.get(groupPosition).get(childPosition).setRemarkIsNull(true);
            AddRecordFragment.records.get(groupPosition).get(childPosition).save();
            this.finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getKeyCode()==KeyEvent.KEYCODE_BACK)
        {
            actionBack();
        }
        return super.onKeyDown(keyCode, event);
    }
}
