package com.deliveryfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.DeliveryFood.lib.Model.FoodModel;
import com.DeliveryFood.lib.Model.InvoiceDetailModel;
import com.DeliveryFood.lib.Repository.Methods;
import com.DeliveryFood.lib.retrofitClient;

import com.deliveryfood.MainActivity;
import com.deliveryfood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class detailBillApdapter extends BaseAdapter {
    private Context context;
    private int layOut;
    private List<InvoiceDetailModel.obj> detailList;

    public detailBillApdapter(Context context, int layOut, List<InvoiceDetailModel.obj> detailList) {
        this.context = context;
        this.layOut = layOut;
        this.detailList = detailList;
    }

    @Override
    public int getCount() {
        return detailList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


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
        InvoiceDetailModel.obj InvoiceDetail = detailList.get(position);
        viewHolder.gia.setText(Float.toString((float) InvoiceDetail.getUnitPrice()) + "đ");
        viewHolder.MoTa.setText("Số lượng: " + Integer.toString(InvoiceDetail.getQuantity()));
        viewHolder.MoTa.setTextSize(15);
        FoodModel.Data food = getFood(InvoiceDetail.getID_Food());
        viewHolder.Name.setText(food.getName_Food());
        Picasso.get().load(food.getPicture()).into(viewHolder.Img);
        return convertView;
    }

    private FoodModel.Data getFood(int id_food) {
        // lay food ve
        MainActivity mainActivity = (MainActivity) context;

        List<FoodModel.Data> dataList = mainActivity.getListProduct();
        if (dataList.size() == 0) {
            Methods methods = retrofitClient.getRetrofit().create(Methods.class);
            Call<FoodModel> call = methods.getNewFood();
            call.enqueue(new Callback<FoodModel>() {
                @Override
                public void onResponse(Call<FoodModel> call, Response<FoodModel> response) {
                    if (response.body().getData().size() != 0) {
                        mainActivity.listProduct = response.body().getData();
                    }
                }

                @Override
                public void onFailure(Call<FoodModel> call, Throwable t) {
                }
            });

        }
        dataList = mainActivity.getListProduct();
        FoodModel.Data food = getFoodByID(id_food, dataList);

        return food;

    }

    private FoodModel.Data getFoodByID(int id_food, List<FoodModel.Data> Foods) {
        for (FoodModel.Data temp : Foods
        ) {
            if (temp.getID_Food() == (id_food))
                return temp;
        }
        return null;
    }

    private static class ViewHolder {
        TextView Name;
        ImageView Img;
        TextView MoTa;
        TextView gia;
    }


}
