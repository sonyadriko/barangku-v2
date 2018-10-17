package com.example.xdreamer.barangku_v2;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xdreamer.barangku_v2.R;
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

public class DaganganItem extends AppCompatActivity {

    private ImageView picItem;
    private TextView textJudul, textHarga;
    private Button edit, hapus;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dagangan_item);

        picItem = findViewById(R.id.imageItem);
        textJudul = findViewById(R.id.textJudulProduk);
        textHarga = findViewById(R.id.textHargaProduk);
        edit = findViewById(R.id.btnEditItem);
        hapus = findViewById(R.id.btnHapusItem);

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        DatabaseReference databaseReference = firebaseDatabase.getReference(auth.getUid());

        storageReference.child(auth.getUid()).child("Dagangan/Produk Pic1").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get()
                        .load(uri)
                        .placeholder(R.drawable.ic_person_black_24dp)
                        .error(R.drawable.ic_perm_identity_black_24dp)
                        .transform(new CircleTransform())
                        .fit()
                        .centerCrop()
                        .into(picItem);
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataToram dataToram = dataSnapshot.getValue(DataToram.class);
                textJudul.setText(dataToram.getJudul());
                textHarga.setText(dataToram.getHarga());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        textJudul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DaganganItem.this, ProdukDetail.class));
            }
        });
    }
}
