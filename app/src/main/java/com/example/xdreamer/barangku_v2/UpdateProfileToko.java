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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class UpdateProfileToko extends AppCompatActivity {

    private ImageView uploadFoto;
    private EditText newNama, newSlogan;
    private TextView userEmail;
    private Button buttonUpdate;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    public static int PICK_IMAGE = 123;
    Uri imagePath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                uploadFoto.setImageBitmap(bitmap);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile_toko);

        uploadFoto = findViewById(R.id.ivUpdateFotoTokoDagangan);
        newNama = findViewById(R.id.etUpdateNamaTokoDagangan);
        newSlogan = findViewById(R.id.etUpdateSloganTokoDagangan);
        userEmail = findViewById(R.id.tvUpdateEmailTokoDagangan);
        buttonUpdate = findViewById(R.id.btnSimpanUpdateDagangan);
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        uploadFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "selected image"), PICK_IMAGE);
            }
        });

        final DatabaseReference databaseReference = firebaseDatabase.getReference(auth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                newNama.setText(userProfile.getUserNama());
                userEmail.setText(userProfile.getUserEmail());
                newSlogan.setText(userProfile.getUserSlogan());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UpdateProfileToko.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = newNama.getText().toString();
                String email = userEmail.getText().toString();
                String slogan = newSlogan.getText().toString();

                UserProfile userProfile = new UserProfile(nama, email, slogan);

                databaseReference.setValue(userProfile);

                StorageReference imageReference = storageReference.child(auth.getUid()).child("Images").child("Profile Pic");
                UploadTask uploadTask = imageReference.putFile(imagePath);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateProfileToko.this, "Upload failed!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        Toast.makeText(UpdateProfileToko.this, "Upload succes :)", Toast.LENGTH_SHORT).show();
                    }
                });

                finish();
            }
        });

    }
}
