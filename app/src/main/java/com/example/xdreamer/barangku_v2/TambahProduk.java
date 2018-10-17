package com.example.xdreamer.barangku_v2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TambahProduk extends AppCompatActivity {

    private Button ml, toram, pubg, hayday;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_produk);

        ml = findViewById(R.id.button_newProdukMl);
        toram = findViewById(R.id.button_newProdukToram);
        pubg = findViewById(R.id.button_newProdukPUBG);
        hayday = findViewById(R.id.button_newProdukHayDay);
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        toram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TambahProduk.this, NewProdukToram.class));
            }
        });
    }
}
