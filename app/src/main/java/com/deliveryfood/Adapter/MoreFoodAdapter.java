package com.deliveryfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DeliveryFood.lib.Model.FoodModel;
import com.deliveryfood.R;
import com.deliveryfood.common.MonneyFormat;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoreFoodAdapter extends RecyclerView.Adapter<MoreFoodAdapter.HangDTViewHolder> {

    Context context;
    int layout;
    private List<FoodModel.Data> mListener;
    private ImageView imgSP;
    OnNoteListener onclink;

    public MoreFoodAdapter(Context context, int layout, List<FoodModel.Data> mListener, OnNoteListener onclink) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product1, parent, false);
        return new HangDTViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HangDTViewHolder holder, int position) {
        FoodModel.Data Food = mListener.get(position);
        if (Food == null)
            return;
        else {
            Picasso.get().load(Food.getPicture()).into(holder.imgSP);
            holder.tenSP.setText(Food.getName_Food());
            holder.giaSP.setText(MonneyFormat.formatMonney((long) Food.getPrice()));
        }

    }

    @Override
    public int getItemCount() {
        if (mListener != null)
            return mListener.size();
        return 0;
    }

    public class HangDTViewHolder extends RecyclerView.ViewHolder {
        TextView tenSP;
        ImageView imgSP;
        TextView giaSP;

        public HangDTViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSP = (ImageView) itemView.findViewById(R.id.imgSP);
            giaSP = (TextView) itemView.findViewById(R.id.giaSP);
            tenSP = (TextView) itemView.findViewById(R.id.tenSP);
        }
    }

    public interface OnNoteListener {
        void onNoteClick(FoodModel.Data position);
    }
}
