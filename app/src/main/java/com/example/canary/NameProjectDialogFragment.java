package com.example.canary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class NameProjectDialogFragment extends DialogFragment {

    private static final String TAG = NameProjectDialogFragment.class.getSimpleName();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_project_name, null);

        EditText mProjectNameField = view.findViewById(R.id.et_project_name);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(view);
        builder.setNegativeButton(getString(R.string.new_project_deny), (dialog, which) -> {

        });
        builder.setPositiveButton(getString(R.string.new_project_create), (dialog, which) -> {
            if (mProjectNameField.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Please enter a project name.", Toast.LENGTH_SHORT).show();
            } else {
                Intent startRecordActivity = new Intent(getContext(), RecordActivity.class);
                startActivity(startRecordActivity);
                Project.instance.setInstance(mProjectNameField.getText().toString(), requireContext());
            }
        });
        return builder.create();
    }
}