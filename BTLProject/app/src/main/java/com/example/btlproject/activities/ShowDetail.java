package com.example.btlproject.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btlproject.API.APIService;
import com.example.btlproject.R;
import com.example.btlproject.models.Cart.CartItem;
import com.example.btlproject.models.Cart.CartResponse;
import com.example.btlproject.models.MenuItems;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowDetail extends AppCompatActivity {
    ImageView imgFoodPic, btnplus, btnminus;
    TextView btnAddToCart;
    TextView txtPrice, txtTotalPrice, txtNumOrder, txtDes,txtTitle;
    private int numberOrder  = 1;
    DecimalFormat formatter = new DecimalFormat("###,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        InnitView();
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        MenuItems menuItem = (MenuItems) bundle.get("objectmenu");
        String token = bundle.getString("token");

        //imgFoodPic.setImageResource(menuItem.getImg());
        String url = menuItem.getImageUrl().replace("\\", "/");
        String newString = url.replaceFirst("^/", "");

        try {
            InputStream inputStream = getAssets().open(newString);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            imgFoodPic.setImageBitmap(bitmap);
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        txtPrice.setText(formatter.format(menuItem.getPrice())+" VND");
        txtTitle.setText(menuItem.getName());
        txtTotalPrice.setText(formatter.format(menuItem.getPrice())+" VND");
        txtDes.setText(menuItem.getDescription());
        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOrder = numberOrder +1;
                txtNumOrder.setText(String.valueOf(numberOrder));
                txtTotalPrice.setText(formatter.format(numberOrder* menuItem.getPrice())+" VND");
            }
        });
        btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numberOrder>1) {
                    numberOrder = numberOrder - 1;
                }
                txtNumOrder.setText(String.valueOf(numberOrder));
                txtTotalPrice.setText(formatter.format(numberOrder* menuItem.getPrice())+" VND");
            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int menuitemID = menuItem.getId();
                APIService.apiService.update_quanity("Bearer "+token, menuitemID,Integer.parseInt(txtNumOrder.getText().toString())).enqueue(new Callback<CartResponse>() {
                    @Override
                    public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                        List<CartItem> mlCartItem = response.body().getResult().getCartItems();
                        if (mlCartItem == null){
                            Toast.makeText(ShowDetail.this, "Add to cart fail", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ShowDetail.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CartResponse> call, Throwable t) {

                    }
                });
            }
        });
    }
    private void InnitView(){
        imgFoodPic = findViewById(R.id.foodPic);
        btnplus = findViewById(R.id.btn_plus);
        btnminus = findViewById(R.id.btn_minus);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);
        txtPrice = findViewById(R.id.txt_price);
        txtTotalPrice = findViewById(R.id.txt_total_price);
        txtNumOrder = findViewById(R.id.txt_number_order);
        txtDes = findViewById(R.id.txt_descroption);
        txtTitle = findViewById(R.id.txt_title);
    }
}