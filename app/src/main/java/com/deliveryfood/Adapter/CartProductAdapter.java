package com.deliveryfood.Adapter;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.DeliveryFood.lib.Entities.Cart;
import com.DeliveryFood.lib.Interface.ChuyenTien;
import com.DeliveryFood.lib.Model.FoodModel;
import com.deliveryfood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartProductAdapter extends BaseAdapter {

    private Context context;
    private int layOut;
    private List<Cart> cartItems;
    private List<FoodModel.Data> Foods;
    ChuyenTien iChuyenData;
    OnClickListener mListener;
    float TongTien;

    public CartProductAdapter(Context context, int layOut, List<Cart> cartItems, List<FoodModel.Data> Foods, OnClickListener mListener) {
        this.context = context;
        this.layOut = layOut;
        this.cartItems = cartItems;
        this.Foods = Foods;
        this.mListener = mListener;
        TongTien = 0;
    }

    @Override
    public int getCount() {
        return cartItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView Name;
        ImageView Img;
        TextView gia;
        ImageButton Cart_Product_btnTru;
        ImageButton Cart_Product_btnCong;
        ImageButton Cart_Product_remove;
        EditText SL;
    }

    //    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Cart cartItem = cartItems.get(position);
        FoodModel.Data food = getFoodByID(cartItem.getID_Food());

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layOut, null);
            viewHolder.Img = (ImageView) convertView.findViewById(R.id.Cart_Product_IMG);
            viewHolder.gia = (TextView) convertView.findViewById(R.id.Cart_Product_price);
            viewHolder.Name = (TextView) convertView.findViewById(R.id.Cart_Product_Name);
            viewHolder.SL = (EditText) convertView.findViewById(R.id.Cart_Product_SL);
            viewHolder.SL.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    cartItem.setQuantity(Integer.parseInt(viewHolder.SL.getText().toString()));
                    viewHolder.gia.setText(Integer.toString((int) food.getPrice() * cartItem.getQuantity()) + " đ");
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    cartItem.setQuantity(Integer.parseInt(viewHolder.SL.getText().toString()));
                    viewHolder.gia.setText(Integer.toString((int) food.getPrice() * cartItem.getQuantity()) + " đ");

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    cartItem.setQuantity(Integer.parseInt(viewHolder.SL.getText().toString()));
                    viewHolder.gia.setText(Integer.toString((int) food.getPrice() * cartItem.getQuantity()) + " đ");

                }
            });
            viewHolder.Cart_Product_btnTru = convertView.findViewById(R.id.Cart_Product_btnTru);
            viewHolder.Cart_Product_btnCong = convertView.findViewById(R.id.Cart_Product_btnCong);
            viewHolder.Cart_Product_remove = convertView.findViewById(R.id.Cart_Product_remove);
            convertView.setTag(viewHolder);
            Picasso.get().load(food.getPicture()).into(viewHolder.Img);
            viewHolder.Name.setText(food.getName_Food());
            viewHolder.SL.setText(Integer.toString(cartItem.getQuantity()));
            cartItem.setTotal_Money(food.getPrice() * cartItem.getQuantity());
            viewHolder.gia.setText(Float.toString(cartItem.getTotal_Money()) + " đ");
            TongTien += cartItem.getTotal_Money();
            ChuyenData(TongTien);
            viewHolder.Cart_Product_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cartItems.remove(cartItem);
                    ChuyenData(0);
                    notifyDataSetChanged();
                }
            });

            viewHolder.Cart_Product_btnCong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = cartItem.getQuantity();
                    count++;
                    viewHolder.SL.setText(Integer.toString(count));
                    cartItem.setQuantity(count);
                    cartItem.setTotal_Money(getFoodByID(cartItem.getID_Food()).getPrice() * cartItem.getQuantity());
                    viewHolder.gia.setText(Float.toString(cartItem.getTotal_Money()));
                    ChuyenData(0);
                }
            });

            viewHolder.Cart_Product_btnTru.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = cartItem.getQuantity();
                    if (count > 0) {
                        count--;
                        cartItem.setQuantity(count);
                        viewHolder.SL.setText(Integer.toString(count));
                        cartItem.setTotal_Money(getFoodByID(cartItem.getID_Food()).getPrice() * cartItem.getQuantity());
                        viewHolder.gia.setText(Float.toString(cartItem.getTotal_Money()));
                    }
                    ChuyenData(0);
                }
            });
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        convertView.startAnimation(animation);
        return convertView;
    }

    private FoodModel.Data getFoodByID(int id_food) {
        for (FoodModel.Data temp : Foods
        ) {
            if (temp.getID_Food() == id_food)
                return temp;
        }
        return null;
    }

    private void ChuyenData(float TT) {

        if (TT == 0) {
            float tt = 0;
            if (cartItems.size() == 0) {
                tt = 0;
            }
            for (Cart temp : cartItems
            ) {
                tt += temp.getTotal_Money();

            }
            mListener.onClick(tt);
            return;
        } else
            mListener.onClick(TT);
    }

    public interface OnClickListener {
        void onClick(float data);
    }

}