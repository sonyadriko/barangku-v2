package com.example.xdreamer.barangku_v2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xdreamer.barangku_v2.Fragment.AkunFragment;
import com.example.xdreamer.barangku_v2.Fragment.HomeFragment;
import com.example.xdreamer.barangku_v2.Fragment.InboxFragment;
import com.example.xdreamer.barangku_v2.Fragment.KeranjangFragment;
import com.example.xdreamer.barangku_v2.Fragment.TokokuFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuActivity extends AppCompatActivity {

    private BottomNavigationView mBottomView;
    private FrameLayout frameLayout;

    private AkunFragment akunFragment;
    private HomeFragment homeFragment;
    private InboxFragment inboxFragment;
    private KeranjangFragment keranjangFragment;
    private TokokuFragment tokokuFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mBottomView = findViewById(R.id.bottomNav);
        frameLayout = findViewById(R.id.frame_container);

        akunFragment = new AkunFragment();
        homeFragment = new HomeFragment();
        inboxFragment = new InboxFragment();
        keranjangFragment = new KeranjangFragment();
        tokokuFragment = new TokokuFragment();

        setFragment(homeFragment);

        mBottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_home:
                        setFragment(homeFragment);
                        return true;
                    case R.id.nav_tokoku:
                        setFragment(tokokuFragment);
                        return true;
                    case R.id.nav_inbox:
                        setFragment(inboxFragment);
                        return true;
                    case R.id.nav_keranjang:
                        setFragment(keranjangFragment);
                        return true;
                    case R.id.nav_akun:
                        setFragment(akunFragment);
                        return true;
                }
                return false;
            }
        });


    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_container, fragment);
        ft.commit();
    }
}
