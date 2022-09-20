package com.deliveryfood.Adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.DeliveryFood.lib.Model.Account;
import com.DeliveryFood.lib.Model.DiscountModel;
import com.DeliveryFood.lib.Model.FoodModel;
import com.DeliveryFood.lib.Repository.Methods;
import com.DeliveryFood.lib.retrofitClient;
import com.deliveryfood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopProductAdapter extends RecyclerView.Adapter<TopProductAdapter.HangDTViewHolder> {

    Context context;
    int layout;
    private List<FoodModel.Data> mListener;
    OnNoteListener onclink;
    private DiscountModel Discount;
    private int percent = 0;


    public TopProductAdapter(Context context, int layout, List<FoodModel.Data> mListener, OnNoteListener onclink,DiscountModel discount) {
        this.context = context;
        this.layout = layout;
        this.mListener = mListener;
        this.onclink = onclink;
        Discount=discount;
    }

    public void setList(List<FoodModel.Data> mListener) {
        this.mListener = mListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HangDTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bsales, parent, false);
        return new HangDTViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HangDTViewHolder holder, int position) {
        FoodModel.Data Food = mListener.get(position);
         float price = getDiscountPrice(Food);
        if (Food == null)
            return;
        else {
            Picasso.get().load(Food.getPicture()).into(holder.Img);
            holder.Name.setText(Food.getName_Food());
            String OldPrice = ConvertPrice( Food.getPrice()) + " đ";
            if (price == Food.getPrice()) {
                // ẩn item discount vs ẩn giá discount
                holder.Price.setText(OldPrice);
            } else if (price < Food.getPrice()) {
                // gắn giá và % giảm giá
                holder.ItemDiscount.setVisibility(View.VISIBLE);
                holder.percent_Discount.setText(Integer.toString(percent)+"%");
                holder.oldPrice.setVisibility(View.VISIBLE);
                SpannableString noidungspanned = new SpannableString(OldPrice);
                noidungspanned.setSpan(new StrikethroughSpan(), 0, noidungspanned.length(), 0);
                holder.oldPrice.setMovementMethod(LinkMovementMethod.getInstance());
                holder.oldPrice.setText(noidungspanned);
                holder.Price.setText(ConvertPrice(price)+ " đ");
            }
            holder.Rating.setText(Double.toString((double) Food.getRating()));
        }
        holder.Rcv_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclink.onNoteClick(Food);
            }
        });
    }
    private float getDiscountPrice(FoodModel.Data food) {

        for (DiscountModel.Data item: Discount.getData()
             ) {
            if (food.getID_Discount() == item.getID_Discount())
            {
                percent = item.getPercent();
                break;
            }
        }
        float price = food.getPrice() - (food.getPrice() * percent/100);
        return price;
    }

    private  String ConvertPrice (float price ){
        int Intprice=(int) price;
        String result = "";
        while (Intprice>999){
            int temp = Intprice %1000;
            if(temp <10)
                result = ".00"+temp +result;
            else
            if(temp<100)
                result =".0"+ temp +result;
            else
            result = "."+temp +result;
            Intprice = Intprice /1000;
        }
        result = Intprice +result;
        return  result;
    }


    @Override
    public int getItemCount() {
        if (mListener != null)
            return mListener.size();
        return 0;
    }

    public class HangDTViewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        ImageView Img;
        TextView Rating;
        TextView Price;
        TextView oldPrice;
        TextView percent_Discount;
        ConstraintLayout Rcv_food;
        ConstraintLayout ItemDiscount;

        public HangDTViewHolder(@NonNull View itemView) {
            super(itemView);
            Img = (ImageView) itemView.findViewById(R.id.img_PD);
            oldPrice = (TextView) itemView.findViewById(R.id.txt_pricePD);
            Price = itemView.findViewById(R.id.txt_NewPricePD);
            Name = (TextView) itemView.findViewById(R.id.txt_namePD);
            Rating = (TextView) itemView.findViewById(R.id.txt_star);
            Rcv_food = itemView.findViewById(R.id.Item_BS);
            percent_Discount = itemView.findViewById(R.id.percent_Discount);
            ItemDiscount = itemView.findViewById(R.id.ItemDiscount);
        }
    }

    public interface OnNoteListener {
        void onNoteClick(FoodModel.Data position);
    }
}
