package com.example.btlproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.btlproject.API.APIService;
import com.example.btlproject.adapter.CategoryAdapter;
import com.example.btlproject.adapter.MenuItemAdapter;
import com.example.btlproject.models.LoginResponse;
import com.example.btlproject.models.MenuItems;
import com.example.btlproject.models.ResponsedData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {
    private RecyclerView menurecycle;
    private MenuItemAdapter adapter;
    private EditText edt_search;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
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
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        menurecycle = view.findViewById(R.id.menurecycl);
        edt_search = view.findViewById(R.id.edtSearch);


        String token = getArguments().getString("token");

        adapter = new MenuItemAdapter(getContext(), token);


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        menurecycle.setLayoutManager(linearLayoutManager1);
        callApiGetMenuItem();

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Toast.makeText(getContext(), charSequence.toString(), Toast.LENGTH_SHORT).show();
                adapter.filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        menurecycle.setHasFixedSize(true);
        menurecycle.setNestedScrollingEnabled(true);


    }
    private void callApiGetMenuItem(){
        APIService.apiService.MenuItem().enqueue(new Callback<ResponsedData<List<MenuItems>>>() {
            @Override
            public void onResponse(Call<ResponsedData<List<MenuItems>>> call, Response<ResponsedData<List<MenuItems>>> response) {
                List<MenuItems> mlistMenuItems = new ArrayList<>();
                mlistMenuItems = response.body().getResult();
                adapter.setData(mlistMenuItems);
                menurecycle.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ResponsedData<List<MenuItems>>> call, Throwable t) {

            }
        });
    }

}