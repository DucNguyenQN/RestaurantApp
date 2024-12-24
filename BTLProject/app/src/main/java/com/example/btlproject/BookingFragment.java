package com.example.btlproject;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.btlproject.API.APIService;
import com.example.btlproject.models.Booking.BookingRequest;
import com.example.btlproject.models.Booking.BookingResponse;
import com.example.btlproject.models.LoginResponse;
import com.example.btlproject.models.ResponsedData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingFragment extends Fragment {
    Button btn_booking;
    EditText edt_booking_name, edt_booking_phonenumber, edt_booking_date, edt_booking_numberofguest, edt_booking_special_request;
    Calendar mycalender = Calendar.getInstance();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookingFragment newInstance(String param1, String param2) {
        BookingFragment fragment = new BookingFragment();
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
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_booking = view.findViewById(R.id.btn_booking);
        edt_booking_name = view.findViewById(R.id.edt_booking_name);
        edt_booking_phonenumber = view.findViewById(R.id.edt_booking_phonenumber);
        edt_booking_date = view.findViewById(R.id.edt_booking_date);
        edt_booking_numberofguest = view.findViewById(R.id.edt_booking_numberofguest);
        edt_booking_special_request = view.findViewById(R.id.edt_special_request);

        LoginResponse loginResponse = (LoginResponse) getArguments().getSerializable("info");
        String token = loginResponse.getResult().getToken();
        edt_booking_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        mycalender.set(Calendar.YEAR,i );
                        mycalender.set(Calendar.MONTH,i1 );
                        mycalender.set(Calendar.DAY_OF_MONTH,i2 );

                        String myFormat = "dd-MMM-YYYY";
                        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                        edt_booking_date.setText(dateFormat.format(mycalender.getTime()));
                    }
                }, mycalender.get(Calendar.YEAR),
                        mycalender.get(Calendar.MONTH),
                        mycalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_booking_name.getText().toString().trim().isEmpty() ||
                    edt_booking_phonenumber.getText().toString().trim().isEmpty() ||
                    edt_booking_date.getText().toString().trim().isEmpty() ||
                    edt_booking_numberofguest.getText().toString().trim().isEmpty() ||
                    edt_booking_special_request.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhạp đầy đủ thông tin đặt bàn", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getContext(), "Nhan booking", Toast.LENGTH_SHORT).show();
                    RequestBody name = RequestBody.create(MediaType.parse("text/plain"), edt_booking_name.getText().toString());
                    RequestBody phoneNumber = RequestBody.create(MediaType.parse("text/plain"), edt_booking_phonenumber.getText().toString());
                    RequestBody bookingDate = RequestBody.create(MediaType.parse("text/plain"), edt_booking_date.getText().toString());
                    RequestBody numberOfGuests = RequestBody.create(MediaType.parse("text/plain"), edt_booking_numberofguest.getText().toString());
                    RequestBody specialRequest = RequestBody.create(MediaType.parse("text/plain"), edt_booking_special_request.getText().toString());
                    APIService.apiService.booking("Bearer " + token, name, phoneNumber, bookingDate, numberOfGuests, specialRequest).enqueue(new Callback<BookingResponse>() {
                        @Override
                        public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                BookingResponse bookingResponse = response.body();
                                if (bookingResponse.isSuccess()) {
                                    Toast.makeText(getContext(), "Booking Successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Booking Fail", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<BookingResponse> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
}