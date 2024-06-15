package com.s22010334.finalproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;
import com.s22010334.finalproject.R;
import com.s22010334.finalproject.View.ViewPageAdapter;

public class HomePageActivity extends BaseActivity {

    ViewPager2 viewPager2;
    ViewPageAdapter viewPageAdapter;
    BottomNavigationView bottomNavigationView;
    private BrightnessManager brightnessManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_home_page);

        //Managing the brightness level
        brightnessManager = BrightnessManager.getInstance(this);
        brightnessManager.start();

        bottomNavigationView = findViewById(R.id.bottamNav);
        viewPager2 = findViewById(R.id.viewPager);
        viewPageAdapter = new ViewPageAdapter(this);
        viewPager2.setAdapter(viewPageAdapter);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.bottom_home:
                        viewPager2.setCurrentItem(0);
                        break;
                    case R.id.bottom_care:
                        viewPager2.setCurrentItem(1);
                        break;
                    case R.id.bottom_orders:
                        viewPager2.setCurrentItem(2);
                        break;
                    case R.id.bottom_profile:
                        viewPager2.setCurrentItem(3);
                        break;
                    default:
                }
                return false;
            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.bottom_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.bottom_care).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.bottom_orders).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.bottom_profile).setChecked(true);
                        break;
                }
                super.onPageSelected(position);
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        brightnessManager.stop();
    }
}