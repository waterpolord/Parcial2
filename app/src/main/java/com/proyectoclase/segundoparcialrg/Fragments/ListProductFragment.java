package com.proyectoclase.segundoparcialrg.Fragments;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.proyectoclase.segundoparcialrg.MainActivity;
import com.proyectoclase.segundoparcialrg.Models.Category;
import com.proyectoclase.segundoparcialrg.Models.Product;
import com.proyectoclase.segundoparcialrg.R;
import com.proyectoclase.segundoparcialrg.conexion.DBManagerProduct;

import java.util.ArrayList;

public class ListProductFragment extends Fragment {
    MenuItem menuItem;
    DBManagerProduct managerProduct;
    ArrayList<Product> data = new ArrayList<>();
    private RecyclerView recyclerView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity())
                .setActionBarTitle("Products");
        View root = inflater.inflate(R.layout.recyclerview, container, false);
        recyclerView = root.findViewById(R.id.recyclerId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        managerProduct = new DBManagerProduct(getActivity());
        managerProduct.open();
        setProducts();
        CustomAdapterProducts adapter = new CustomAdapterProducts(data);
        recyclerView.setAdapter(adapter);

        return root;
    }



    public void setProducts(){
        Cursor cursor = managerProduct.findAllProductsAndCategories();
        data = new ArrayList<>();
        while (cursor.moveToNext()){
            data.add(new Product(
                    cursor.getString(0),
                    cursor.getDouble(1),
                    new Category(cursor.getString(2))
            ));
        }

    }
}
