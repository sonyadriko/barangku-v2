package com.example.xdreamer.barangku_v2.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xdreamer.barangku_v2.CircleTransform;
import com.example.xdreamer.barangku_v2.MainActivity;
import com.example.xdreamer.barangku_v2.ProfileAkun;
import com.example.xdreamer.barangku_v2.R;
import com.example.xdreamer.barangku_v2.UserProfile;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class AkunFragment extends Fragment implements View.OnClickListener {

    View v;
    ImageView imageView;
    Button keluar;
    RelativeLayout relativeLayout;
    TextView nama;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;


    public AkunFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_akun, container, false);
        relativeLayout = v.findViewById(R.id.layoutAkun);
        nama = v.findViewById(R.id.tvNamaAkun);
        imageView = v.findViewById(R.id.picAkun);
        keluar = v.findViewById(R.id.button_keluar);

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        relativeLayout.setOnClickListener(this);
        keluar.setOnClickListener(this);

        DatabaseReference databaseReference = firebaseDatabase.getReference(auth.getUid());

        storageReference.child(auth.getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get()
                        .load(uri)
                        .placeholder(R.drawable.ic_person_black_24dp)
                        .error(R.drawable.ic_perm_identity_black_24dp)
                        .transform(new CircleTransform())
                        .fit()
                        .centerCrop()
                        .into(imageView);
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                nama.setText(userProfile.getUserNama());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layoutAkun){
            startActivity(new Intent(getActivity(), ProfileAkun.class));
        } if (v.getId() == R.id.button_keluar){
            auth.signOut();
            startActivity(new Intent(getActivity(), MainActivity.class));
        }

    }
}
