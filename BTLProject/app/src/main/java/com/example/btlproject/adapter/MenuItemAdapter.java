package com.example.btlproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btlproject.R;
import com.example.btlproject.activities.ShowDetail;
import com.example.btlproject.models.MenuItems;
import com.example.btlproject.models.MenuItems;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder>{
    private Context context;
    private List<MenuItems> menuItemList;
    private List<MenuItems> menuItemListFilter;
    private String token;

    public MenuItemAdapter(Context context, String token) {
        this.context = context;
        this.token = token;
    }
    public void setData(List<MenuItems> list){
        this.menuItemList = new ArrayList<>(list);
        this.menuItemListFilter = new ArrayList<>(list);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_item,parent, false);

        return new MenuItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemViewHolder holder, int position) {
        MenuItems menuItem = menuItemListFilter.get(position);
        if (menuItem == null){
            return;
        }
        holder.name.setText(menuItem.getName());
        //Glide.with(context).load(menuItem.getImageUrl()).into(holder.img);
        //holder.img.setImageResource(menuItem.getImg());
        String url = menuItem.getImageUrl().replace("\\", "/");
        String newString = url.replaceFirst("^/", "");

        try {
            InputStream inputStream = context.getAssets().open(newString);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            holder.img.setImageBitmap(bitmap);
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        holder.price.setText(decimalFormat.format(menuItem.getPrice())+ " VND");
        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGoToDetail(menuItem);
            }
        });
    }
    private void onClickGoToDetail(MenuItems menuItem){
        Intent intent = new Intent(context, ShowDetail.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("objectmenu",menuItem);
        bundle.putString("token",token);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    @Override
    public int getItemCount() {
        if(menuItemListFilter  != null){
            return menuItemListFilter .size();
        }
        return 0;
    }

    public class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView img;
        private TextView price;
        private ConstraintLayout layout_item;
        public MenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView16);
            img = itemView.findViewById(R.id.imageView);
            price = itemView.findViewById(R.id.txt_cart_title);
            layout_item = itemView.findViewById(R.id.layout_item);
        }
    }
    public void release(){
        context = null;
    }
    public void filter(String text) {
        //Toast.makeText(context, String.valueOf(menuItemList.size()), Toast.LENGTH_SHORT).show();
        menuItemListFilter.clear();
        //Toast.makeText(context, String.valueOf(menuItemListFilter.size()), Toast.LENGTH_SHORT).show();
        if (text.isEmpty()) {
            menuItemListFilter.addAll(menuItemList);
        }
        else {
            for (MenuItems menuItems : menuItemList) {
                if (menuItems.getName().toLowerCase().contains(text.toLowerCase())) {
                    menuItemListFilter.add(menuItems);
                }
            }
            //Toast.makeText(context, String.valueOf(menuItemListFilter.size()), Toast.LENGTH_SHORT).show();
        }
        notifyDataSetChanged();
    }
}
