package com.example.sprintproject.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sprintproject.R;
import com.example.sprintproject.WSBotNavView;

public class FragmentHandlerActivity extends AppCompatActivity {

    WSBotNavView botNavView;

    LogisticsFragment logisticsFragment = new LogisticsFragment();
    DestinationFragment destinationFragment = new DestinationFragment();
    DiningFragment diningFragment = new DiningFragment();
    AccommodationFragment accommodationFragment = new AccommodationFragment();
    TransportationFragment transportationFragment = new TransportationFragment();
    CommunityFragment communityFragment = new CommunityFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fragmenthandler);

        botNavView = findViewById(R.id.bottom_nav);
        botNavView.setOnItemSelectedListener(this::onNavigationItemSelected);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainFL, logisticsFragment).commit();

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_logistics) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFL, logisticsFragment).commit();
            return true;
        } else if (item.getItemId() == R.id.nav_destination) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFL, destinationFragment).commit();
            return true;
        } else if (item.getItemId() == R.id.nav_dining) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFL, diningFragment).commit();
            return true;
        } else if (item.getItemId() == R.id.nav_accommodation) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFL, accommodationFragment).commit();
            return true;
        } else if (item.getItemId() == R.id.nav_transportation) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFL, transportationFragment).commit();
            return true;
        } else if (item.getItemId() == R.id.nav_community) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFL, communityFragment).commit();
            return true;
        }

        return false;
    }
}