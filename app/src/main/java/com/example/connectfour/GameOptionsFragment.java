package com.example.connectfour;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class GameOptionsFragment extends Fragment {
    private static final String PREFS_NAME = "GamePreferences";
    private static final String KEY_SELECTED_LEVEL = "selected_level";
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the fragment_game_options layout
        View view = inflater.inflate(R.layout.fragment_game_options, container, false);

        // Instantiate SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Get the saved level ID and select the corresponding radio button
        int selectedLevelId = sharedPreferences.getInt(KEY_SELECTED_LEVEL, -1); // Default to -1 if no level is saved
        RadioGroup levelGroup = view.findViewById(R.id.radioGroup);

        if (selectedLevelId != -1) {
            RadioButton selectedButton = view.findViewById(selectedLevelId);
            if (selectedButton != null) {
                selectedButton.setChecked(true); // Set the saved level as checked
            }
        }

        // Set a click listener on the radio group to handle selection changes
        levelGroup.setOnCheckedChangeListener((group, checkedId) -> onLevelSelected(checkedId));

        // Return the view object
        return view;
    }

    // Method to handle radio button selection and save it in SharedPreferences
    private void onLevelSelected(int selectedId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_SELECTED_LEVEL, selectedId); // Save the selected level ID
        editor.apply(); // Apply changes to SharedPreferences
    }
}