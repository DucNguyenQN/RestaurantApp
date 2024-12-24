package com.example.btlproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.btlproject.models.LoginResponse;
import com.example.btlproject.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        InnitWigets();
        LoginResponse loginResponse = (LoginResponse) getIntent().getExtras().get("user");
        String tokent = getIntent().getStringExtra("tokent");
        //Toast.makeText(this, userID, Toast.LENGTH_SHORT).show();

        replaceFragment(new HomeFragment(), loginResponse);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frame, new HomeFragment());
//        fragmentTransaction.commit();
        bottomNavigationView.setOnItemSelectedListener(item -> {
                if(item.getItemId() == R.id.itemhome) {
                    replaceFragment(new HomeFragment(),loginResponse);
                } else if (item.getItemId() == R.id.itemcart) {
                    replaceFragment(new CartFragment(),loginResponse);
                }
                else if (item.getItemId() == R.id.itemsearch) {
                    replaceFragment(new MenuFragment(),loginResponse);
                }
                else if (item.getItemId() == R.id.itemmenu) {
                    replaceFragment(new BookingFragment(),loginResponse);
                }
                else if (item.getItemId() == R.id.itemprofile) {
                    replaceFragment(new ProfileFragment(), loginResponse);
                }
            return true;
        });
    }
    private void InnitWigets(){
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }
    private void replaceFragment(Fragment fragment,LoginResponse loginResponse){


        FragmentManager fragmentManager = getSupportFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putSerializable("info",loginResponse);

        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
}