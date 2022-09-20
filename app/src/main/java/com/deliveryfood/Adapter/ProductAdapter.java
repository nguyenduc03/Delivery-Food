package com.deliveryfood.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.DeliveryFood.lib.Model.FoodModel;
import com.deliveryfood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private int layOut;
    private List<FoodModel.Data> products;

    public ProductAdapter(Context context, int layOut, List<FoodModel.Data> products) {
        this.context = context;
        this.layOut = layOut;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class ViewHolder {
        TextView Name;
        ImageView Img;
        TextView MoTa;
        TextView gia;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layOut, null);
            viewHolder.Img = (ImageView) convertView.findViewById(R.id.img_sanpham);
            viewHolder.gia = (TextView) convertView.findViewById(R.id.gia_sanpham);
            viewHolder.Name = (TextView) convertView.findViewById(R.id.ten_sanpham);
            viewHolder.MoTa = (TextView) convertView.findViewById(R.id.mota_sanpham);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FoodModel.Data product = products.get(position);
        viewHolder.gia.setText(Float.toString((float) product.getPrice()) + " đ");
        viewHolder.Name.setText(product.getName_Food());
        Picasso.get().load(product.getPicture()).into(viewHolder.Img);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        convertView.startAnimation(animation);
        return convertView;
    }


}
