package com.example.canary;

import androidx.activity.result.ActivityResultCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import java.io.IOException;

public class RecordActivity extends AppCompatActivity {

    private static final String TAG = RecordActivity.class.getSimpleName();
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    private ImageButton mStartRecordBtn;
    private ImageButton mStopRecordBtn;

    private MediaRecorder recorder;

    // Permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = { Manifest.permission.RECORD_AUDIO };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mStartRecordBtn = (ImageButton) findViewById(R.id.btn_start_record);
        mStopRecordBtn = (ImageButton) findViewById(R.id.btn_stop_record);
        mStopRecordBtn.setEnabled(false);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        mStartRecordBtn.setOnClickListener(v -> {
            Log.d(TAG, "Start recording.");
            mStartRecordBtn.setEnabled(false);
            mStopRecordBtn.setEnabled(true);
            startRecording();
        });

        mStopRecordBtn.setOnClickListener(v -> {
            Log.d(TAG, "Stop recording.");
            mStartRecordBtn.setEnabled(true);
            mStopRecordBtn.setEnabled(false);
            stopRecording();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted) finish();
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.OGG);
        recorder.setOutputFile(getFilesDir());
        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
        recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }
}