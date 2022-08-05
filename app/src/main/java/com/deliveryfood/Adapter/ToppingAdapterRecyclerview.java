package com.deliveryfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib.Model.ToppingModel;
import com.example.sanpham.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ToppingAdapterRecyclerview extends RecyclerView.Adapter<ToppingAdapterRecyclerview.HangDTViewHolder>{

    Context context;
    int layout;
    private List<ToppingModel.Data> List;

    public ToppingAdapterRecyclerview(Context context, int layout, List<ToppingModel.Data> List) {
        this.context = context;
        this.layout = layout;
        this.List = List;
    }

    public void setList(List<ToppingModel.Data> List) {
        this.List = List;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HangDTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topping_item,parent,false);
        return new HangDTViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HangDTViewHolder holder, int position) {
        ToppingModel.Data Topping = List.get(position);
        if(Topping == null)
            return;
        else
        {
            Picasso.get().load(Topping.getImg()).into(holder.Img);
            holder.Name.setText(Topping.getName_Topping());
            holder.gia.setText(Float.toString((float)Topping.getPrice()));
        }
        holder.itemView.setOnClickListener(view -> {
            List.get(position);
            if(holder.cb_topping.isChecked())
            {
                holder.cb_topping.setChecked(false);

            }
            else {
                holder.cb_topping.setChecked(true);

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
        CheckBox cb_topping;
        public HangDTViewHolder(@NonNull View itemView) {
            super(itemView);
            Img = (ImageView) itemView.findViewById(R.id.img_topping);
            gia = (TextView) itemView.findViewById(R.id.txt_PriceTopping);
            Name = (TextView) itemView.findViewById(R.id.txt_NameTopping);
            cb_topping = itemView.findViewById(R.id.cb_topping);
        }
    }

    public interface OnNoteListener{
        void onNoteClick(ToppingModel.Data position);
    }
}
