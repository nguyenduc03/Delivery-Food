package com.deliveryfood.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.DeliveryFood.lib.Model.AccountInsertModel;
import com.DeliveryFood.lib.Repository.Methods;
import com.DeliveryFood.lib.retrofitClient;
import com.deliveryfood.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    TextView  name, phone , email,address;
    EditText password;
    Button btn_go_DangNhap;
    private View result;
    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DangKiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        result = inflater.inflate(R.layout.fragment_register, container, false);;
        anhxa();
        Button btn = result.findViewById(R.id.dangki);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solve();
            }
        });

        btn_go_DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new fragment_login();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
                fragmentTransaction.addToBackStack("Fragment home");
                fragmentTransaction.commit();
            }
        });
        // Inflate the layout for this fragment
        return result ;
    }

    public void anhxa(){
        password = result.findViewById(R.id.password);
        name = result.findViewById(R.id.fullname);
        phone = result.findViewById(R.id.phone);
        btn_go_DangNhap = result.findViewById(R.id.btn_go_DangNhap);


    }

    public void solve() {
        Methods methods = retrofitClient.getRetrofit().create(Methods.class);
        System.out.println(password.getText().toString().trim());
        AccountInsertModel Account = new AccountInsertModel();

        Account.setName( name.getText().toString());
        Account.setPassword(password.getText().toString());
        Account.setSDT(phone.getText().toString());
        Call<String> call = methods.GetCode(Account);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){

                }
                Fragment fragment = new ConfimFragment(Account,response.body());
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
                fragmentTransaction.addToBackStack("Fragment home");
                fragmentTransaction.commit();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }
}