package com.deliveryfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DeliveryFood.lib.Model.ToppingModel;
import com.deliveryfood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ToppingCartAdapter extends RecyclerView.Adapter<ToppingCartAdapter.HangDTViewHolder> {

    Context context;
    int layout;
    private List<ToppingModel.Data> List;

    public ToppingCartAdapter(Context context, int layout, List<ToppingModel.Data> List) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topping_cart, parent, false);
        return new HangDTViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HangDTViewHolder holder, int position) {
        ToppingModel.Data Topping = List.get(position);
        if (Topping == null)
            return;
        else {
            Picasso.get().load(Topping.getImg()).into(holder.Img);
            holder.Name.setText(Topping.getName_Topping());
        }
    }

    @Override
    public int getItemCount() {
        if (List != null)
            return List.size();
        return 0;
    }

    public class HangDTViewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        ImageView Img;
        public HangDTViewHolder(@NonNull View itemView) {
            super(itemView);
            Img = (ImageView) itemView.findViewById(R.id.img_topping_cart);
            Name = (TextView) itemView.findViewById(R.id.txt_NameTopping_cart);

        }
    }
}
