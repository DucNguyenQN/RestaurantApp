package com.example.btlproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btlproject.API.APIService;
import com.example.btlproject.MainActivity;
import com.example.btlproject.R;
import com.example.btlproject.models.LoginRequest;
import com.example.btlproject.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView txtregister, txttest;
    Button btnlogin;
    EditText edtUsername, edtPassWord, edttest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                moveTaskToBack(true);
            }
        });
        InnitWigets();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(LoginActivity.this, edtUsername.getText().toString(), Toast.LENGTH_SHORT).show();
                if(edtUsername.getText().toString().trim().isEmpty() || edtPassWord.getText().toString().trim().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    LoginRequest loginRequest = new LoginRequest(edtUsername.getText().toString(), edtPassWord.getText().toString());
                    APIService.apiService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            LoginResponse loginResponse = response.body();
                            if (loginResponse != null && response.isSuccessful()) {
                                if (loginResponse.getStatusCode() == 200) {
                                    //Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("user", loginResponse);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Username or Password wrong", Toast.LENGTH_SHORT).show();
                                edtUsername.requestFocus();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                        }
                    });
                }
            }
        });
        txtregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistationActivity.class));
            }
        });
    }
    private void InnitWigets(){
        btnlogin = findViewById(R.id.btnLogin);
        txtregister = findViewById(R.id.txtregister);
        edtUsername =  findViewById(R.id.edtUser);
        edtPassWord = findViewById(R.id.edtPass);
        txttest = findViewById(R.id.textView2);
    }

}