package com.example.canary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mEmptyListMessageDisplay;
    private FloatingActionButton mCreateProjectFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmptyListMessageDisplay = (TextView) findViewById(R.id.tv_empty_list_message_display);

        mCreateProjectFab = (FloatingActionButton) findViewById(R.id.fab_create_project);
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
}