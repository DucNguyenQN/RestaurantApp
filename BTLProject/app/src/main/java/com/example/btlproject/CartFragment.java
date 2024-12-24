package com.example.btlproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btlproject.API.APIService;
import com.example.btlproject.adapter.CartAdapter;
import com.example.btlproject.models.Cart.CartItem;
import com.example.btlproject.models.Cart.CartResponse;
import com.example.btlproject.models.Delivery.DeliveryInfo;
import com.example.btlproject.models.Delivery.UserDeliveryInfo;
import com.example.btlproject.models.LoginResponse;
import com.example.btlproject.models.ResponsePayment;
import com.example.btlproject.models.ResponsedData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment implements CartAdapter.OnCartQuantityChangeListener{
    private RecyclerView recycle_cart;
    private CartAdapter adapter;
    ConstraintLayout btnThanhToan;
    private TextView txt_cart_total, txt_item_total;
    String token;
    List<CartItem> mlCartItem;
    String tthai_ttoan, tongtien;
    DecimalFormat formatter = new DecimalFormat("###,###");
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }
    private final ActivityResultLauncher<Intent> paymentLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->{
        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
            String paymentResult = result.getData().getStringExtra("payment_result");
            if ("success".equals(paymentResult)) {
                Toast.makeText(getContext(), "Thanh toán thành công", Toast.LENGTH_SHORT).show();
            }
        } else if (result.getResultCode() == Activity.RESULT_CANCELED && result.getData() != null) {
            String paymentResult = result.getData().getStringExtra("payment_result");
            if ("canceled".equals(paymentResult)) {
                Toast.makeText(getContext(), "Thanh toán đã bị hủy", Toast.LENGTH_SHORT).show();
            } else if ("error".equals(paymentResult)) {
                String errorMessage = result.getData().getStringExtra("error_message");
                Toast.makeText(getContext(), "Lỗi thanh toán: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        }
    });
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginResponse loginResponse = (LoginResponse) getArguments().getSerializable("info");
        String userID = loginResponse.getResult().getUser().getId();
        token = loginResponse.getResult().getToken();
        //Toast.makeText(getContext(), userid, Toast.LENGTH_SHORT).show();
        txt_cart_total = view.findViewById(R.id.txt_cart_total);
        txt_item_total = view.findViewById(R.id.txt_item_total);
        btnThanhToan = view.findViewById(R.id.btnThanhToan);

        recycle_cart = view.findViewById(R.id.recycle_cart);

        adapter = new CartAdapter(getContext(),token, (CartAdapter.OnCartQuantityChangeListener) this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);

        recycle_cart.setLayoutManager(linearLayoutManager);

        callApigetCart(token);
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mlCartItem.size() == 0){
                    Toast.makeText(getContext(), "Vui lòng chọn sản phẩm trước khi thanh toán", Toast.LENGTH_SHORT).show();
                }else {
                    Open_Dialog(Gravity.CENTER, token, userID, tongtien);
                }
            }
        });
    }


    private void callApigetCart(String token){
       APIService.apiService.cart("Bearer "+token).enqueue(new Callback<CartResponse>() {
           @Override
           public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
               mlCartItem = response.body().getResult().getCartItems();
               txt_cart_total.setText(formatter.format(response.body().getResult().getCartTotal())+" VND");
               txt_item_total.setText(response.body().getResult().getItemsTotal()+"");
               tongtien = response.body().getResult().getCartTotal()+"";
               //Toast.makeText(getContext(), "Thanh cong", Toast.LENGTH_SHORT).show();
               adapter.setData(mlCartItem);
               recycle_cart.setAdapter(adapter);
           }

           @Override
           public void onFailure(Call<CartResponse> call, Throwable t) {

           }
       });
    }

    @Override
    public void onCartQuantityChanged() {
        callApigetCart(token);
    }
    private void Open_Dialog(int gravity, String token, String userID, String total){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog);

        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windownatributes = window.getAttributes();
        windownatributes.gravity = gravity;
        window.setAttributes(windownatributes);

        ArrayList<String> arrayThanhToan = new ArrayList<String>();
        arrayThanhToan.add("Thanh toán khi nhận hàng");
        arrayThanhToan.add("Thanh toán bằng ZaloPay");
        Spinner spinner, order_payment;
        EditText order_name, order_phone, order_address, order_city;
        Button btnhuy, btn_thanhtoan;

        order_name = dialog.findViewById(R.id.order_name);
        order_phone = dialog.findViewById(R.id.order_phone);
        order_address = dialog.findViewById(R.id.order_address);
        order_city = dialog.findViewById(R.id.order_city);
        order_payment = dialog.findViewById(R.id.order_payment);

        btnhuy = dialog.findViewById(R.id.btn_cancel);
        btn_thanhtoan = dialog.findViewById(R.id.btn_thanhtoan);

        spinner = dialog.findViewById(R.id.order_payment);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,arrayThanhToan);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tthai_ttoan =  arrayThanhToan.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(order_name.getText().toString().trim().isEmpty() || order_phone.getText().toString().trim().isEmpty() ||
                order_address.getText().toString().trim().isEmpty() || order_city.getText().toString().trim().isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    if (tthai_ttoan.equals("Thanh toán khi nhận hàng")){
                        DeliveryInfo deliveryInfo = new DeliveryInfo(order_name.getText().toString(),
                                                                    order_phone.getText().toString(),
                                                                    order_address.getText().toString(),
                                                                    order_city.getText().toString(),"VIET NAM");
                        UserDeliveryInfo userDeliveryInfo = new UserDeliveryInfo(userID, deliveryInfo);
                        APIService.apiService.order("Bearer "+token,userDeliveryInfo).enqueue(new Callback<ResponsedData<ResponsePayment>>() {
                            @Override
                            public void onResponse(Call<ResponsedData<ResponsePayment>> call, Response<ResponsedData<ResponsePayment>> response) {
                                if (response.body().getResult() == null){
                                    Toast.makeText(getContext(), "Đặt hàng thất bại", Toast.LENGTH_SHORT).show();

                                }else {
                                    Toast.makeText(getContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                                    callApigetCart(token);
                                    dialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponsedData<ResponsePayment>> call, Throwable t) {

                            }
                        });
                    }else{
                        Intent intent = new Intent(getContext(), MainActivity2.class);
                        intent.putExtra("total", total);
                        //startActivity(intent);
                        paymentLauncher.launch(intent);
                        dialog.dismiss();
                    }
                }
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }
}