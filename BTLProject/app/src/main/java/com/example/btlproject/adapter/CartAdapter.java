package com.example.btlproject.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlproject.API.APIService;
import com.example.btlproject.R;
import com.example.btlproject.models.Cart.CartItem;
import com.example.btlproject.models.Cart.CartRequest;
import com.example.btlproject.models.Cart.CartResponse;
import com.example.btlproject.models.Cart.MenuItem;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{
    private Context context;
    private List<CartItem> cartItem;
    private String token;
    private OnCartQuantityChangeListener onCartQuantityChangeListener;
    public interface  OnCartQuantityChangeListener {
        void onCartQuantityChanged();
    }

    public CartAdapter(Context context, String token, OnCartQuantityChangeListener listener) {
        this.context = context;
        this.token = token;
        this.onCartQuantityChangeListener = listener;
    }

    public void setData(List<CartItem> cart){
        this.cartItem = cart;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent, false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItem.get(position);
        MenuItem menuItem = item.getMenuItem();
        if(item == null){
            return;
        }
        holder.txt_cart_title.setText(menuItem.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        holder.txt_cart_price.setText(decimalFormat.format(menuItem.getPrice())+" VND");
        holder.txt_cart_quantity.setText(item.getQuantity()+"");

        String url = menuItem.getImageUrl().replace("\\", "/");
        String newString = url.replaceFirst("^/", "");

        try {
            InputStream inputStream = context.getAssets().open(newString);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            holder.img_cart.setImageBitmap(bitmap);
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        holder.img_cart_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numberOrder = item.getQuantity();
                int newnumberOrder = numberOrder + 1;
                holder.txt_cart_quantity.setText(newnumberOrder+"");
                item.setQuantity(newnumberOrder);
                APIService.apiService.update_quanity("Bearer "+token,item.getMenuItemId(),1).enqueue(new Callback<CartResponse>() {
                    @Override
                    public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                        //Toast.makeText(context, "thanh cong", Toast.LENGTH_SHORT).show();
                        if (response.isSuccessful()) {
                            // Gọi callback khi cập nhật thành công
                            if (onCartQuantityChangeListener != null) {
                                onCartQuantityChangeListener.onCartQuantityChanged();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CartResponse> call, Throwable t) {
                        Toast.makeText(context, "Loi", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.img_cart_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newQuantity = item.getQuantity();
                if (newQuantity > 1) {
                    newQuantity -= 1;
                    holder.txt_cart_quantity.setText(newQuantity + "");
                    item.setQuantity(newQuantity);

                    // Gọi API để cập nhật số lượng
                    APIService.apiService.update_quanity("Bearer "+token, item.getMenuItemId(), -1).enqueue(new Callback<CartResponse>() {
                        @Override
                        public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                            if (response.isSuccessful()) {
                                // Gọi callback khi cập nhật thành công
                                if (onCartQuantityChangeListener != null) {
                                    onCartQuantityChangeListener.onCartQuantityChanged();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<CartResponse> call, Throwable t) {
                            Toast.makeText(context, "Lỗi cập nhật số lượng", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(cartItem != null){
            return cartItem.size();
        }
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView txt_cart_title, txt_cart_price, txt_cart_quantity;
        ImageView img_cart, img_cart_plus, img_cart_minus;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_cart_title = itemView.findViewById(R.id.txt_cart_title);
            txt_cart_price = itemView.findViewById(R.id.txt_cart_price);
            txt_cart_quantity = itemView.findViewById(R.id.txt_cart_quantity);
            img_cart = itemView.findViewById(R.id.img_cart);
            img_cart_minus = itemView.findViewById(R.id.img_cart_minus);
            img_cart_plus = itemView.findViewById(R.id.img_cart_plus);
        }
    }
}
