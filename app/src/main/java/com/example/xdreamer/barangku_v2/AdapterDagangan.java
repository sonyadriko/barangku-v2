package com.example.xdreamer.barangku_v2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterDagangan extends RecyclerView.Adapter<AdapterDagangan.DaganganHolder> {

    private List<DataToram> mData;
    public ClickListerner clickListerner;

    public AdapterDagangan(List<DataToram> data) { mData = data; }

    public AdapterDagangan(List<DataToram> data, ClickListerner click) {
        mData = data;
        clickListerner = click;
    }

    @NonNull
    @Override
    public DaganganHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dagangan_item, parent, false);
        return new DaganganHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DaganganHolder holder, final int position) {
        final DataToram dataToram = mData.get(position);

        holder.namaItem.setText(dataToram.getJudul());
        holder.hargaItem.setText(dataToram.getHarga());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ProdukDetail.class);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class DaganganHolder extends RecyclerView.ViewHolder{
        private ImageView picItem;
        private CardView cardView;
        private TextView namaItem, hargaItem;
        private Button btnEdit, btnDelete;

        public DaganganHolder(View dataView){
            super(dataView);
        }

        public void bind(final DataToram dataToram,
                         final ClickListerner clickListerner){
            cardView = itemView.findViewById(R.id.cardViewDaganganItem);
            picItem = itemView.findViewById(R.id.imageItem);
            namaItem = itemView.findViewById(R.id.textJudulProduk);
            hargaItem = itemView.findViewById(R.id.textHargaProduk);
            btnEdit = itemView.findViewById(R.id.btnEditItem);
            btnDelete = itemView.findViewById(R.id.btnHapusItem);

            namaItem.setText(dataToram.getJudul());
            hargaItem.setText(dataToram.getHarga());

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListerner != null ){
                        clickListerner.onClick(dataToram);
                    }
                }
            });
        }
    }

    interface ClickListerner {
        public void onClick(DataToram dataToram);
    }
}
