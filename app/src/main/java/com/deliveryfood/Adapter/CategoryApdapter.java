package com.deliveryfood.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DeliveryFood.lib.Entities.Category;
import com.deliveryfood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryApdapter extends RecyclerView.Adapter<CategoryApdapter.CategoryViewHolder> {

    List<Category> list;

    private OnNoteListener mListener;

    public CategoryApdapter(List<Category> list, OnNoteListener mListener) {
        this.list = list;

        this.mListener = mListener;
    }

    public void setList(List<Category> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = list.get(position);
        if (category == null)
            return;
        else {
            System.out.println(category.getPicture());
            Picasso.get().load(category.getPicture()).into(holder.imgHangSX);
            holder.Name.setText(category.getName_Category());
        }
        holder.item_cate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onNoteClick(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHangSX;
        TextView Name;
        LinearLayout item_cate;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.name_Category);
            imgHangSX = itemView.findViewById(R.id.img);
            item_cate = itemView.findViewById(R.id.item_cate);
        }
    }

    public interface OnNoteListener {
        void onNoteClick(Category position);
    }
}
