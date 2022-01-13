package com.example.canary;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import java.io.IOException;

public class RecordActivity extends AppCompatActivity {

    private static final String TAG = RecordActivity.class.getSimpleName();

    private ImageButton mStartRecordBtn;
    private ImageButton mStopRecordBtn;

    private MediaRecorder recorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mStartRecordBtn = (ImageButton) findViewById(R.id.btn_start_record);
        mStopRecordBtn = (ImageButton) findViewById(R.id.btn_stop_record);
        mStopRecordBtn.setEnabled(false);

        MediaRecorder recorder = new MediaRecorder(this);

        mStartRecordBtn.setOnClickListener(v -> {
            Log.d(TAG, "Start recording.");
            mStartRecordBtn.setEnabled(false);
            mStopRecordBtn.setEnabled(true);

            startRecording();
        });

        mStopRecordBtn.setOnClickListener(v -> {
            Log.d(TAG, "Stop recording.");
            
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        
    }

    private void checkPermissions() {

    }

    private void startRecording() {
    }
}