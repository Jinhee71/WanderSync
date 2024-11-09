package com.example.sprintproject.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sprintproject.R;

public class FragmentHandlerActivity extends AppCompatActivity {

    private WSBotNavView botNavView;

    private LogisticsFragment logisticsFragment = new LogisticsFragment();
    private DestinationFragment destinationFragment = new DestinationFragment();
    private DiningFragment diningFragment = new DiningFragment();
    private AccommodationFragment accommodationFragment = new AccommodationFragment();
    private TransportationFragment transportationFragment = new TransportationFragment();
    private CommunityFragment communityFragment = new CommunityFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fragmenthandler);

        botNavView = findViewById(R.id.bottom_nav);
        botNavView.setOnItemSelectedListener(this::onNavigationItemSelected);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainFL,
                logisticsFragment).commit();

    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_logistics) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFL,
                    logisticsFragment).commit();
            return true;
        } else if (item.getItemId() == R.id.nav_destination) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFL,
                    destinationFragment).commit();
            return true;
        } else if (item.getItemId() == R.id.nav_dining) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFL,
                    diningFragment).commit();
            return true;
        } else if (item.getItemId() == R.id.nav_accommodation) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFL,
                    accommodationFragment).commit();
            return true;
        } else if (item.getItemId() == R.id.nav_transportation) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFL,
                    transportationFragment).commit();
            return true;
        } else if (item.getItemId() == R.id.nav_community) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFL,
                    communityFragment).commit();
            return true;
        }

        return false;
    }

    public void onLogisticsDisplayDataClick(View v) {
        logisticsFragment.onDisplayDataClick();
        Toast.makeText(v.getContext(), "Display Data Button Clicked!",
                Toast.LENGTH_SHORT).show();
    }

    public void onLogisticsAddNoteButtonClick(View v) {
        logisticsFragment.onAddNoteButtonClick(v);
        Toast.makeText(v.getContext(), "Add Note Button Clicked!",
                Toast.LENGTH_SHORT).show();
    }

    public void addLogisticsCollaboratorButtonClick(View v) {
        logisticsFragment.addCollaboratorButtonClick(v);
        Toast.makeText(v.getContext(), "Add Collaborator Button Clicked!",
                Toast.LENGTH_SHORT).show();
    }
}