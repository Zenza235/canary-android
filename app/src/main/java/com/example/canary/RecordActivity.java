package com.example.canary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

public class RecordActivity extends AppCompatActivity {

    private static final String TAG = RecordActivity.class.getSimpleName();
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    private ImageButton mStartRecordBtn;
    private ImageButton mStopRecordBtn;

    private MediaRecorder recorder;

    // Permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private final String[] permissions = { Manifest.permission.RECORD_AUDIO };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mStartRecordBtn = findViewById(R.id.btn_start_record);
        mStopRecordBtn = findViewById(R.id.btn_stop_record);
        mStopRecordBtn.setEnabled(false);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        mStartRecordBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Starting recording...", Toast.LENGTH_SHORT).show();
            mStartRecordBtn.setEnabled(false);
            mStopRecordBtn.setEnabled(true);
            startRecording();
        });

        mStopRecordBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Stopping recording...", Toast.LENGTH_SHORT).show();
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
        // TODO: test if works, set up tarsos
        // Get output file, then run analysis on separate thread
        // Get result as .pdf or .musicxml
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