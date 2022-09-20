package com.deliveryfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DeliveryFood.lib.Model.FoodModel;
import com.deliveryfood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapterRecyclerview extends RecyclerView.Adapter<ProductAdapterRecyclerview.HangDTViewHolder> {

    Context context;
    int layout;
    private List<FoodModel.Data> mListener;
    OnNoteListener onclink;

    public ProductAdapterRecyclerview(Context context, int layout, List<FoodModel.Data> mListener, OnNoteListener onclink) {
        this.context = context;
        this.layout = layout;
        this.mListener = mListener;
        this.onclink = onclink;
    }

    public void setList(List<FoodModel.Data> mListener) {
        this.mListener = mListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HangDTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new HangDTViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HangDTViewHolder holder, int position) {
        FoodModel.Data Food = mListener.get(position);
        if (Food == null)
            return;
        else {
            Picasso.get().load(Food.getPicture()).into(holder.Img);
            holder.Name.setText(Food.getName_Food());
            holder.gia.setText(Integer.toString((int) Food.getPrice()) + "đ");
        }
        holder.Rcv_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclink.onNoteClick(Food);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListener != null)
            return mListener.size();
        return 0;
    }

    public class HangDTViewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        ImageView Img;
        TextView MoTa;
        TextView gia;
        LinearLayout Rcv_food;

        public HangDTViewHolder(@NonNull View itemView) {
            super(itemView);
            Img = (ImageView) itemView.findViewById(R.id.img_sanpham);
            gia = (TextView) itemView.findViewById(R.id.gia_sanpham);
            Name = (TextView) itemView.findViewById(R.id.ten_sanpham);
            MoTa = (TextView) itemView.findViewById(R.id.mota_sanpham);
            Rcv_food = itemView.findViewById(R.id.Rcv_food);
        }
    }

    public interface OnNoteListener {
        void onNoteClick(FoodModel.Data position);
    }
}
