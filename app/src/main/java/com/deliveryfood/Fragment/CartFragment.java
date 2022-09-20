package com.deliveryfood.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.DeliveryFood.lib.Entities.Cart;
import com.DeliveryFood.lib.Interface.ChuyenTien;
import com.DeliveryFood.lib.Interface.IChuyenData;
import com.DeliveryFood.lib.Model.Account;
import com.DeliveryFood.lib.Model.FoodModel;
import com.DeliveryFood.lib.Model.ResultModel;
import com.DeliveryFood.lib.Repository.Methods;
import com.DeliveryFood.lib.retrofitClient;
import com.deliveryfood.Adapter.CartProductAdapter;
import com.deliveryfood.MainActivity;
import com.deliveryfood.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.momo.momo_partner.AppMoMoLib;


public class CartFragment extends Fragment implements ChuyenTien {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;
    ImageButton btn_back_product;
    ListView cart_list;
    private float TongTien;
    TextView TongTien1;
    Button button;
    public List<Cart> cartItems;
    MainActivity mainActivity;
    private Methods methods;
    IChuyenData iChuyenData;
    Button TT_Momo;
    private String amount = "100000";
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "dtri2001";
    private String merchantCode = "MOMOHHFF20220602";
    private String merchantNameLabel = "Nhà cung cấp";
    private String description = "Thanh toán dịch vụ đồ ăn tại Delivery Food";

    public CartFragment() {
        // Required empty public constructor
    }


    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainActivity = (MainActivity) getActivity();
        if (mainActivity.cartItems.size() == 0)
            view = inflater.inflate(R.layout.fragment_no_food, container, false);
        else {
            view = inflater.inflate(R.layout.fragment_cart, container, false);

            AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT);
            mainActivity.ChipNavigationBar.setItemSelected(R.id.menu_nav_buy, true);
            AnhXa();
            InitList();
            cartItems = mainActivity.cartItems;

            CartProductAdapter adapter =
                    new CartProductAdapter(
                            getActivity(),
                            R.layout.cart_product,
                            cartItems, mainActivity.listProduct, new CartProductAdapter.OnClickListener() {
                        @Override
                        public void onClick(float data) {
                            TongTien1.setText(Integer.toString((int) data) + " đ");
                            TongTien = data;

                        }
                    }
                    );
            cart_list.setAdapter(adapter);


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mainActivity.getTaiKhoan() == null) {
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out);

                        fragmentTransaction.replace(R.id.fragmentContainerView, new NonUserProfileFragment());
                        fragmentTransaction.addToBackStack("Fragment home");
                        fragmentTransaction.commit();
                    } else if (mainActivity.getTaiKhoan().getData().getAddress() == null || mainActivity.getTaiKhoan().getData().getSdt() == null) {
                        Toast.makeText(getActivity(), "Vui lòng nhập thông tin ", Toast.LENGTH_SHORT).show();
                    } else {
                        // tien hanh thanh toan
                        Methods methods = retrofitClient.getRetrofit().create(Methods.class);
                        Account account = mainActivity.getTaiKhoan();
                        Call<ResultModel> call = methods.Payment(account);
                        call.enqueue(new Callback<ResultModel>() {
                            @Override
                            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                                if (!response.body().isStatus())
                                    return;
                                mainActivity.cartItems.clear();
                                CartProductAdapter adapter =
                                        new CartProductAdapter(
                                                getActivity(),
                                                R.layout.cart_product,
                                                mainActivity.cartItems, mainActivity.listProduct, new CartProductAdapter.OnClickListener() {
                                            @Override
                                            public void onClick(float data) {
                                                TongTien = 0;
                                            }
                                        }
                                        );
                                cart_list.setAdapter(adapter);
                                ShowMessage();
                            }

                            @Override
                            public void onFailure(Call<ResultModel> call, Throwable t) {
                                Toast.makeText(getActivity(), "đặt hàng không thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });

            TT_Momo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestPayment();
                }
            });
        }

        return view;
    }

    private void UpdateCart() {
        Methods methods = retrofitClient.getRetrofit().create(Methods.class);
        Call<ResultModel> call = methods.InsertCart(mainActivity.cartItems);
        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {

            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {

            }
        });
    }

    private void requestPayment() {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);


        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", TongTien); //Kiểu integer
        eventValue.put("orderId", "orderId123456789"); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue.put("orderLabel", "Mã đơn hàng"); //gán nhãn

        //client Optional - bill info
        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn
        eventValue.put("fee", fee); //Kiểu integer
        eventValue.put("description", description); //mô tả đơn hàng - short description

        //client extra data
        eventValue.put("requestId", merchantCode + "merchant_billId_" + System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);
        //Example extra data
        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", "CGV Cresent Mall");
            objExtraData.put("screen_code", 0);
            objExtraData.put("screen_name", "Special");
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
            objExtraData.put("movie_format", "2D");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eventValue.put("extraData", objExtraData.toString());

        eventValue.put("extra", "");
        AppMoMoLib.getInstance().requestMoMoCallBack(getActivity(), eventValue);


    }


    private void AnhXa() {
        TT_Momo = view.findViewById(R.id.TT_Momo);
        methods = retrofitClient.getRetrofit().create(Methods.class);
        btn_back_product = view.findViewById(R.id.btn_back_product);
        TongTien1 = view.findViewById(R.id.TongTien);
        cart_list = view.findViewById(R.id.cart_list);
        button = view.findViewById(R.id.checkout);
        btn_back_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void InitList() {
        GetDataFood();
        MainActivity mainActivity = (MainActivity) getActivity();
        cartItems = mainActivity.cartItems;
    }

    private void GetDataFood() {
        if (mainActivity.listProduct == null) {

            Call<FoodModel> call = methods.getFood();
            call.enqueue(new Callback<FoodModel>() {
                @Override
                public void onResponse(Call<FoodModel> call, Response<FoodModel> response) {
                    mainActivity.listProduct = response.body().getData();
                }

                @Override
                public void onFailure(Call<FoodModel> call, Throwable t) {
                }

            });
        }
    }


    public void ShowMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Cám ơn bạn");
        builder.setTitle("Bạn đã đặt hàng thành công");
        builder.setPositiveButton("OK", null);
        builder.setCancelable(true);
        builder.create().show();
    }


    @Override
    public void ChuyenTongTien(float Tien) {
    }
}