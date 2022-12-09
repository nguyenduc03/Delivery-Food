package com.deliveryfood.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.DeliveryFood.lib.Entities.Category;
import com.DeliveryFood.lib.Interface.IChuyenData;
import com.DeliveryFood.lib.Model.Account;
import com.DeliveryFood.lib.Model.CategoryModel;
import com.DeliveryFood.lib.Model.Food;
import com.DeliveryFood.lib.Model.FoodModel;
import com.DeliveryFood.lib.Repository.Methods;
import com.DeliveryFood.lib.retrofitClient;
import com.deliveryfood.Adapter.CategoryApdapter;
import com.deliveryfood.Adapter.ProductAdapterRCV;
import com.deliveryfood.Adapter.ScrollListener;
import com.deliveryfood.MainActivity;
import com.deliveryfood.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragmentproduct extends Fragment {
    public fragmentproduct(Account userInfo) {
        this.userInfo = userInfo;
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    IChuyenData iChuyenData;
    RecyclerView listView;
    List<FoodModel.Data> listProduct;
    RecyclerView recyclerView;
    ImageButton imageButton, btn_find;
    Account userInfo;
    TextView txt_find;
    MainActivity mainActivity;
    Methods methods;
    private ProductAdapterRCV productAdapter;
    private boolean isLoading;
    private boolean isLastPage;
    private int currentPage = 1;

    private String mParam1;
    private String mParam2;

    public fragmentproduct() {
    }


    public static fragmentproduct newInstance(String param1, String param2) {
        fragmentproduct fragment = new fragmentproduct();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    View result;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iChuyenData = (IChuyenData) getActivity();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        result = inflater.inflate(R.layout.fragment_products, container, false);
        methods = retrofitClient.getRetrofit().create(Methods.class);
        mainActivity = (MainActivity) getActivity();
        anhXa();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(layoutManager);
        productAdapter = new ProductAdapterRCV(new ProductAdapterRCV.OnNoteListener() {
            @Override
            public void onNoteClick(FoodModel.Data position) {
                chuyenData(position);
            }
        }, getContext());

        listProduct = new ArrayList<FoodModel.Data>();
        listView.setAdapter(productAdapter);
        getDataFood();


        listView.addOnScrollListener(new ScrollListener(layoutManager) {
            @Override
            public void LoadMoreItems() {
                isLoading = true;
                currentPage++;
                LoadNextPage();
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
        });
        try {
            Thread.sleep(200);
        } catch (Exception e) {

        }
        getDataCate();
        find();
        open_cart();
        return result;
    }


    private void LoadNextPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                productAdapter.RemoveFooterLoading();
                getDataFood();
            }
        }, 2000);
    }


    private void open_cart() {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CartFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out);
                fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
                fragmentTransaction.addToBackStack("Fragment home");
                fragmentTransaction.commit();
            }
        });
    }

    public void chuyenData(FoodModel.Data product) {
        iChuyenData.ChuyenData(product);
    }

    private void anhXa() {
        listView = result.findViewById(R.id.ListProduct);
        recyclerView = result.findViewById(R.id.list_category);
        imageButton = result.findViewById(R.id.product_btn_cart);
        btn_find = result.findViewById(R.id.button_findProduct);
        txt_find = result.findViewById(R.id.txt_inputProduct);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

    }

    private List<FoodModel.Data> getDataFood() {
        FoodModel temp = new FoodModel();
        temp.data = new ArrayList<FoodModel.Data>();
        if (listProduct.size() == 0) {
            Call<FoodModel> call = methods.getFood();
            call.enqueue(new Callback<FoodModel>() {
                @Override
                public void onResponse(Call<FoodModel> call, Response<FoodModel> response) {
                    listProduct = response.body().data;
                    CreateView(listProduct);

                }

                @Override
                public void onFailure(Call<FoodModel> call, Throwable t) {

                }
            });
        } else {
            System.out.println(listProduct.get(listProduct.size() - 1).getID_Food());

            Call<FoodModel> call = methods.getMoreFood(listProduct.get(listProduct.size() - 1));
            call.enqueue(new Callback<FoodModel>() {
                @Override
                public void onResponse(Call<FoodModel> call, Response<FoodModel> response) {
                    if (response.body().isStatus()) {
                        listProduct.addAll(response.body().data);
                        productAdapter.notifyDataSetChanged();
                        isLoading = false;
                        productAdapter.AddFooterLoading();
                    } else {
                        isLastPage = true;
                        productAdapter.RemoveFooterLoading();
                    }
                }

                @Override
                public void onFailure(Call<FoodModel> call, Throwable t) {
                }
            });
        }
        return temp.data;
    }

    private void getDataCate() {
        Call<CategoryModel> callCate = methods.GetCategoryList();
        callCate.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                List<Category> categories = response.body().getData();
                CategoryApdapter categoryApdapter =
                        new CategoryApdapter(
                                categories,
                                new CategoryApdapter.OnNoteListener() {
                                    @Override
                                    public void onNoteClick(Category category) {
                                        getFoodByCate(category);

                                    }
                                });
                recyclerView.setAdapter(categoryApdapter);
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {

            }
        });

    }

    private void getFoodByCate(Category category) {
        Call<FoodModel> getByCate = methods.getFoodByCategory(category);
        getByCate.enqueue(new Callback<FoodModel>() {
            @Override
            public void onResponse(Call<FoodModel> call, Response<FoodModel> response) {
                List<FoodModel.Data> foods = response.body().getData();
                ProductAdapterRCV productAdapter = new ProductAdapterRCV(new ProductAdapterRCV.OnNoteListener() {
                    @Override
                    public void onNoteClick(FoodModel.Data position) {
                        chuyenData(position);
                    }
                }, getContext());
                listView.setAdapter(productAdapter);
            }

            @Override
            public void onFailure(Call<FoodModel> call, Throwable t) {

            }
        });
    }

    private void CreateView(List<FoodModel.Data> listProduct) {

        productAdapter.setmListFood(listProduct);
        productAdapter.AddFooterLoading();
    }

    private void find() {
        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Food newFood = new Food();
                newFood.setName_Food(txt_find.getText().toString().trim());
                Call<FoodModel> call = methods.FindFood(newFood);
                call.enqueue(new Callback<FoodModel>() {
                    @Override
                    public void onResponse(Call<FoodModel> call, Response<FoodModel> response) {
                        if (response.body().getData().size() > 0) {
                            List<FoodModel.Data> foods = response.body().getData();
                            ShowMessage("Tìm thấy", "Kết quả", "Ok");
                            CreateView(foods);
                        } else {
                            ShowMessage("không tìm thấy", "Kết quả", "Ok");
                        }

                    }

                    @Override
                    public void onFailure(Call<FoodModel> call, Throwable t) {

                    }
                });
            }
        });
    }

    public void ShowMessage(String message, String title, String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setPositiveButton(text, null);
        builder.setCancelable(true);
        builder.create().show();
    }
}