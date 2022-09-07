package com.deliveryfood.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.DeliveryFood.lib.Interface.IChuyenData;
import com.DeliveryFood.lib.Model.Account;
import com.DeliveryFood.lib.Model.FoodModel;
import com.DeliveryFood.lib.Model.ResultModel;
import com.DeliveryFood.lib.Repository.Methods;
import com.DeliveryFood.lib.retrofitClient;
import com.deliveryfood.Adapter.PhotoAdapter;
import com.deliveryfood.Adapter.ProductAdapterRecyclerview;
import com.deliveryfood.MainActivity;
import com.deliveryfood.R;

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
    RecyclerView recyclerView;
    private TextView txt_xinchao;
    private  MainActivity mainActivity ;
    RecyclerView list_SP_BanChay1;
    private  Account account;
    private IChuyenData iChuyenData;

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
        mainActivity  = (MainActivity) getActivity();
        account = mainActivity.getTaiKhoan();
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = view.findViewById(R.id.viewPager_home);
        circleIndicator = view.findViewById(R.id.CircleIndicator_home);
        recyclerView = view.findViewById(R.id.list_spMoi);
        list_SP_BanChay1 = view.findViewById(R.id.list_SP_BanChay1);
        txt_xinchao = view.findViewById(R.id.txt_xinchao);
        if(account!=null){
            txt_xinchao.setText("Xin chào bạn\n\n\t"+account.getData().getName());
            if(mainActivity.cartItems.size()>0){
                Methods methods = retrofitClient.getRetrofit().create(Methods.class);
                Call<ResultModel> call = methods.InsertCart(mainActivity.cartItems);
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
        }


        List<String> photo = new ArrayList<>();
        photo.add("https://res.cloudinary.com/dtri2001/image/upload/v1654576417/IMG/Milk%20tea/payment_c0silj.jpg");
        photo.add("https://res.cloudinary.com/dtri2001/image/upload/v1654576416/IMG/Milk%20tea/maps_wp2wjg.png");
        photoAdapter = new PhotoAdapter(inflater.getContext(), photo);
        viewPager.setAdapter(photoAdapter);
        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        // xem them BC
        Button btn_dkNgay = view.findViewById(R.id.btb_XemThem_BC);
        btn_dkNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new DangNhapFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in,R.anim.slide_out,R.anim.slide_in,R.anim.slide_out);

                fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
                fragmentTransaction.addToBackStack("Fragment home");

                mainActivity.ChipNavigationBar.setItemSelected(R.id.menu_nav_user,true);
                fragmentTransaction.commit();
            }
        });
        // xem them sp moi

        Button btn_chuyen_sanpham = view.findViewById(R.id.btb_XemThem_SPMoi);
        btn_chuyen_sanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new fragmentproduct();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in,R.anim.slide_out,R.anim.slide_in,R.anim.slide_out);

                fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
                fragmentTransaction.addToBackStack("Fragment home");
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.ChipNavigationBar.setItemSelected(R.id.menu_nav_buy,true);
                fragmentTransaction.commit();
            }
        });

        ImageButton btn_cart = view.findViewById(R.id.product_btn_cart);
        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new CartFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in,R.anim.slide_out,R.anim.slide_in,R.anim.slide_out);

                fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
                fragmentTransaction.addToBackStack("Fragment home");
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.ChipNavigationBar.setItemSelected(R.id.menu_nav_buy,true);
                fragmentTransaction.commit();
            }
        });


        SPMoi();
       SPBanChay();
        return view;
    }

    private void SPBanChay() {
        Methods methods = retrofitClient.getRetrofit().create(Methods.class);
        Call<FoodModel> callCate = methods.getPopularFood();
        callCate.enqueue(new Callback<FoodModel>() {
            @Override
            public void onResponse(Call<FoodModel> call, Response<FoodModel> response) {
                FoodModel temp = new FoodModel();
                temp.setData(response.body().getData());
                ProductAdapterRecyclerview categoryApdapter =
                        new ProductAdapterRecyclerview(getContext(), R.layout.item_product,
                                temp.getData(), new ProductAdapterRecyclerview.OnNoteListener() {
                            @Override
                            public void onNoteClick(FoodModel.Data position) {

                            }
                        }
                        );
                categoryApdapter.setList(temp.getData());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
                list_SP_BanChay1.setLayoutManager(linearLayoutManager);
                list_SP_BanChay1.setAdapter(categoryApdapter);
            }

            @Override
            public void onFailure(Call<FoodModel> call, Throwable t) {

            }
        });
    }


    public  void SPMoi(){
        Methods methods = retrofitClient.getRetrofit().create(Methods.class);
        Call<FoodModel> callCate = methods.getNewFood();
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
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(categoryApdapter);
            }

            @Override
            public void onFailure(Call<FoodModel> call, Throwable t) {

            }
        });
    }
    private List<FoodModel.Data> getList(List<FoodModel.Data> foods, Response<FoodModel> response) {

        for (FoodModel.Data item: response.body().getData()
        ) {
            foods.add(item);
        }
        return foods;
    }
    public void chuyenData(FoodModel.Data product) {
        iChuyenData.ChuyenData(product);
    }

}
