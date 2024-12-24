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
import com.example.btlproject.adapter.HistoryBookingAdapter;
import com.example.btlproject.models.Booking.HistoryBoooking;
import com.example.btlproject.models.ResponsedData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryBooking extends AppCompatActivity {
    RecyclerView recycleBooking;
    private HistoryBookingAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history_booking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recycleBooking = findViewById(R.id.recycle_booking);
        String token = getIntent().getStringExtra("tokenhistory");
        adapter = new HistoryBookingAdapter(this,token);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);

        recycleBooking.setLayoutManager(linearLayoutManager);

        CallApiGetHistoryBooking(token);

    }

    private void CallApiGetHistoryBooking(String token) {
        APIService.apiService.getBooking("Bearer "+token).enqueue(new Callback<ResponsedData<List<HistoryBoooking>>>() {
            @Override
            public void onResponse(Call<ResponsedData<List<HistoryBoooking>>> call, Response<ResponsedData<List<HistoryBoooking>>> response) {
                //Toast.makeText(HistoryOrder.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                List<HistoryBoooking> mlOder = response.body().getResult();
                adapter.setData(mlOder);
                recycleBooking.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponsedData<List<HistoryBoooking>>> call, Throwable t) {

            }
        });
    }
}