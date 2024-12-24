package com.example.btlproject.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlproject.API.APIService;
import com.example.btlproject.R;
import com.example.btlproject.adapter.HistoryAdapter;
import com.example.btlproject.models.ResponsedData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryOrder extends AppCompatActivity {
    RecyclerView recycleOrder;
    private HistoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recycleOrder = findViewById(R.id.recycle_order);
        String token = getIntent().getStringExtra("tokenhistory");
        adapter = new HistoryAdapter(this,token);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);

        recycleOrder.setLayoutManager(linearLayoutManager);

        CallApiGetHistoryOrder(token);

    }
    private void CallApiGetHistoryOrder(String token){
        APIService.apiService.getOder("Bearer "+token).enqueue(new Callback<ResponsedData<List<com.example.btlproject.models.HistoryOrder>>>() {
            @Override
            public void onResponse(Call<ResponsedData<List<com.example.btlproject.models.HistoryOrder>>> call, Response<ResponsedData<List<com.example.btlproject.models.HistoryOrder>>> response) {
                //Toast.makeText(HistoryOrder.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                List<com.example.btlproject.models.HistoryOrder> mlOder = response.body().getResult();
                adapter.setData(mlOder);
                recycleOrder.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponsedData<List<com.example.btlproject.models.HistoryOrder>>> call, Throwable t) {
                Toast.makeText(HistoryOrder.this, "That bai", Toast.LENGTH_SHORT).show();
            }
        });
    }
}