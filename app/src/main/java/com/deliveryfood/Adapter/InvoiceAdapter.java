package com.deliveryfood.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.DeliveryFood.lib.Model.InvoiceModel;
import com.deliveryfood.Fragment.invoiceDetailFragment;
import com.deliveryfood.MainActivity;
import com.deliveryfood.R;
import com.deliveryfood.common.MonneyFormat;

import java.util.List;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.FoodViewHolder> {

    private List<InvoiceModel.Data> list;
    private MainActivity mainActivity;

    public InvoiceAdapter(List<InvoiceModel.Data> list, MainActivity mainActivity) {
        this.list = list;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invoice, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        InvoiceModel temp = new InvoiceModel();

        if (list.get(position) == null)
            return;
        //List<com.example.lib.Model.product> temp = product.get();
        //Picasso.get().load(temp.get(0).getImg()).into(holder.imgviewFood);
        holder.imgviewFood.setImageResource(R.drawable.food);
        holder.NgayDat.setText(list.get(position).getDate_Create());
        holder.Gia.setText(MonneyFormat.formatMonney((long) list.get(position).getTotal_Money()));
        holder.Item_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDetail(list.get(position));
            }
        });
    }

    private void goToDetail(InvoiceModel.Data invoice) {
        FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();
        Fragment fragment = new invoiceDetailFragment(invoice);
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.addToBackStack("Fragment invoice");
        fragmentTransaction.commit();
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView imgviewFood;
        TextView NgayDat;
        LinearLayout Item_product;
        TextView Gia;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imgviewFood = itemView.findViewById(R.id.Img_invoice);
            Item_product = itemView.findViewById(R.id.Item_product);
            NgayDat = itemView.findViewById(R.id.txt_Ngaydat);
            Gia = itemView.findViewById(R.id.txt_tongTien);
        }

    }
}
