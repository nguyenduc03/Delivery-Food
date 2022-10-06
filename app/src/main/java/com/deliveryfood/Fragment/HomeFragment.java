package com.deliveryfood.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.DeliveryFood.lib.Interface.IChuyenData;
import com.DeliveryFood.lib.Model.Account;
import com.DeliveryFood.lib.Model.DiscountModel;
import com.DeliveryFood.lib.Model.FoodModel;
import com.DeliveryFood.lib.Model.ResultModel;
import com.DeliveryFood.lib.Repository.Methods;
import com.DeliveryFood.lib.retrofitClient;
import com.deliveryfood.Adapter.PhotoAdapter;
import com.deliveryfood.Adapter.ProductAdapterRecyclerview;
import com.deliveryfood.Adapter.TopProductAdapter;
import com.deliveryfood.MainActivity;
import com.deliveryfood.R;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;
    private RecyclerView recyclerView;
    private TextView txtXinChao;
    private MainActivity mainActivity;
    private RecyclerView bestSaleFoods;
    private Account account;
    private ImageView imgSun;
    private IChuyenData iChuyenData;
    private Button btnXemThemSPMoi;
    private Button btnXemThemBC;
    private RecyclerView discountFoods;
    private ImageButton btnCart;

    public HomeFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        InitData(view);
        loadData(inflater.getContext());
        OnClick();
        return view;
    }

    private void OnClick() {
        btnXemThemBC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new DangNhapFragment();
                goToFragment(fragment, R.id.menu_nav_user);

            }
        });

        btnXemThemSPMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new fragmentproduct();
                goToFragment(fragment, R.id.menu_nav_buy);
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new CartFragment();
                goToFragment(fragment, R.id.menu_nav_buy);
            }
        });
    }

    private void goToFragment(Fragment fragment, int menu_nav_buy) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out);
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.addToBackStack("Fragment home");
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.ChipNavigationBar.setItemSelected(menu_nav_buy, true);
        fragmentTransaction.commit();
    }

    private void loadData(Context context) {
        LoadviewPager(context);
        LoadNewFoods();
        try {
            Thread.sleep(200);
        } catch (Exception e) {
        }
        LoadBestFoods();
        try {
            Thread.sleep(200);
        } catch (Exception e) {
        }
        LoadBestDiscountFoods();
    }

    private void setLoiChao() {
        String LoiChao = "";
        int Hour = LocalTime.now().getHour();
        if (Hour >= 4 && Hour < 11)
            LoiChao = "Xin chào buổi sáng";
        else if (Hour >= 11 && Hour <= 12)
            LoiChao = "Xin chào buổi trưa";
        else if (Hour > 12 && Hour < 18)
            LoiChao = "Xin chào buổi chiều";
        else {
            LoiChao = "Xin chào buổi tối";
            imgSun.setImageResource(R.drawable.night);
        }
        if (account != null)
            LoiChao += "\n" + account.getData().getName();
        txtXinChao.setText(LoiChao);
    }

    private void LoadviewPager(Context context) {
        List<String> photo = new ArrayList<>();
        photo.add("https://res.cloudinary.com/dtri2001/image/upload/v1654576417/IMG/Milk%20tea/payment_c0silj.jpg");
        photo.add("https://res.cloudinary.com/dtri2001/image/upload/v1654576416/IMG/Milk%20tea/maps_wp2wjg.png");
        photoAdapter = new PhotoAdapter(context, photo);
        viewPager.setAdapter(photoAdapter);
        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
    }

    private void InitData(View view) {
        mainActivity = (MainActivity) getActivity();
        account = mainActivity.getTaiKhoan();
        viewPager = view.findViewById(R.id.viewPagerSuKien);
        circleIndicator = view.findViewById(R.id.CircleIndicator_home);
        recyclerView = view.findViewById(R.id.listSPMoi);
        imgSun = view.findViewById(R.id.imgSun);
        btnXemThemBC = view.findViewById(R.id.btnXemThemBC);
        btnXemThemSPMoi = view.findViewById(R.id.btnXemThemSPMoi);
        bestSaleFoods = view.findViewById(R.id.bestSaleFoods);
        txtXinChao = view.findViewById(R.id.txtXinChao);
        discountFoods = view.findViewById(R.id.discountFoods);
        btnCart = view.findViewById(R.id.btnCart);

        setLoiChao();
        // cập nhật giỏ hàng
        if (account != null) {
            if (mainActivity.cartItems.size() > 0) {
                Methods methods = retrofitClient.getRetrofit().create(Methods.class);
                Call<ResultModel> callInsertCart = methods.InsertCart(mainActivity.cartItems);
                callInsertCart.enqueue(new Callback<ResultModel>() {
                    @Override
                    public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                        response.code();
                    }

                    @Override
                    public void onFailure(Call<ResultModel> call, Throwable t) {

                    }
                });
            }
        }
    }

    private void LoadBestDiscountFoods() {
        Methods methods = retrofitClient.getRetrofit().create(Methods.class);
        Call<FoodModel> callTopFoodDiscount = methods.getTopFoodDiscount();
        callTopFoodDiscount.enqueue(new Callback<FoodModel>() {
            @Override
            public void onResponse(Call<FoodModel> call, Response<FoodModel> response) {

            }

            @Override
            public void onFailure(Call<FoodModel> call, Throwable t) {

            }
        });
    }

    private void LoadBestFoods() {
        Methods methods = retrofitClient.getRetrofit().create(Methods.class);
        Call<FoodModel> callTopFoods = methods.getTopFood();
        callTopFoods.enqueue(new Callback<FoodModel>() {
            @Override
            public void onResponse(Call<FoodModel> call, Response<FoodModel> response) {
                FoodModel temp = new FoodModel();
                temp.setData(response.body().getData());
                TopProductAdapter categoryApdapter =
                        new TopProductAdapter(getContext(), R.layout.item_product,
                                temp.getData(), new TopProductAdapter.OnNoteListener() {
                            @Override
                            public void onNoteClick(FoodModel.Data position) {
                                // GO TO DETAIL
                                chuyenData(position);
                            }
                        }
                        );
                categoryApdapter.setList(temp.getData());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                bestSaleFoods.setLayoutManager(linearLayoutManager);
                bestSaleFoods.setAdapter(categoryApdapter);
            }

            @Override
            public void onFailure(Call<FoodModel> call, Throwable t) {

            }
        });
    }


    public void LoadNewFoods() {
        Methods methods = retrofitClient.getRetrofit().create(Methods.class);
//        Call<FoodModel> callCate = methods.getNewFood();
        Call<FoodModel> callCate = methods.getFood();
        callCate.enqueue(new Callback<FoodModel>() {
            @Override
            public void onResponse(Call<FoodModel> call, Response<FoodModel> response) {
                FoodModel temp = new FoodModel();
                MainActivity mainActivity = (MainActivity) getActivity();
                temp.setData(response.body().getData());
                mainActivity.listProduct = temp.getData();
                ProductAdapterRecyclerview categoryApdapter =
                        new ProductAdapterRecyclerview(getContext(), R.layout.item_product,
                                temp.getData(), new ProductAdapterRecyclerview.OnNoteListener() {
                            @Override
                            public void onNoteClick(FoodModel.Data position) {
                                chuyenData(position);
                            }
                        }
                        );
                categoryApdapter.setList(temp.getData());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(categoryApdapter);
            }

            @Override
            public void onFailure(Call<FoodModel> call, Throwable t) {
            }
        });
    }

    public void chuyenData(FoodModel.Data product) {
        iChuyenData.ChuyenData(product);
    }
}
