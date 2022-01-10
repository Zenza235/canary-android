package com.example.canary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

public class RecordActivity extends AppCompatActivity {

    private static final String TAG = RecordActivity.class.getSimpleName();

    private ImageButton mStartRecordBtn;
    private ImageButton mStopRecordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mStartRecordBtn = (ImageButton) findViewById(R.id.btn_start_record);
        mStopRecordBtn = (ImageButton) findViewById(R.id.btn_stop_record);
    }
}