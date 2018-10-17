package com.example.xdreamer.barangku_v2.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.xdreamer.barangku_v2.DaganganActivity;
import com.example.xdreamer.barangku_v2.PengaturanToko;
import com.example.xdreamer.barangku_v2.R;
import com.example.xdreamer.barangku_v2.RiwayatPesanan;
import com.example.xdreamer.barangku_v2.SaldoActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class TokokuFragment extends Fragment implements View.OnClickListener{

    View v;
    Button btnDagangan, btnRwytPsn, btnSaldo, btnSetToko;


    public TokokuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_tokoku, container, false);
        btnDagangan = v.findViewById(R.id.btnDagangan);
        btnRwytPsn = v.findViewById(R.id.btnRiwayatPsn);
        btnSaldo = v.findViewById(R.id.btnSaldo);
        btnSetToko = v.findViewById(R.id.btnPengaturanToko);

        btnDagangan.setOnClickListener(this);
        btnRwytPsn.setOnClickListener(this);
        btnSaldo.setOnClickListener(this);
        btnSetToko.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnDagangan){
            startActivity(new Intent(getActivity(), DaganganActivity.class));
        } if (v.getId() == R.id.btnRiwayatPsn) {
            startActivity(new Intent(getActivity(), RiwayatPesanan.class));
        } if (v.getId() == R.id.btnSaldo){
            startActivity(new Intent(getActivity(), SaldoActivity.class));
        } if (v.getId() == R.id.btnPengaturanToko) {
            startActivity(new Intent(getActivity(), PengaturanToko.class));
        }
    }
}
