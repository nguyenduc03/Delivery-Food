package com.deliveryfood.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.DeliveryFood.lib.Model.IMGModel;
import com.DeliveryFood.lib.Repository.Methods;
import com.DeliveryFood.lib.retrofitClient;
import com.deliveryfood.MainActivity;
import com.deliveryfood.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.Objects;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuyWithFriendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuyWithFriendFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageButton btnBack;
    private View view;
    private MainActivity mainActivity;
    private ChipNavigationBar menuQR;

    public BuyWithFriendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BuyWithFriendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BuyWithFriendFragment newInstance(String param1, String param2) {
        BuyWithFriendFragment fragment = new BuyWithFriendFragment();
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

    @SneakyThrows
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainActivity = (MainActivity) getActivity();
        view = inflater.inflate(R.layout.fragment_buy_with_friend, container, false);
        init();
        onClick();
        if (Objects.equals(mainActivity.getQRCode(), ""))
            getQRCode();
        Thread.sleep(200);
        Fragment fragment = new ScanFragment();
        menuQR.setItemSelected(R.id.Scan_QR, true);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out);
        fragmentTransaction.replace(R.id.ContainerView, fragment);
        fragmentTransaction.addToBackStack("Fragment home");
        fragmentTransaction.commit();
        return view;
    }

    private void getQRCode() {
        Methods methods = retrofitClient.getRetrofit().create(Methods.class);
        Call<IMGModel> callGetQR = methods.getQRCode(mainActivity.getTaiKhoan().data, "Bearer " + mainActivity.jwt.getToken());
        callGetQR.enqueue(new Callback<IMGModel>() {
            @Override
            public void onResponse(Call<IMGModel> call, Response<IMGModel> response) {
                if (response.body().isStatus()) {
                    mainActivity.setQRCode(response.body().getData());
                }
            }


            @Override
            public void onFailure(Call<IMGModel> call, Throwable t) {

            }
        });
    }

    private void onClick() {

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        menuQR.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment;
                if (i == R.id.Scan_QR) {
                    fragment = new ScanFragment();
                    menuQR.setItemSelected(R.id.Scan_QR, true);
                } else {
                    fragment = new MyQRFragment();
                    menuQR.setItemSelected(R.id.My_QR, true);
                }
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out);
                fragmentTransaction.replace(R.id.ContainerView, fragment);
                fragmentTransaction.addToBackStack("Fragment home");
                fragmentTransaction.commit();
            }
        });

        MainActivity mainActivity = (MainActivity) getContext();
        if (mainActivity.getTaiKhoan() != null) {

        }
    }

    private void init() {
        btnBack = view.findViewById(R.id.btnBack);
        menuQR = view.findViewById(R.id.menuQR);
        menuQR.setItemSelected(R.id.Scan_QR, true);
    }


}