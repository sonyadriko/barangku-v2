package com.example.xdreamer.barangku_v2;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
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

public class ProdukDetail extends AppCompatActivity {

    private ImageView imageProduk, imageProfil;
    private TextView namaProduk, hargaProduk, stokProduk, deskripsiProduk, namaProfil;
    private Button kirimPesan, masukkanKeranjang;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produk_detail);

        imageProduk = findViewById(R.id.imageDetailItem);
        imageProfil = findViewById(R.id.picProdukDetail);
        namaProduk = findViewById(R.id.namaDetailItem);
        hargaProduk = findViewById(R.id.hargaDetailItem);
        stokProduk = findViewById(R.id.stokDetailItem);
        deskripsiProduk = findViewById(R.id.textDeskDetailProduk);
        namaProfil = findViewById(R.id.tvNamaTokoPD);
        kirimPesan = findViewById(R.id.btnChatDetailProduk);
        masukkanKeranjang = findViewById(R.id.btnMasukkanKeranjangPD);

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        databaseReference = firebaseDatabase.getReference(auth.getUid());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        storageReference.child(auth.getUid()).child("Dagangan/Produk Pic1").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get()
                        .load(uri)
                        .fit()
                        .centerCrop()
                        .into(imageProduk);
            }
        });
        storageReference.child(auth.getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get()
                        .load(uri)
                        .transform(new CircleTransform())
                        .fit()
                        .centerCrop()
                        .into(imageProfil);
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                namaProfil.setText(userProfile.getUserNama());
                DataToram dataToram = dataSnapshot.getValue(DataToram.class);
                namaProduk.setText(dataToram.getJudul());
                hargaProduk.setText(dataToram.getHarga());
                stokProduk.setText(dataToram.getStok());
                deskripsiProduk.setText(dataToram.getDeskripsi());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
