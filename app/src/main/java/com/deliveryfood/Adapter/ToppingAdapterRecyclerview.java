package com.deliveryfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DeliveryFood.lib.Model.ToppingModel;
import com.deliveryfood.MainActivity;
import com.deliveryfood.R;
import com.deliveryfood.common.MonneyFormat;
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
        toppings = new ArrayList<ToppingModel.Data>();
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
        MainActivity mainActivity = (MainActivity) context;
        if (toppings.contains(Topping))
            holder.checkBox.setChecked(true);

        if (Topping == null)
            return;
        else {
            Picasso.get().load(Topping.getImg()).into(holder.Img);
            holder.Name.setText(Topping.getName_Topping());
            holder.gia.setText(MonneyFormat.formatMonney((long) Topping.getPrice()));
        }

        holder.item_topping.setOnClickListener(view -> {
            if (holder.checkBox.isChecked()) {
                holder.checkBox.setChecked(false);
                toppings.remove(List.get(position));
                mainActivity.setToppings(toppings);
            } else {
                holder.checkBox.setChecked(true);
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
        LinearLayout item_topping;
        CheckBox checkBox;


        public HangDTViewHolder(@NonNull View itemView) {
            super(itemView);
            Img = (ImageView) itemView.findViewById(R.id.img_topping);
            gia = (TextView) itemView.findViewById(R.id.txt_PriceTopping);
            Name = (TextView) itemView.findViewById(R.id.txt_NameTopping);
            item_topping = itemView.findViewById(R.id.item_topping);
            checkBox = itemView.findViewById(R.id.cb_topping);

        }
    }
}
