package com.example.btlproject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btlproject.API.APIService;
import com.example.btlproject.activities.HistoryBooking;
import com.example.btlproject.activities.HistoryOrder;
import com.example.btlproject.activities.LoginActivity;
import com.example.btlproject.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView profile_name, txtinfor, txtbooking, txtorder, txtlogout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginResponse loginResponse = (LoginResponse) getArguments().getSerializable("info");
        String token = loginResponse.getResult().getToken();
        String userID = loginResponse.getResult().getUser().getId();
        profile_name = view.findViewById(R.id.profile_name);
        profile_name.setText(loginResponse.getResult().getUser().getName());
        txtinfor = view.findViewById(R.id.personal_info);
        txtbooking = view.findViewById(R.id.history_booking);
        txtorder = view.findViewById(R.id.history_order);
        txtlogout = view.findViewById(R.id.log_out);

        txtinfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_dialog_infor);
                Window window = dialog.getWindow();
                if (window == null){
                    return;
                }
                            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                            WindowManager.LayoutParams windownatributes = window.getAttributes();
                            windownatributes.gravity = Gravity.CENTER;
                            window.setAttributes(windownatributes);

                            TextView txt_infor_username, txt_infor_nam;
                            Button btn_dong;

                            txt_infor_username = dialog.findViewById(R.id.txt_infor_username);
                            txt_infor_nam = dialog.findViewById(R.id.txt_infor_name);
                            btn_dong = dialog.findViewById(R.id.btn_dong);

                            txt_infor_nam.setText(loginResponse.getResult().getUser().getName());
                            txt_infor_username.setText(loginResponse.getResult().getUser().getUserName());
                            btn_dong.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.setCancelable(false);
                            dialog.show();
            }
        });
        txtbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getContext(), HistoryBooking.class);
                intent2.putExtra("tokenhistory",token);
                startActivity(intent2);
            }
        });
        txtorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getContext(), HistoryOrder.class);
                intent1.putExtra("tokenhistory",token);
                startActivity(intent1);
            }
        });
        txtlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), LoginActivity.class));
                if (getActivity() != null){
                    getActivity().finish();
                }
            }
        });
    }

}