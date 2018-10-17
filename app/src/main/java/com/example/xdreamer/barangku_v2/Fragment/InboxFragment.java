package com.example.xdreamer.barangku_v2.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.xdreamer.barangku_v2.ChatActivity;
import com.example.xdreamer.barangku_v2.PesanBantuan;
import com.example.xdreamer.barangku_v2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InboxFragment extends Fragment implements View.OnClickListener {

    View v;
    Button btnChat, btnPesanBntn;


    public InboxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_inbox, container, false);
        btnChat = v.findViewById(R.id.btnChat);
        btnPesanBntn = v.findViewById(R.id.btnPesanBantuan);

        btnPesanBntn.setOnClickListener(this);
        btnChat.setOnClickListener(this);



        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnChat){
            startActivity(new Intent(getActivity(), ChatActivity.class));
        } if (v.getId() == R.id.btnPesanBantuan){
            startActivity(new Intent(getActivity(), PesanBantuan.class));
        }
    }
}
