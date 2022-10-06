package com.DeliveryFood.lib.Repository;


import com.DeliveryFood.lib.Model.DiscountModel;
import com.DeliveryFood.lib.Model.FoodModel;
import com.DeliveryFood.lib.Entities.Cart;
import com.DeliveryFood.lib.Entities.Category;
import com.DeliveryFood.lib.Entities.Food;
import com.DeliveryFood.lib.Model.Account;
import com.DeliveryFood.lib.Model.AccountInsertModel;
import com.DeliveryFood.lib.Model.CategoryModel;
import com.DeliveryFood.lib.Model.Invoice;
import com.DeliveryFood.lib.Model.InvoiceDetailModel;
import com.DeliveryFood.lib.Model.InvoiceModel;
import com.DeliveryFood.lib.Model.ResultModel;
import com.DeliveryFood.lib.Model.ToppingModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Methods {
    // get food list
    @GET("api/Food/get-popular-food-list")
    Call<FoodModel> getPopularFood();

    @GET("api/Food/get-top-food")
    Call<FoodModel> getTopFood();

    @POST("api/Cart/insert-list-food")
    Call<ResultModel> InsertCart(@Body List<Cart> list);

    @POST("api/Food/Get-more-food")
    Call<FoodModel> getMoreFood(@Body FoodModel.Data LastFood);


    @POST("api/Cart/insert-food")
    Call<ResultModel> InsertFoodCart(@Body Cart cart);

    @GET("api/Cart/insert-food")
    Call<FoodModel> getTopFoodDiscount();


    @GET("api/Food/get-all-food-list")
    Call<FoodModel> getAllFood();

    @GET("api/Food/get-all-food-list")
    Call<FoodModel> getFood();

    @POST ("api/Food/get-food-byID")
    Call<Food> getFoodByID(@Body com.DeliveryFood.lib.Model.Food food);

    @GET("api/Food/get-Recent-food")
    Call<FoodModel> getNewFood();

    @POST("api/Cart/payment")
    Call<ResultModel> Payment(@Body Account account);


    @POST("api/Food/get-food-in-Category")
    Call<FoodModel> getFoodByCategory(@Body Category category);

    @POST("api/Account/insert-Account")
    Call<ResultModel> signup(@Body AccountInsertModel Account);

    @POST("api/ConfimSDT/Create_Code")
    Call<String> GetCode(@Body AccountInsertModel Account);

    @POST("api/Cart/insert-list-food")
    Call<ResultModel> CartInsertFood (@Body List<Cart> Cart);

    @POST("api/InvoiceDetail/get-Invoice-Detail")
    Call<InvoiceDetailModel> GetInvoiceByInvoice(@Body Invoice invoice);

    @POST("api/Invoice/get-List-Invoice")
    Call<InvoiceModel> GetInvoiceList(@Body AccountInsertModel account);

    @POST("api/Account/update-data")
    Call<ResultModel> UpdateAccount(@Body AccountInsertModel account);

    @POST("api/Account/log-in")
    Call<Account> login(@Body AccountInsertModel accountInsertModel);

    @GET("api/Category/get-category-list")
    Call<CategoryModel> GetCategoryList ();

    @POST("api/Food/get-food-byName")
    Call<FoodModel> FindFood(@Body com.DeliveryFood.lib.Model.Food food);

    @GET("api/Discount/get-discount-available")
    Call<DiscountModel> GetDiscount();

    @POST("api/Topping/get-Topping-list")
    Call<ToppingModel> GetTopping(@Body FoodModel.Data food);
}
