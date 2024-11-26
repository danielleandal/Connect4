package com.example.connectfour;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.annotation.Nullable;

import android.widget.Toast;


/*board class that redirects to activity board when clicked*/
public class BoardFragment extends Fragment {

    final String GAME_STATE = "gameState";
    ConnectFourGame mGame;
    GridLayout mGrid;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layoutView = inflater.inflate(R.layout.fragment_board, container, false);

        // Step 1: Instantiate the ConnectFourGame member variable
        mGame = new ConnectFourGame();

        // Step 2: Initialize the GridLayout from the inflated view
        mGrid = layoutView.findViewById(R.id.grid_layout);

        // Step 3: Check if savedInstanceState is null
        if (savedInstanceState == null) {
            // Start a new game
            startGame();
            System.out.println("Starting a new game");
        } else {
            System.out.println("Restoring previous game state");
            // Step 4: Retrieve and restore the game state if it's not null
            String gameState = savedInstanceState.getString(GAME_STATE);
            if (gameState != null) {
                // Restore the game state
                mGame.setState(gameState);
                // Update the board to reflect the restored game state
                setDisc();
            }
        }

        // Step 5: Set up the grid buttons
        setupGridButtons();

        return layoutView;
    }

    private void onButtonClick(View view, int col) {
        try {
            Log.d("ConnectFour", "Button clicked in column: " + col);

            if (col < 0 || col >= ConnectFourGame.COLS) {
                Log.e("ConnectFour", "Invalid column clicked: " + col);
                return;
            }

            // Attempt to drop a disc in the selected column
            boolean discPlaced = mGame.selectDisc(col);
            if (discPlaced) {
                setDisc(); // Update the board UI

                // Check if the game is over
                if (mGame.isGameOver()) {
                    String winner = (mGame.getCurrentPlayer() == ConnectFourGame.BLUE) ? "RED" : "BLUE";
                    Toast.makeText(getActivity(), "Player " + winner + " wins!", Toast.LENGTH_SHORT).show();
                    startGame(); // Restart the game after a win
                }
            } else {
                Toast.makeText(getActivity(), "Column is full!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("ConnectFour", "Error in onButtonClick: " + e.getMessage(), e);
        }
    }

    private void setupGridButtons() {
        for (int i = 0; i < mGrid.getChildCount(); i++) {
            Button gridButton = (Button) mGrid.getChildAt(i);

            // Calculate the column based on the button's index
            final int col = i % ConnectFourGame.COLS; // 7 columns

            // Set an OnClickListener for each button
            gridButton.setOnClickListener(view -> onButtonClick(view, col));
        }
    }

    private void startGame(){
        mGame.newGame();
        setDisc();
    }

    private void setDisc() {
        // Step c: Iterate through each button in the GridLayout
        for (int buttonIndex = 0; buttonIndex < mGrid.getChildCount(); buttonIndex++) {
            // Step c.i: Instantiate an instance of Button for the current button in the grid
            Button gridButton = (Button) mGrid.getChildAt(buttonIndex);

            // Step c.ii: Calculate the row and column of the current button
            int row = buttonIndex / ConnectFourGame.COLS;
            int col = buttonIndex % ConnectFourGame.COLS;



            // Step c.iii: Instantiate instances of Drawable for the disc colors
            Drawable emptyDisc = DrawableCompat.wrap(ContextCompat.getDrawable(getActivity(), R.drawable.circle_white));
            Drawable blueDisc = DrawableCompat.wrap(ContextCompat.getDrawable(getActivity(), R.drawable.circle_blue));
            Drawable redDisc = DrawableCompat.wrap(ContextCompat.getDrawable(getActivity(), R.drawable.circle_red));


            // Step c.v: Get the value from the game board
            int discValue = mGame.getDisc(row, col);

            // Step c.v.1: Set the background of the button based on the value (EMPTY, BLUE, RED)
            if (discValue == ConnectFourGame.BLUE) {
                gridButton.setBackground(blueDisc);
            } else if (discValue == ConnectFourGame.RED) {
                gridButton.setBackground(redDisc);
            } else {
                gridButton.setBackground(emptyDisc);
            }
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(GAME_STATE, mGame.getState());
    }


}
