package com.example.btlproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btlproject.API.APIService;
import com.example.btlproject.R;
import com.example.btlproject.models.Category;
import com.example.btlproject.models.MenuItems;
import com.example.btlproject.models.ResponsedData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.BookViewHolder>{
    private Context context;
    private List<Category> categoryList;
    private OnClickCategoryAdapter onClickCategoryAdapter;
    public interface OnClickCategoryAdapter{
        void onClickCategory(int id);
    }

    public CategoryAdapter(Context context, OnClickCategoryAdapter onClickCategoryAdapter) {
        this.context = context;
        this.onClickCategoryAdapter = onClickCategoryAdapter;
    }

    public void setData(List<Category> list){
        this.categoryList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_horizontal_item, parent, false);
        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Category book = categoryList.get(position);
        if (book == null) {
            return;
        }
        //holder.img.setImageResource(book.getImg());
        //Glide.with(context).load(book.getImg()).into(holder.img);
        holder.img.setImageResource(book.getImg());
        holder.title.setText(book.getTitle());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickCategoryAdapter != null){
                    onClickCategoryAdapter.onClickCategory(book.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(categoryList != null){
            return categoryList.size();
        }
        return 0;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView title;
        private CardView cardView;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_hor);
            title = itemView.findViewById(R.id.text_hor);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }
}
