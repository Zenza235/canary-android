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

    private Button mRecordBtn;
    private Button mPlaybackBtn;

    private MediaRecorder recorder;
    private MediaPlayer player;

    private String filePath;

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

        final boolean[] startRecording = {true};
        mRecordBtn = findViewById(R.id.btn_record);
        mRecordBtn.setOnClickListener(v -> {
            onRecord(startRecording[0]);
            if (startRecording[0]) {
                mRecordBtn.setText(R.string.stop_record_desc);
            } else {
                mRecordBtn.setText(R.string.start_record_desc);
            }
            startRecording[0] = !startRecording[0];
        });

        final boolean[] startPlaying = {true};
        mPlaybackBtn = findViewById(R.id.btn_playback);
        mPlaybackBtn.setOnClickListener(v -> {
            onPlay(startPlaying[0]);
            if (startPlaying[0]) {
                mPlaybackBtn.setText(R.string.stop_playing);
            } else {
                mPlaybackBtn.setText(R.string.start_playing);
            }
            startPlaying[0] = !startPlaying[0];


        });

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
    }

    private void onRecord(boolean start) {
        if (start) {
            Toast.makeText(this, "Starting recording...", Toast.LENGTH_SHORT).show();
            startRecording();
        } else {
            Toast.makeText(this, "Stopping recording...", Toast.LENGTH_SHORT).show();
            stopRecording();
        }
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
        String path = Project.instance.getDir().getAbsolutePath();
        filePath = path + File.separator + "audio";

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setAudioSamplingRate(44100);
        recorder.setOutputFile(filePath);
        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException | IllegalStateException e) {
            Log.e(TAG, e.toString());
        }
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    private void startPlaying() {
        mRecordBtn.setEnabled(false);

        if (filePath == null) {
            Toast.makeText(this, "Please record something first.", Toast.LENGTH_SHORT).show();
            mRecordBtn.setEnabled(true);
            return;
        }

        player = new MediaPlayer();
        try {
            player.setDataSource(filePath);
            player.prepare();
            player.setVolume(1f, 1f);
            player.start();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mRecordBtn.setEnabled(true);

        if (player == null) {
            Toast.makeText(this, "Please record something first.", Toast.LENGTH_SHORT).show();
            return;
        }

        player.release();
        player = null;
    }
}