package com.example.btlproject.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.btlproject.API.APIService;
import com.example.btlproject.MenuFragment;
import com.example.btlproject.R;
import com.example.btlproject.adapter.CategoryAdapter;
import com.example.btlproject.adapter.MenuItemAdapter;
import com.example.btlproject.models.Category;
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
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements CategoryAdapter.OnClickCategoryAdapter{
    RecyclerView recycle, recyclemenuitem;
    CategoryAdapter categoryAdapter;
    MenuItemAdapter menuItemAdapter;
    EditText edtSearch;
    TextView txtdau;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LoginResponse loginResponse = (LoginResponse) getArguments().getSerializable("info");
        String token = loginResponse.getResult().getToken();
        //Toast.makeText(getContext(), loginResponse.getResult().getToken(), Toast.LENGTH_SHORT).show();
        recycle = view.findViewById(R.id.home_hor_rec);
        recyclemenuitem = view.findViewById(R.id.recyclepopular);
        edtSearch = view.findViewById(R.id.editText);
        txtdau = view.findViewById(R.id.txtdau);
        txtdau.setText("Xin chào "+loginResponse.getResult().getUser().getName());
        edtSearch.clearFocus();
        edtSearch.setFocusable(false);

        edtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuFragment menuFragment = new MenuFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("token",token);
                menuFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,menuFragment);
                fragmentTransaction.commit();
            }
        });



        categoryAdapter = new CategoryAdapter(getContext(),this);
        menuItemAdapter = new MenuItemAdapter(getContext(), token);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        recycle.setLayoutManager(linearLayoutManager);
        recyclemenuitem.setLayoutManager(linearLayoutManager1);


        callApiGetCategory();
        callApiGetMenuItem();

        recycle.setHasFixedSize(true);
        recycle.setNestedScrollingEnabled(true);
        recyclemenuitem.setHasFixedSize(true);
        recyclemenuitem.setNestedScrollingEnabled(true);

        banner(view);
    }
    private void banner(View view){
        ArrayList<SlideModel> imagelist = new ArrayList<>();
        imagelist.add(new SlideModel(R.drawable.img_7, ScaleTypes.FIT));
        imagelist.add(new SlideModel(R.drawable.img_8, ScaleTypes.FIT));
        imagelist.add(new SlideModel(R.drawable.hq720, ScaleTypes.FIT));

        ImageSlider imageSlider;
        imageSlider = view.findViewById(R.id.image_slider);
        imageSlider.setImageList(imagelist);
        imageSlider.setImageList(imagelist, ScaleTypes.FIT);
    }
    private void callApiGetCategory(){
//        APIService.apiService.category().enqueue(new Callback<ResponsedData<List<Category>>>() {
//            @Override
//            public void onResponse(Call<ResponsedData<List<Category>>> call, Response<ResponsedData<List<Category>>> response) {
//                //Toast.makeText(getContext(), "Succesful", Toast.LENGTH_SHORT).show();
//                List<Category> mlistcategory = new ArrayList<>();
//                mlistcategory  = response.body().getResult();
//                categoryAdapter.setData(mlistcategory);
//                recycle.setAdapter(categoryAdapter);
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponsedData<List<Category>>> call, Throwable t) {
//                Log.d("loi",t.getMessage());
//                //Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
        List<Category> mlistcategory = new ArrayList<>();
        mlistcategory.add(new Category(1, R.drawable.khaivi,"Khai vị"));
        mlistcategory.add(new Category(2, R.drawable.monchinh,"Món chính"));
        mlistcategory.add(new Category(3, R.drawable.trangmieng,"Tráng miệng"));
        mlistcategory.add(new Category(4, R.drawable.giaikhat,"Giải khát"));
        mlistcategory.add(new Category(5, R.drawable.snack,"Snack"));
        categoryAdapter.setData(mlistcategory);
        recycle.setAdapter(categoryAdapter);
    }
    private void callApiGetMenuItem(){
        APIService.apiService.MenuItem().enqueue(new Callback<ResponsedData<List<MenuItems>>>() {
            @Override
            public void onResponse(Call<ResponsedData<List<MenuItems>>> call, Response<ResponsedData<List<MenuItems>>> response) {
                List<MenuItems> mlistMenuItems = new ArrayList<>();
                mlistMenuItems = response.body().getResult();
                menuItemAdapter.setData(mlistMenuItems);
                recyclemenuitem.setAdapter(menuItemAdapter);

            }

            @Override
            public void onFailure(Call<ResponsedData<List<MenuItems>>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(menuItemAdapter != null){
            menuItemAdapter.release();
        }
    }
    @Override
    public void onClickCategory(int id) {
        callApiGetMenuItemByCategory(id);
    }
    private void callApiGetMenuItemByCategory(int id){
        APIService.apiService.get_product_by_category(id).enqueue(new Callback<ResponsedData<List<MenuItems>>>() {
            @Override
            public void onResponse(Call<ResponsedData<List<MenuItems>>> call, Response<ResponsedData<List<MenuItems>>> response) {
                List<MenuItems> mlistMenuItems = new ArrayList<>();
                mlistMenuItems = response.body().getResult();
                menuItemAdapter.setData(mlistMenuItems);
                recyclemenuitem.setAdapter(menuItemAdapter);
                //Toast.makeText(getContext(), "Thanh cong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponsedData<List<MenuItems>>> call, Throwable t) {
                Toast.makeText(getContext(), "Loi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}