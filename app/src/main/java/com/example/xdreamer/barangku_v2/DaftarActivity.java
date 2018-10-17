package com.example.xdreamer.barangku_v2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DaftarActivity extends AppCompatActivity {

    private EditText userNama, userEmail, password;
    private Button registrasi;
    private FirebaseAuth auth;
    private RadioGroup rGroup;
    private RadioButton pria, wanita;
    String namaText, emailText, passwordText, sloganText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar_activity);

        userNama = findViewById(R.id.etNamaDaftar);
        userEmail = findViewById(R.id.etEmailDaftar);
        password = findViewById(R.id.etPasswordDaftar);
        registrasi = findViewById(R.id.btnRegistrasi);
        rGroup = findViewById(R.id.radioGrup);
        pria = findViewById(R.id.rbPria);
        wanita = findViewById(R.id.rbWanita);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();

        registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    String user_email = userEmail.getText().toString();
                    String user_password = password.getText().toString();


                /* if (TextUtils.isEmpty(namaText)) {
                    userNama.setError("Nama tidak boleh kosong");
                    userNama.requestFocus();
                } else if ( TextUtils.isEmpty(emailText)) {
                    userEmail.setError("Email tidak boleh kosong");
                    userEmail.requestFocus();
                } else if (TextUtils.isEmpty(passwordText)){
                    password.setError("Password tidak boleh kosong");
                    password.requestFocus();
                } else { */
                    auth.createUserWithEmailAndPassword(user_email, user_password)
                            .addOnCompleteListener(DaftarActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task != null) {
                                        SendUserData();
                                        auth.signOut();
                                        //FirebaseUser user = auth.getCurrentUser();
                                        Toast.makeText(DaftarActivity.this, "berhasil", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(DaftarActivity.this, LoginActivity.class));
                                    } else {
                                        Toast.makeText(DaftarActivity.this, "gagal", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private boolean validate() {
        Boolean result = false;

        emailText = userEmail.getText().toString();
        passwordText = password.getText().toString();
        namaText = userNama.getText().toString();

        if (namaText.isEmpty() || emailText.isEmpty() || passwordText.isEmpty() ) {
            Toast.makeText(this, "Masukkan identitas", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }

        return result;
    }

    private void SendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(auth.getUid());

        UserProfile userProfile = new UserProfile(namaText, emailText, sloganText);
        myRef.setValue(userProfile);
    }
}

