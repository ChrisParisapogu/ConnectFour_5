package com.example.connectfour;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BoardFragment extends Fragment {
    private GameView gameView;
    private Button resetButton;
    private static TextView statusText;
    public static boolean isPlayer1Turn = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board, container, false);
        gameView = view.findViewById(R.id.gameView);
        resetButton = view.findViewById(R.id.resetButton);
        statusText = view.findViewById(R.id.statusText);

        resetButton.setOnClickListener(v -> resetGame());
        updateStatus();


        return view;
    }

    private void resetGame() {
        gameView.resetGame();
        isPlayer1Turn = true;
        updateStatus();
    }

    public static void updateStatus() {
        statusText.setText(isPlayer1Turn ? "Player 1's Turn" : "Player 2's Turn");
    }
}
