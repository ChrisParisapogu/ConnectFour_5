package com.example.connectfour;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GameOptionsFragment extends Fragment {
    private RadioGroup difficultyRadioGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_options, container, false);

        difficultyRadioGroup = view.findViewById(R.id.radioGroupDifficulty);
        SharedPreferences prefs = requireActivity().getSharedPreferences("GamePrefs", getContext().MODE_PRIVATE);

        // Load the selected level
        int selectedLevel = prefs.getInt("selectedLevel", R.id.easyRadioButton);
        ((RadioButton) view.findViewById(selectedLevel)).setChecked(true);

        // Set up listener for level selection
        difficultyRadioGroup.setOnCheckedChangeListener((group, checkedId) -> onLevelSelected(checkedId, prefs));

        return view;
    }

    private void onLevelSelected(int checkedId, SharedPreferences prefs) {
        // Save the selected level to SharedPreferences
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("selectedLevel", checkedId);
        editor.apply();
    }
}
