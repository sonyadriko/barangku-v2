package com.example.xdreamer.barangku_v2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class NewProdukToram extends AppCompatActivity {

    private ImageView image1, image2, image3;
    private EditText namaItem, hargaItem, stokItem, deskItem;
    private Button simpan;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    public static int PICK_IMAGE = 123;
    public static int PICK_IMAGE1 = 234;
    public static int PICK_IMAGE2 = 345;
    String nama, harga, stok, desk;
    Uri imagePath, imagePath2, imagePath3;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                image1.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PICK_IMAGE1 && resultCode == RESULT_OK && data.getData() != null) {
            imagePath2 = data.getData();
            try {
                Bitmap bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath2);
                image2.setImageBitmap(bitmap2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PICK_IMAGE2 && resultCode == RESULT_OK && data.getData() != null) {
            imagePath3 = data.getData();
            try {
                Bitmap bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath3);
                image3.setImageBitmap(bitmap3);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.new_produk_toram);

            image1 = findViewById(R.id.ivToram1);
            image2 = findViewById(R.id.ivToram2);
            image3 = findViewById(R.id.ivToram3);
            namaItem = findViewById(R.id.etNamaToram);
            hargaItem = findViewById(R.id.etHargaToram);
            stokItem = findViewById(R.id.etStokToram);
            deskItem = findViewById(R.id.etDeskToram);
            simpan = findViewById(R.id.btnSimpanToram);

            auth = FirebaseAuth.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseStorage = FirebaseStorage.getInstance();
            storageReference = firebaseStorage.getReference();

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            image1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "selected image"), PICK_IMAGE);
                }
            });
            image2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "selected image"), PICK_IMAGE1);
                }
            });
            image3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "selected image"), PICK_IMAGE2);
                }
            });
            simpan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validate()) {
                        sendUserData();
                        Toast.makeText(NewProdukToram.this, "upload succes", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(NewProdukToram.this, "gagal", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    private void sendUserData() {
        DatabaseReference myRef = firebaseDatabase.getReference(auth.getUid());
        DataToram dataToram = new DataToram(nama, harga, stok, desk);
        myRef.setValue(dataToram);
        StorageReference imageReference = storageReference.child(auth.getUid()).child("Dagangan").child("Produk Pic1");
        UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NewProdukToram.this, "Upload failed!", Toast.LENGTH_SHORT).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Toast.makeText(NewProdukToram.this, "Upload succes :)", Toast.LENGTH_SHORT).show();
            }
        });
        StorageReference image2 = storageReference.child(auth.getUid()).child("Dagangan").child("Produk Pic2");
        UploadTask upload2 = image2.putFile(imagePath2);
        upload2.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NewProdukToram.this, "Upload failed!", Toast.LENGTH_SHORT).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Toast.makeText(NewProdukToram.this, "Upload succes :)", Toast.LENGTH_SHORT).show();
            }
        });
        StorageReference image3 = storageReference.child(auth.getUid()).child("Dagangan").child("Produk Pic3");
        UploadTask upload3 = image3.putFile(imagePath3);
        upload3.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NewProdukToram.this, "Upload failed!", Toast.LENGTH_SHORT).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Toast.makeText(NewProdukToram.this, "Upload succes :)", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean validate () {
            Boolean result = false;

            nama = namaItem.getText().toString();
            harga = hargaItem.getText().toString();
            stok = stokItem.getText().toString();
            desk = deskItem.getText().toString();

            if (nama.isEmpty() || harga.isEmpty() || stok.isEmpty() || desk.isEmpty() || imagePath == null){
                Toast.makeText(this, "Masukkan detail produk", Toast.LENGTH_SHORT).show();
            } else {
                result = true;
            }

            return result;
        }
    }
