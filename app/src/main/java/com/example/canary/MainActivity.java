package com.example.canary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    /* Main Activity components */
    private TextView mEmptyListMessageDisplay;
    private FloatingActionButton mCreateProjectFab;

    /* Popup components */
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmptyListMessageDisplay = findViewById(R.id.tv_empty_list_message_display);

        mCreateProjectFab = findViewById(R.id.fab_create_project);
        mCreateProjectFab.setOnClickListener(v -> {
            Log.d(TAG, "Start new project");
            openPopup();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.project_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            Log.d(TAG, "Search item selected.");
            return true;
        }
        if (id == R.id.action_settings) {
            Log.d(TAG, "Settings item selected.");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openPopup() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);
        mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setElevation(20);

        FrameLayout mFrameLayout = findViewById(R.id.fl);

        Button mCancelBtn = findViewById(R.id.btn_cancel);
        Button mCreateBtn = findViewById(R.id.btn_create);

        Log.d(TAG, String.valueOf(mCancelBtn == null));

        mCancelBtn.setOnClickListener(v -> {
            Toast.makeText()
            mPopupWindow.dismiss();
        });

        mCreateBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Creating new project...", Toast.LENGTH_SHORT).show();
        });

        mPopupWindow.showAtLocation(mFrameLayout, Gravity.CENTER, 0, 0);
    }
}