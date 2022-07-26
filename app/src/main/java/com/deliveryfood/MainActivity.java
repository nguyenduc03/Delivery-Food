package com.deliveryfood;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.DeliveryFood.lib.Entities.Cart;
import com.DeliveryFood.lib.Entities.Category;
import com.DeliveryFood.lib.Entities.Food;
import com.DeliveryFood.lib.Entities.Jwt;
import com.DeliveryFood.lib.Interface.IChuyenData;
import com.DeliveryFood.lib.Model.Account;
import com.DeliveryFood.lib.Model.DiscountModel;
import com.DeliveryFood.lib.Model.FoodModel;
import com.DeliveryFood.lib.Model.ToppingModel;
import com.DeliveryFood.lib.Model.UsertModel;
import com.DeliveryFood.lib.Repository.Methods;
import com.DeliveryFood.lib.retrofitClient;
import com.deliveryfood.Fragment.BuyWithFriendFragment;
import com.deliveryfood.Fragment.CartFragment;
import com.deliveryfood.Fragment.Fragment_detail;
import com.deliveryfood.Fragment.HomeFragment;
import com.deliveryfood.Fragment.NonUserProfileFragment;
import com.deliveryfood.Fragment.Profile_Fragment;
import com.deliveryfood.Fragment.fragment_login;
import com.deliveryfood.Fragment.fragmentproduct;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import lombok.Data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.momo.momo_partner.AppMoMoLib;

@Data
public class MainActivity extends AppCompatActivity implements IChuyenData, Serializable {

    public MainActivity() {

    }
    public float tongTien;
    public ChipNavigationBar ChipNavigationBar;
    Context context;
    public ImageButton btnBuyWithFr;
    public Account taiKhoan;
    public TedBottomPicker tedBottomPicker;
    public List<Cart> cartItems;
    public String token;
    private String Code;
    private DiscountModel discountModel;
    public boolean IsGoogle;
    public Jwt jwt;
    private String QRCode;

    public List<ToppingModel.Data> getToppings() {
        return toppings;
    }

    public void setToppings(List<ToppingModel.Data> toppings) {
        this.toppings = toppings;
    }

    protected List<ToppingModel.Data> toppings;
    public Account getTaiKhoan() {
        return taiKhoan;
    }

    public List<FoodModel.Data> listProduct;

    public List<FoodModel.Data> getListProduct() {
        return this.listProduct;
    }

    public List<Category> categorylist;

    public void setListProduct(List<FoodModel.Data> listProduct) {
        this.listProduct = listProduct;
    }

    public void setTaiKhoan(Account taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public MainActivity(Account taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        listProduct = new ArrayList<FoodModel.Data>();
        ChipNavigationBar = findViewById(R.id.menu_Navigation);
        ChipNavigationBar.setItemSelected(R.id.fragment_home, true);
        btnBuyWithFr = findViewById(R.id.btnBuyWithFriend);
        btnBuyWithFr.setVisibility(View.VISIBLE);
        cartItems = new ArrayList<Cart>();
        categorylist = new ArrayList<>();
        QRCode="";
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, new HomeFragment());
        fragmentTransaction.addToBackStack("Fragment home");
        fragmentTransaction.commit();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            token = bundle.getString("jwt");
            taiKhoan = (Account) bundle.getSerializable("Account");
            if(taiKhoan!= null)
            {
                UsertModel user = new UsertModel(taiKhoan.getData().getSdt(),taiKhoan.getData().getPassword());
                Methods methods = retrofitClient.getRetrofit().create(Methods.class);
                Call<Jwt> call = methods.getToken(user);
                call.enqueue(new Callback<Jwt>() {
                    @Override
                    public void onResponse(Call<Jwt> call, Response<Jwt> response) {
                        if(!response.body().getToken().isEmpty())
                        {
                            jwt=response.body();
                        }
                    }

                    @Override
                    public void onFailure(Call<Jwt> call, Throwable t) {

                    }
                });
            }
            IsGoogle = bundle.getBoolean("isGoogle");
            //Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
        }
        MenuClick();
        btnBuyWithFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(taiKhoan == null ){
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerView, new NonUserProfileFragment());
                    fragmentTransaction.addToBackStack("Fragment home");
                    fragmentTransaction.commit();
                }else {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerView, new BuyWithFriendFragment());
                    fragmentTransaction.addToBackStack("Fragment home");
                    fragmentTransaction.commit();
                }
            }
        });
    }


    private void MenuClick() {
        ChipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                Fragment fragment = null;
                switch (i) {
                    case R.id.fragment_home:
                        btnBuyWithFr.setVisibility(View.VISIBLE);
                        fragment = new HomeFragment();
                        break;
                    case R.id.menu_nav_buy:
                        fragment = new CartFragment();
                        break;
                    case R.id.fragmentproduct:
                        btnBuyWithFr.setVisibility(View.VISIBLE);
                        fragment = new fragmentproduct();
                        break;
                    case R.id.menu_nav_user:
                        if (taiKhoan == null)
                            fragment = new fragment_login();
                        else
                            fragment = new Profile_Fragment(taiKhoan);
                        break;
                }
                fragmentTransaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void ChuyenData(FoodModel.Data p) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("product", p);
        Fragment_detail temp = new Fragment_detail();
        temp.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, temp).commit();
        fragmentTransaction.addToBackStack("Fragment Detail");
    }


    @Override
    public void PassDetailBill(Food bill) {
        Bundle bundle = new Bundle();
        Serializable x = (Serializable) bill;
        bundle.putSerializable("bill", x);
        Fragment_detail temp = new Fragment_detail();
        temp.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, temp).commit();
        fragmentTransaction.addToBackStack("Fragment Invoice");
    }

    @Override
    public void ChuyenTongTien(float Tien) {
        // TextView Text = findViewById(R.id.txt_tongTien);
        //  Text.setText(Float.toString(Tien));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if (data != null) {
                if (data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
                    ShowMessage("Thành Công");
                    String token = data.getStringExtra("data"); //Token response
                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if (env == null) {
                        env = "app";
                    }

                    if (token != null && !token.equals("")) {
                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
                        // IF Momo topup success, continue to process your order
                    } else {
                        ShowMessage("Không thành Công");
                    }
                } else if (data.getIntExtra("status", -1) == 1) {
                    //TOKEN FAIL
                    String message = data.getStringExtra("message") != null ? data.getStringExtra("message") : "Thất bại";
                    ShowMessage("Không thành Công");
                } else if (data.getIntExtra("status", -1) == 2) {
                    //TOKEN FAIL
                    ShowMessage("Không thành Công");
                } else {
                    //TOKEN FAIL
                    ShowMessage("Không thành Công");
                }
            } else {
                ShowMessage("Không thành Công");
            }
        } else {
            ShowMessage("Không thành Công");
        }
    }

    public void ShowMessage(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Cám ơn bạn");
        builder.setTitle(text);
        builder.setPositiveButton("OK", null);
        builder.setCancelable(true);
        builder.create().show();
    }
}