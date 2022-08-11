package com.deliveryfood.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.DeliveryFood.lib.Entities.Cart;
import com.DeliveryFood.lib.Interface.IChuyenData;
import com.DeliveryFood.lib.Model.Account;
import com.DeliveryFood.lib.Model.AccountInsertModel;
import com.DeliveryFood.lib.Repository.Methods;
import com.DeliveryFood.lib.retrofitClient;
import com.deliveryfood.MainActivity;
import com.deliveryfood.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_login extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btn_dn ;
    private IChuyenData iChuyenData;
    private View view ;
    private EditText dn_UserName;
    private EditText dn_Password;
    Button btn_go_DangKi;
    public Account loginAccount;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN =001;
    public fragment_login() {

    }
    public static fragment_login newInstance(String param1, String param2) {
        fragment_login fragment = new fragment_login();
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
        view =  inflater.inflate(R.layout.fragment_login, container, false);
        Methods methods = retrofitClient.getRetrofit().create(Methods.class);
        anhXa();
        btn_go_DangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new RegisterFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
                fragmentTransaction.addToBackStack("Fragment home");
                fragmentTransaction.commit();
            }
        });
        btn_dn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AccountInsertModel temp = new AccountInsertModel();
                temp.setSDT(dn_UserName.getText().toString());
                temp.setPassword(dn_Password.getText().toString());
                Call<Account> call = methods.login(temp);
                call.enqueue(new Callback<Account>() {
                    @Override
                    public void onResponse(Call<Account> call, Response<Account> response) {
                        if(response.body().isStatus() ){
                            loginAccount= response.body();
                            Fragment fragment = new HomeFragment();
                            MainActivity mainActivity = (MainActivity) getActivity();
                            mainActivity.taiKhoan = (loginAccount);
                            updateCart(loginAccount);
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
                            fragmentTransaction.addToBackStack("Fragment home");
                            mainActivity.ChipNavigationBar.setItemSelected(R.id.fragment_home,true);
                            fragmentTransaction.commit();

                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("Tài khoản không hợp lệ");
                            builder.setTitle("Cảnh báo");
                            builder.setPositiveButton("OK", null);
                            builder.setCancelable(true);
                            builder.create().show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Account> call, Throwable t) {
                        Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(),gso);
//        btn_dnGG.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                switch (view.getId()) {
//                    case R.id.btn_dn2:
//                        signIn();
//                        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
//                        if (acct != null) {
//                            Call<googleAccount> call = methods.googleGet(acct.getEmail());
//                            call.enqueue(new Callback<googleAccount>() {
//                                @Override
//                                public void onResponse(Call<googleAccount> call, Response<googleAccount> response) {
//                                    Intent intent = new Intent(getActivity(), MainActivity.class);
//                                    userResponse userResponse = new userResponse();
//                                    userResponse.setFullname(acct.getDisplayName());
//                                    userResponse.setEmail(response.body().getEmail());
//                                    userResponse.setAddress(response.body().getAddress());
//                                    userResponse.setPhone(response.body().getPhone());
//                                    Bundle bundle = new Bundle();
//                                    bundle.putSerializable("data",userResponse);
//                                    bundle.putBoolean("isGoogle",true);
//                                    intent.putExtras(bundle);
//                                    startActivity(intent);
//                                    getActivity().finish();
//                                }
//
//                                @Override
//                                public void onFailure(Call<googleAccount> call, Throwable t) {
//
//                                }
//                            });
//                        }
//                        break;
//                    // ...
//                }
//            }
//        });
//
        return view;
   }

    private void updateCart(Account loginAccount) {
        MainActivity mainActivity = (MainActivity) getActivity();
        List<Cart> list = mainActivity.cartItems;
        for (Cart temp:list
             ) {
            temp.setSDT(loginAccount.getData().getSdt());
        }

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void anhXa(){
        btn_dn=view.findViewById(R.id.btn_dn);
        iChuyenData = (IChuyenData) getActivity();
        dn_UserName = view.findViewById(R.id.dn_UserName);
        dn_Password = view.findViewById(R.id.dn_Password);
        btn_go_DangKi = view.findViewById(R.id.btn_go_DangKi);
    }


}