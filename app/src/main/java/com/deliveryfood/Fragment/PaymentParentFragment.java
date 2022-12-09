package com.deliveryfood.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.DeliveryFood.lib.Model.DiscountModel;
import com.DeliveryFood.lib.Model.ResultModel;
import com.DeliveryFood.lib.Repository.Methods;
import com.DeliveryFood.lib.retrofitClient;
import com.deliveryfood.MainActivity;
import com.deliveryfood.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.momo.momo_partner.AppMoMoLib;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentParentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentParentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button checkout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView TongTien;
    private Spinner PhuongThucTT;
    private float GiaGoc;
    private TextView TongTienCu;
    MainActivity mainActivity;
    private Spinner chonMaGiamGia;
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "dtri2001";
    private String merchantCode = "MOMOHHFF20220602";
    private String merchantNameLabel = "Nhà cung cấp";
    private String description = "Thanh toán dịch vụ tại Delivery Food";


    public PaymentParentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaymentParentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentParentFragment newInstance(String param1, String param2) {
        PaymentParentFragment fragment = new PaymentParentFragment();
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
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.PaymentContainer, new PaymentFragment());
        fragmentTransaction.addToBackStack("Fragment home");
        fragmentTransaction.commit();
        View view = inflater.inflate(R.layout.fragment_payment_parent, container, false);
        TongTien = view.findViewById(R.id.TongTien);
        PhuongThucTT = view.findViewById(R.id.PhuongThucTT);
        chonMaGiamGia = view.findViewById(R.id.chonMaGiamGia);
        TongTienCu = view.findViewById(R.id.TongTienCu);
        mainActivity = (MainActivity) getActivity();
        final Activity activity = getActivity();
        getDiscount();
        String[] phuongThucTT = {"Chọn phương thức TT", "TT khi nhan hang", "TT bằng Momo"};
        PhuongThucTT.setAdapter(new ArrayAdapter<>(getContext(), com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, phuongThucTT));
        GiaGoc = mainActivity.tongTien;
        TongTien.setText(String.valueOf(GiaGoc));
        checkout = view.findViewById(R.id.checkout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // thanh toán
                String PTTT = PhuongThucTT.getSelectedItem().toString();
                if ("Chọn phương thức thanh toán".equals(PTTT)) {
                    // thong bao chon pttt
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                    builder1.setMessage("Vui lòng chọn phương thức thanh toán");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else {
                    if ("Momo".equals(PTTT)) {
                        requestPayment();
                    } else if ("Khi nhan hang".equals(PTTT))
                        TTBinhThuong();
                    Fragment fragment;
                    fragment = new SuccessFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out);
                    fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
                    fragmentTransaction.addToBackStack("Fragment home");
                    fragmentTransaction.commit();
                }
            }
        });

        chonMaGiamGia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // tinh toan tong tien
                if (position != 0) {
                    int index = position - 1;
                    Integer percent = mainActivity.getDiscountModel().data.get(index).getPercent();
                    if (percent != 0) {
                        TongTienCu.setVisibility(View.VISIBLE);
                        TongTienCu.setText(String.valueOf(GiaGoc));
                        SpannableString noidungspanned = new SpannableString(String.valueOf(GiaGoc));
                        noidungspanned.setSpan(new StrikethroughSpan(), 0, noidungspanned.length(), 0);
                        TongTienCu.setMovementMethod(LinkMovementMethod.getInstance());
                        TongTienCu.setText(noidungspanned);
                        float temp = GiaGoc - (GiaGoc * percent);
                        TongTien.setText(String.valueOf(temp));
                    }
                }
            }
        });
        return view;
    }

    private void TTBinhThuong() {
        Methods methods = retrofitClient.getRetrofit().create(Methods.class);
        Call<ResultModel> callGetDiscout = methods.Payment(mainActivity.getTaiKhoan().data, mainActivity.getToken());
        callGetDiscout.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {

            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
            }
        });

    }

    private void getDiscount() {
        Methods methods = retrofitClient.getRetrofit().create(Methods.class);
        Call<DiscountModel> callGetDiscout = methods.GetDiscount();
        callGetDiscout.enqueue(new Callback<DiscountModel>() {
            @Override
            public void onResponse(Call<DiscountModel> call, Response<DiscountModel> response) {
                if (response.body().isStatus()) {
                    mainActivity.setDiscountModel(response.body());
                    List<String> Ma = new ArrayList<>();
                    Ma.add("Chọn mã giảm giá");
                    for (DiscountModel.Data temp : mainActivity.getDiscountModel().data)
                        Ma.add(temp.getCode());
                    chonMaGiamGia.setAdapter(new ArrayAdapter<>(getContext(), com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, Ma));
                }
            }

            @Override
            public void onFailure(Call<DiscountModel> call, Throwable t) {
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
        eventValue.put("amount", Integer.parseInt(TongTien.getText().toString())); //Kiểu integer
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
}