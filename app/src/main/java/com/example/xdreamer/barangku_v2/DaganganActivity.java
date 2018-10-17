package com.example.xdreamer.barangku_v2;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class DaganganActivity extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private AdapterDagangan adapterDagangan;
    private ImageView picToko;
    private TextView namaToko, sloganToko;
    private Button tambahProduk;
    private RecyclerView recyclerView;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dagangan_activity);

        relativeLayout = findViewById(R.id.rlProfilTokoDagangan);
        picToko = findViewById(R.id.ivPicTokoDagangan);
        namaToko = findViewById(R.id.tvNamaTokoDagangan);
        sloganToko = findViewById(R.id.tvSloganTokoDagangan);
        tambahProduk = findViewById(R.id.btnTambahDagangan);
        recyclerView = findViewById(R.id.recViewDagangan);
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DaganganActivity.this, UpdateProfileToko.class));
            }
        });

        DatabaseReference databaseReference = firebaseDatabase.getReference(auth.getUid());

        storageReference.child(auth.getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get()
                        .load(uri)
                        .transform(new CircleTransform())
                        .fit()
                        .centerCrop()
                        .into(picToko);
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                namaToko.setText(userProfile.getUserNama());
                sloganToko.setText(userProfile.getUserSlogan());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        tambahProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DaganganActivity.this, TambahProduk.class));
            }
        });

        GridLayoutManager glm = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(glm);
        recyclerView.setAdapter(adapterDagangan);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
