package com.example.canary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;

public class RecordActivity extends AppCompatActivity {

    private static final String TAG = RecordActivity.class.getSimpleName();
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    private FloatingActionButton mAnalyzeAudioFab;

    private Button mStartRecordBtn;
    private Button mStopRecordBtn;
    private Button mPlayRecordBtn;

    private MediaRecorder recorder;
    private MediaPlayer player;

    private String fileName;

    // Permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private final String[] permissions = { Manifest.permission.RECORD_AUDIO };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mAnalyzeAudioFab = findViewById(R.id.fab_analyze_audio);
        mAnalyzeAudioFab.setOnClickListener(v -> {
            // TODO: use AudioDispatcher to start processing audio file
        });

        mStartRecordBtn = findViewById(R.id.btn_start_record);
        mStartRecordBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Starting recording...", Toast.LENGTH_SHORT).show();
            startRecording();
        });

        mStopRecordBtn = findViewById(R.id.btn_stop_record);
        mStopRecordBtn.setEnabled(false);
        mStopRecordBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Stopping recording...", Toast.LENGTH_SHORT).show();
            stopRecording();
        });

        final boolean[] mStartPlaying = {true};
        mPlayRecordBtn = findViewById(R.id.btn_play_record);
        mPlayRecordBtn.setOnClickListener(v -> {
            onPlay(mStartPlaying[0]);
            if (mStartPlaying[0]) {
                mPlayRecordBtn.setText(R.string.stop_playing);
            } else {
                mPlayRecordBtn.setText(R.string.start_playing);
            }
            mStartPlaying[0] = !mStartPlaying[0];


        });

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
    }

    private void onPlay(boolean start) {
        if (start) {
            Toast.makeText(this, "Starting playback...", Toast.LENGTH_LONG).show();
            startPlaying();
        } else {
            Toast.makeText(this, "Stopping playback...", Toast.LENGTH_SHORT).show();
            stopPlaying();
        }
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
        mStartRecordBtn.setEnabled(false);
        mStopRecordBtn.setEnabled(true);

        String path = Project.instance.getDir().getAbsolutePath();
        fileName = path + File.separator + "audio";

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(fileName);
        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException | IllegalStateException e) {
            Log.e(TAG, e.toString());
        }
    }

    private void stopRecording() {
        mStartRecordBtn.setEnabled(true);
        mStopRecordBtn.setEnabled(false);

        recorder.stop();
        recorder.release();
        recorder = null;
    }

    private void startPlaying() {
        mStartRecordBtn.setEnabled(false);
        mStopRecordBtn.setEnabled(false);

        if (fileName == null) {
            Toast.makeText(this, "No recording found.", Toast.LENGTH_SHORT).show();
            mStartRecordBtn.setEnabled(true);
            mStopRecordBtn.setEnabled(true);
            return;
        }

        player = new MediaPlayer();
        try {
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        if (player == null) {
            Toast.makeText(this, "No recording found.", Toast.LENGTH_SHORT).show();
            return;
        }

        mStartRecordBtn.setEnabled(true);
        mStopRecordBtn.setEnabled(true);

        player.release();
        player = null;
    }
}