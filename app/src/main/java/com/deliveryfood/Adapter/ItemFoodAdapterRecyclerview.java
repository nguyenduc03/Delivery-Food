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

public class ItemFoodAdapterRecyclerview extends RecyclerView.Adapter<ItemFoodAdapterRecyclerview.HangDTViewHolder>{

    Context context;
    int layout;
    private List<FoodModel.Data>  List;
    OnNoteListener onclick;

    public ItemFoodAdapterRecyclerview(Context context, int layout, List<FoodModel.Data>  List, OnNoteListener onclick) {
        this.context = context;
        this.layout = layout;
        this.List = List;
        this.onclick = onclick;
    }

    public void setList(List<FoodModel.Data>  List) {
        this.List = List;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HangDTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new HangDTViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HangDTViewHolder holder, int position) {
        FoodModel.Data Food = List.get(position);
        if(Food == null)
            return;
        else
        {
            Picasso.get().load(Food.getPicture()).into(holder.Img);
            holder.Name.setText(Food.getName_Food());
            holder.gia.setText(Float.toString((float)Food.getPrice()));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onclick.onNoteClick(Food);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(List!= null)
            return List.size();
        return 0;
    }

    public   class  HangDTViewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        ImageView Img;
        TextView gia;
        LinearLayout item_food;

        public HangDTViewHolder(@NonNull View itemView) {
            super(itemView);
            Img = (ImageView) itemView.findViewById(R.id.img_sanpham);
            gia = (TextView) itemView.findViewById(R.id.gia_sanpham);
            Name = (TextView) itemView.findViewById(R.id.ten_sanpham);
            item_food = itemView.findViewById(R.id.gia_sanpham);
        }
    }

    public interface OnNoteListener{
        void onNoteClick(FoodModel.Data position);
    }
}
