package com.deliveryfood.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.DeliveryFood.lib.Entities.Cart;
import com.DeliveryFood.lib.Entities.Category;
import com.DeliveryFood.lib.Interface.IChuyenData;
import com.DeliveryFood.lib.Model.FoodModel;
import com.DeliveryFood.lib.Model.ResultModel;
import com.DeliveryFood.lib.Model.ToppingModel;
import com.DeliveryFood.lib.Repository.Methods;
import com.DeliveryFood.lib.retrofitClient;
import com.deliveryfood.Adapter.ItemFoodAdapterRecyclerview;
import com.deliveryfood.Adapter.PhotoAdapter;
import com.deliveryfood.Adapter.ProductAdapterRecyclerview;
import com.deliveryfood.Adapter.ToppingAdapterRecyclerview;
import com.deliveryfood.MainActivity;
import com.deliveryfood.R;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_detail extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int count;
    MainActivity mainActivity;
    View view;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;
    private RecyclerView recyclerView;
    FoodModel.Data food;

    TextView Name, Price, description;
    ImageButton btn_cart, detail_sl_cong, detail_sl_tru, btn_back_product;
    EditText detail_sl;
    private Button detail_btnThemSP;
    private Button btnFind;
    private TextView txtFind;
    private RecyclerView list_SP_LienQuan;
    RecyclerView list_Toping;
    private IChuyenData iChuyenData;
    private RecyclerView list_SP_BanChay;
    private ImageView add_food_img;


    public static Fragment_detail newInstance(String param1, String param2) {
        Fragment_detail fragment = new Fragment_detail();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iChuyenData = (IChuyenData) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        food = (FoodModel.Data) bundle.getSerializable("product");
        count = 0;
        mainActivity = (MainActivity) getActivity();

        view = inflater.inflate(R.layout.fragment_detail, container, false);
        viewPager = view.findViewById(R.id.view_Pager_Detail);
        circleIndicator = view.findViewById(R.id.Circle_Indicator_Detail);

        anhXa();
        GetToppingList();
        try {
            Thread.sleep(200);

        } catch (Exception e) {

        }
        GetSPLienQuan();
        // SPBanChay();
        setImgs(food, inflater);

        SetData(food);

        open_cart();


        detail_btnThemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cartItem = new Cart();
                cartItem.setID_Food(food.getID_Food());
                cartItem.setQuantity(Integer.parseInt(detail_sl.getText().toString()));
                if (mainActivity.getTaiKhoan() != null) {
                    cartItem.setSDT(mainActivity.getTaiKhoan().getData().getSdt());
                    Methods methods = retrofitClient.getRetrofit().create(Methods.class);
                    Call<ResultModel> call = methods.InsertFoodCart(cartItem);
                    call.enqueue(new Callback<ResultModel>() {
                        @Override
                        public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                            response.code();
                        }

                        @Override
                        public void onFailure(Call<ResultModel> call, Throwable t) {

                        }
                    });
                }
                mainActivity.cartItems.add(cartItem);

            }
        });
        return view;
    }

    private void GetSPLienQuan() {
        Methods methods = retrofitClient.getRetrofit().create(Methods.class);
        Category temp = new Category();
        temp.setID_Category(food.getID_Category());
        temp.setDescription("");
        temp.setName_Category("");
        temp.setPicture("");
        Call<FoodModel> call = methods.getFoodByCategory(temp);
        call.enqueue(new Callback<FoodModel>() {
            @Override
            public void onResponse(Call<FoodModel> call, Response<FoodModel> response) {
                ProductAdapterRecyclerview categoryApdapter =
                        new ProductAdapterRecyclerview(getContext(), R.layout.item_product,
                                response.body().getData(), new ProductAdapterRecyclerview.OnNoteListener() {
                            @Override
                            public void onNoteClick(FoodModel.Data position) {
                                // go to detail
                                chuyenData (position);
                            }
                        }
                        );
                categoryApdapter.setList(response.body().getData());
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                list_SP_LienQuan.setLayoutManager(gridLayoutManager);
                list_SP_LienQuan.setAdapter(categoryApdapter);
            }

            @Override
            public void onFailure(Call<FoodModel> call, Throwable t) {

            }
        });

    }

    private  List<ToppingModel.Data>  toppings;

    private void GetToppingList() {
        Methods methods = retrofitClient.getRetrofit().create(Methods.class);
        Call<ToppingModel> call = methods.GetTopping(food);
        call.enqueue(new Callback<ToppingModel>() {
            @Override
            public void onResponse(Call<ToppingModel> call, Response<ToppingModel> response) {
                // day data len giao dien
                ToppingAdapterRecyclerview categoryApdapter =
                        new ToppingAdapterRecyclerview(getContext(), R.layout.item_product,
                                response.body().getData());
                toppings = new ArrayList<ToppingModel.Data>();
                toppings = response.body().getData();

                categoryApdapter.setList(response.body().getData());
                GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
                list_Toping.setLayoutManager(linearLayoutManager);
                list_Toping.setAdapter(categoryApdapter);
            }

            @Override
            public void onFailure(Call<ToppingModel> call, Throwable t) {

            }
        });
    }

    private void setImgs(FoodModel.Data p, LayoutInflater inflater) {
        List<String> list = new ArrayList<>();
        list.add(food.getPicture());
        list.add(food.getPicture());
        list.add(food.getPicture());
        photoAdapter = new PhotoAdapter(inflater.getContext(), list);
        viewPager.setAdapter(photoAdapter);
        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

    }

    private void xuLy() {
        detail_sl_cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                detail_sl.setText(String.valueOf(count));
            }
        });

        detail_sl_tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    Toast.makeText(getContext(), "Sl > 0", Toast.LENGTH_LONG);
                } else {
                    count--;
                    detail_sl.setText(String.valueOf(count));
                }
            }
        });


    }

    private void CheckSL() {
        float sl = Float.parseFloat(detail_sl.getText().toString());
        if (sl < 0) {
        }
    }

    private void open_cart() {
        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out);

                fragmentTransaction.replace(R.id.fragmentContainerView, new CartFragment());
                fragmentTransaction.addToBackStack("fragment cart ");
                fragmentTransaction.commit();

            }
        });
    }

    public void chuyenData(FoodModel.Data product) {
        iChuyenData.ChuyenData(product);
    }

    private void anhXa() {
        detail_sl = view.findViewById(R.id.detail_sl);
        detail_sl.setText(String.valueOf(count));
//        recyclerView = view.findViewById(R.id.list_SP_LienQuan);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
//        recyclerView.setLayoutManager(linearLayoutManager);
        list_Toping = view.findViewById(R.id.list_Toping);
        btn_cart = view.findViewById(R.id.btn_cart);
        detail_sl_cong = view.findViewById(R.id.detail_sl_cong);
        detail_sl_tru = view.findViewById(R.id.detail_sl_tru);
        btn_back_product = view.findViewById(R.id.btn_back_product);
        detail_btnThemSP = view.findViewById(R.id.detail_btnThemSP);
        list_SP_LienQuan = view.findViewById(R.id.list_SP_LienQuan);
        add_food_img = view.findViewById(R.id.add_food_img);

        this.Name = view.findViewById(R.id.detail_ten);
        this.Price = view.findViewById(R.id.detail_gia);
        description = view.findViewById(R.id.detail_MotaSP);
        xuLy();
        btn_back_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });


    }

    private void SetData(FoodModel.Data product_in) {
        Name.setText(product_in.getName_Food());
        Price.setText(Float.toString((Float) product_in.getPrice()) + " đ");
        description.setText(product_in.getDescription());
    }


}