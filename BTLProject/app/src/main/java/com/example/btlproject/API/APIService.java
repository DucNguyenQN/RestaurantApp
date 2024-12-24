package com.example.btlproject.API;

import com.example.btlproject.UserResponse;
import com.example.btlproject.models.Booking.BookingRequest;
import com.example.btlproject.models.Booking.BookingResponse;
import com.example.btlproject.models.Booking.HistoryBoooking;
import com.example.btlproject.models.Cart.CartRequest;
import com.example.btlproject.models.Cart.CartResponse;
import com.example.btlproject.models.Category;
import com.example.btlproject.models.Delivery.UserDeliveryInfo;
import com.example.btlproject.models.HistoryOrder;
import com.example.btlproject.models.LoginRequest;
import com.example.btlproject.models.LoginResponse;
import com.example.btlproject.models.MenuItems;
import com.example.btlproject.models.Register;
import com.example.btlproject.models.ResponsePayment;
import com.example.btlproject.models.ResponsedData;
import com.example.btlproject.models.ResultResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    APIService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.94:5148/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);

    @POST("api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("api/auth/register")
    Call<ResultResponse> register(@Body Register register);

    @GET("api/Category")
    Call<ResponsedData<List<Category>>> category();

    @GET("api/MenuItem")
    Call<ResponsedData<List<MenuItems>>> MenuItem();

    @GET("api/ShoppingCart")
    Call<CartResponse> cart(@Header("Authorization") String header);

    @POST("api/ShoppingCart")
    Call<CartResponse> update_quanity(@Header("Authorization") String header,
                                      @Query("menuItemId") int menuItemId,
                                      @Query("updateQuantity") int updateQuantity);

    @Multipart
    @POST("api/Booking")
    Call<BookingResponse> booking(@Header("Authorization") String header,
                                                       @Part("Name") RequestBody name,
                                                       @Part("PhoneNumber") RequestBody phoneNumber,
                                                       @Part("BookingDate") RequestBody bookingDate,
                                                       @Part("NumberOfGuests") RequestBody numberOfGuests,
                                                       @Part("SpecialRequest") RequestBody specialRequest);

    @GET("/api/MenuItem/category/{categoryId}")
    Call<ResponsedData<List<MenuItems>>> get_product_by_category(@Path("categoryId") int categoryId);

    @GET("api/User")
    Call<UserResponse> getUser(@Header("Authorization") String header,
                               @Query("id ") String id);

    @GET("api/Order")
    Call<ResponsedData<List<HistoryOrder>>> getOder(@Header("Authorization") String header);

    @GET("api/Booking")
    Call<ResponsedData<List<HistoryBoooking>>> getBooking(@Header("Authorization") String header);

    @POST("api/Order")
    Call<ResponsedData<ResponsePayment>> order(@Header("Authorization") String header,
                                               @Body UserDeliveryInfo userDeliveryInfo);

    
}
