package com.deliveryfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DeliveryFood.lib.Model.FoodModel;
import com.deliveryfood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapterRCV extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_LOADING = 2;
    private List<FoodModel.Data> mListFood;
    private boolean isLoading;
    private Context context;

    OnNoteListener onclink;

    public List<FoodModel.Data> getmListFood() {
        return mListFood;
    }

    public ProductAdapterRCV(OnNoteListener onclink, Context context) {
        this.onclink = onclink;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (mListFood != null && position == mListFood.size() - 1 && isLoading) {
            return TYPE_LOADING;
        }
        return TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
            return new ProductViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_ITEM) {
            ProductViewHolder productViewHolder = (ProductViewHolder) holder;
            FoodModel.Data Food = mListFood.get(position);
            if (Food == null)
                return;
            else {
                Picasso.get().load(Food.getPicture()).into(productViewHolder.Img);
                productViewHolder.Name.setText(Food.getName_Food());
                productViewHolder.gia.setText(Integer.toString((int) Food.getPrice()) + "đ");
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                productViewHolder.Rcv_food.startAnimation(animation);
            }
            productViewHolder.Rcv_food.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onclink.onNoteClick(Food);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mListFood != null) {
            return mListFood.size();
        }
        return 0;
    }

    public void setmListFood(List<FoodModel.Data> mListFood) {
        this.mListFood = mListFood;
        notifyDataSetChanged();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        ImageView Img;
        TextView MoTa;
        TextView gia;
        LinearLayout Rcv_food;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            Img = (ImageView) itemView.findViewById(R.id.img_sanpham);
            gia = (TextView) itemView.findViewById(R.id.gia_sanpham);
            Name = (TextView) itemView.findViewById(R.id.ten_sanpham);
            MoTa = (TextView) itemView.findViewById(R.id.mota_sanpham);
            Rcv_food = itemView.findViewById(R.id.Rcv_food);

        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.ProgressBar);
        }
    }

    public void AddFooterLoading() {
        isLoading = true;
//        FoodModel temp =new FoodModel();
//        temp.data.add();
//        mListFood.add(temp.data.);
    }

    public void RemoveFooterLoading() {
        isLoading = false;

    }


    public interface OnNoteListener {
        void onNoteClick(FoodModel.Data position);
    }
}
