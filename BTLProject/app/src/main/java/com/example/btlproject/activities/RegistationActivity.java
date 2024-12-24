package com.example.btlproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btlproject.API.APIService;
import com.example.btlproject.R;
import com.example.btlproject.models.Register;
import com.example.btlproject.models.ResultResponse;

import java.util.concurrent.Delayed;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistationActivity extends AppCompatActivity {
    TextView txtlogin;
    EditText edtUsername, edtName, edtPassword, edtPhone, edtAddress, edtCity;
    Button btnregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        InnitWigets();
        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistationActivity.this, LoginActivity.class));
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtName.getText().toString().trim().isEmpty() || edtUsername.getText().toString().trim().isEmpty()
                || edtPassword.getText().toString().trim().isEmpty() || edtPhone.getText().toString().trim().isEmpty()
                || edtAddress.getText().toString().trim().isEmpty() || edtCity.getText().toString().trim().isEmpty()) {
                    Toast.makeText(RegistationActivity.this, "Vui lòng nhập đầy đủ thông tin đăng kí", Toast.LENGTH_SHORT).show();
                } else {
                    Register register = new Register(edtUsername.getText().toString(),
                            edtName.getText().toString(),
                            edtPassword.getText().toString(),
                            edtPhone.getText().toString(),
                            "customer",
                            edtAddress.getText().toString(),
                            edtCity.getText().toString(),
                            "VIỆT NAM");
                    APIService.apiService.register(register).enqueue(new Callback<ResultResponse>() {
                        @Override
                        public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                            Toast.makeText(RegistationActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ResultResponse> call, Throwable t) {

                        }
                    });
                }
            }
        });

    }
    private void InnitWigets(){
        txtlogin = findViewById(R.id.txtlogin);
        edtUsername = findViewById(R.id.regisUser);
        edtName = findViewById(R.id.regisName);
        edtPassword = findViewById(R.id.regisPassword);
        edtPhone = findViewById(R.id.regisPhone);
        edtAddress = findViewById(R.id.regisAddress);
        edtCity = findViewById(R.id.regisCity);
        btnregister = findViewById(R.id.btnregister);
    }
}