package com.example.xdreamer.barangku_v2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    private Button keMasuk, keDaftar;
    private static final int RC_SIGN_IN = 234;
    private static final String TAG = "barangku";
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keMasuk = findViewById(R.id.btnToMasuk);
        keDaftar = findViewById(R.id.btnToDaftar);

        keDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DaftarActivity.class));
            }
        });

        keMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });


       /* email = findViewById(R.id.etEmailLogin);
        password = findViewById(R.id.etPasswordLogin);
        login = findViewById(R.id.btnLogin);
        lupaPassword = findViewById(R.id.tvLupaPassword);
        loginToRegis = findViewById(R.id.tvLoginToDaftar);

        auth = FirebaseAuth.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();

                if (TextUtils.isEmpty(emailText)) {
                    email.setError("Email tidak boleh kosong");
                    email.requestFocus();
                } else if (TextUtils.isEmpty(passwordText)) {
                    password.setError("Password tidak boleh kosong");
                    password.requestFocus();
                } else {
                    auth.signInWithEmailAndPassword(emailText, passwordText)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        FirebaseUser user = auth.getCurrentUser();

                                        if (user != null){
                                            Intent i = new Intent(MainActivity.this, MenuActivity.class);
                                            startActivity(i);
                                            finish();
                                        } else {
                                            Toast.makeText(MainActivity.this, "login gagal", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(MainActivity.this, "login gagal", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        loginToRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, DaftarActivity.class));
            }
        });
        lupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ResetPassword.class));
            }
        }); */

        auth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        findViewById(R.id.button_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);

            firebaseAuthWithGoogle(account);
        } catch (ApiException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle: " + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "SignInWithCredential:succes");
                            FirebaseUser user = auth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Berhasil Masuk", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.w(TAG, "SignInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Gagal Masuk", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signIn() {
        //google sign intent
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        //memulai activity
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}
