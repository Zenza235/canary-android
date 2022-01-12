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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mStartRecordBtn = (ImageButton) findViewById(R.id.btn_start_record);
        mStopRecordBtn = (ImageButton) findViewById(R.id.btn_stop_record);

        MediaRecorder recorder = new MediaRecorder();

        mStartRecordBtn.setOnClickListener(v -> {
            Log.d(TAG, "Start recording.");
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.OGG);
            recorder.setOutputFile(getFilesDir());
            try {
                recorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            recorder.start();
        });

        mStopRecordBtn.setOnClickListener(v -> {
            Log.d(TAG, "Stop recording.");
            recorder.stop();
            recorder.release();
        });
    }
}