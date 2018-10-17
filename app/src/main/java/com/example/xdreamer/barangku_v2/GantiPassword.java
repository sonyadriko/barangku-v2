package com.example.xdreamer.barangku_v2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GantiPassword extends AppCompatActivity {

    private FirebaseUser firebaseUser;
    private FirebaseAuth auth;
    private EditText newPass;
    private Button simpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ganti_password);

        newPass = findViewById(R.id.etNewPass);
        simpan = findViewById(R.id.buttonGantiPass);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userPasswordNew = newPass.getText().toString();
                firebaseUser.updatePassword(userPasswordNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(GantiPassword.this, "password changed", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(GantiPassword.this, "failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}
