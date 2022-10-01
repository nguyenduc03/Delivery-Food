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

import com.DeliveryFood.lib.Model.ToppingModel;
import com.deliveryfood.MainActivity;
import com.deliveryfood.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ToppingAdapterRecyclerview extends RecyclerView.Adapter<ToppingAdapterRecyclerview.HangDTViewHolder> {

    Context context;
    int layout;
    private List<ToppingModel.Data> List;
    private List<ToppingModel.Data> toppings;


    public ToppingAdapterRecyclerview(Context context, int layout, List<ToppingModel.Data> List) {
        this.context = context;
        this.layout = layout;
        this.List = List;
        toppings= new ArrayList<ToppingModel.Data>();
    }

    public void setList(List<ToppingModel.Data> List) {
        this.List = List;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HangDTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topping, parent, false);

        return new HangDTViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HangDTViewHolder holder, int position) {
        ToppingModel.Data Topping = List.get(position);
        if(toppings.contains(Topping))
            holder.cb_topping.setChecked(true);

        if (Topping == null)
            return;
        else {
            Picasso.get().load(Topping.getImg()).into(holder.Img);
            holder.Name.setText(Topping.getName_Topping());
            holder.gia.setText(Float.toString((float) Topping.getPrice()));
        }
        holder.itemView.setOnClickListener(view -> {
            List.get(position);
            MainActivity mainActivity = (MainActivity)context;

            if (holder.cb_topping.isChecked()) {
                holder.cb_topping.setChecked(false);
                toppings.remove( List.get(position));
                mainActivity.setToppings(toppings);
            } else {
                holder.cb_topping.setChecked(true);
                toppings.add(List.get(position));
                mainActivity.setToppings(toppings);
            }
        });
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
}
