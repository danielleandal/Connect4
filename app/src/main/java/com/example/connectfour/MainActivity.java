package com.example.connectfour;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.MenuItem;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment selectedFragment;
    private TextView headerTextView; // Declare the TextView for the header

    // called when activity starts
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the TextView for the header
        headerTextView = findViewById(R.id.header_text_view);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        // Set the default fragment when the app starts./
        if (savedInstanceState == null) {  // == null checks if the app is freshly opened
            selectedFragment = new BoardFragment(); // Load default fragment
            headerTextView.setText("Connect Four"); // Set default header text
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, selectedFragment)
                    .commit(); // commit, finalizes the fragment
        }

        // Set up the BottomNavigationView listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Determine which fragment to load based on selected item
                if (item.getItemId() == R.id.nav_connect_four) {
                    selectedFragment = new BoardFragment();
                    headerTextView.setText("Connect Four"); // Change header text
                } else if (item.getItemId() == R.id.nav_options) {
                    selectedFragment = new GameOptionsFragment();
                    headerTextView.setText("Game Options"); // Change header text
                } else {
                    return false; // Return false if no valid item is selected
                }

                // Replace the current fragment with the selected fragment
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_view, selectedFragment)
                            .commit();
                }

                return true;
            }
        });
    }
}