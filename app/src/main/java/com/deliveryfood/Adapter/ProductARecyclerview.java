package com.deliveryfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.DeliveryFood.lib.Entities.Cart;
import com.DeliveryFood.lib.Model.FoodModel;
import com.deliveryfood.R;
import com.deliveryfood.common.MonneyFormat;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductARecyclerview extends RecyclerView.Adapter<ProductARecyclerview.HangDTViewHolder> {

    Context context;
    int layout;
    public List<Cart> list;
    List<FoodModel.Data> Foods;


    public ProductARecyclerview(Context context, List<Cart> cartItems, List<FoodModel.Data> Foods) {
        this.context = context;
        list = cartItems;
        this.Foods = Foods;

    }

    public void setList(List<Cart> mListener) {
        this.list = mListener;
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
        Cart Cart = list.get(position);
        FoodModel.Data food = getFoodByID(Cart.getID_Food());

        if (food == null)
            return;
        else {
            if (food.getPercenDiscount() == 0) {
                holder.constraintTemp.setVisibility(View.GONE);
            } else {
                holder.constraintTemp.setVisibility(View.VISIBLE);
                holder.Discount.setText(food.getPercenDiscount() + "%");
            }
            Picasso.get().load(food.getPicture()).into(holder.Img);
            holder.Name.setText(food.getName_Food());
            float gia = food.getPrice();
            if (food.getPercenDiscount() != 0)
                gia = gia - (food.getPrice() * food.getPercenDiscount() / 100);
            holder.gia.setText(MonneyFormat.formatMonney((long) gia));
        }


    }

    private FoodModel.Data getFoodByID(int id_food) {
        for (FoodModel.Data temp : Foods
        ) {
            if (temp.getID_Food() == id_food)
                return temp;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    public class HangDTViewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        ImageView Img;
        TextView MoTa;
        TextView gia;
        TextView Discount;
        ConstraintLayout constraintTemp;

        LinearLayout Rcv_food;

        public HangDTViewHolder(@NonNull View itemView) {
            super(itemView);
            Img = (ImageView) itemView.findViewById(R.id.img_sanpham);
            gia = (TextView) itemView.findViewById(R.id.gia_sanpham);
            Name = (TextView) itemView.findViewById(R.id.ten_sanpham);
            MoTa = (TextView) itemView.findViewById(R.id.mota_sanpham);
            Rcv_food = itemView.findViewById(R.id.Rcv_food);
            Discount = itemView.findViewById(R.id.Discount);
            constraintTemp = itemView.findViewById(R.id.constraintTemp);
        }
    }

    public interface OnNoteListener {
        void onNoteClick(FoodModel.Data position);
    }
}
