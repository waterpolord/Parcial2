package com.proyectoclase.segundoparcialrg.Fragments;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.proyectoclase.segundoparcialrg.Models.Product;
import com.proyectoclase.segundoparcialrg.Models.SharedPreferenceStorage;
import com.proyectoclase.segundoparcialrg.R;

import java.util.ArrayList;

public class CustomAdapterProducts extends RecyclerView.Adapter<CustomAdapterProducts.MyViewHolder> {
    private static ArrayList<Product> products;
    private static View.OnClickListener clickListener;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;
        TextView category;
        View divider;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.name = itemView.findViewById(R.id.txtProductNameList);
            this.price = itemView.findViewById(R.id.txtPriceList);
                this.category = itemView.findViewById(R.id.txtCategoryList);
            //this.divider = itemView.findViewById(R.id.divider);

        }
    }
    public CustomAdapterProducts(ArrayList<Product> dataSet) {
        this.products = dataSet;


    }
    public  CustomAdapterProducts.MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product_layout, parent, false);
        view.setOnClickListener( clickListener);

        return new  CustomAdapterProducts.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder( CustomAdapterProducts.MyViewHolder holder, int position) {

        final TextView name = holder.name;
        final TextView price = holder.price;
        final TextView category = holder.category;
        /*clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               *//* AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new UpdateProductFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();
                SharedPreferenceStorage storage = new SharedPreferenceStorage(activity.getApplicationContext());
                storage.put("name",name.getText().toString());
                storage.put("price",price.getText().toString());
                storage.put("category",category.getText().toString());*//*
            }
        };*/
    //    View divider = holder.divider;
        name.setText(products.get(position).getName());
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new UpdateProductFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();
                SharedPreferenceStorage storage = new SharedPreferenceStorage(activity.getApplicationContext());
                storage.putBool("onUpdate",true);
                storage.put("name",name.getText().toString());
                storage.put("price",price.getText().toString());
                storage.put("category",category.getText().toString());
            }
        });
        price.setText(products.get(position).getPrice().toString());
        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new UpdateProductFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();
                SharedPreferenceStorage storage = new SharedPreferenceStorage(activity.getApplicationContext());
                storage.putBool("onUpdate",true);
                storage.put("name",name.getText().toString());
                storage.put("price",price.getText().toString());
                storage.put("category",category.getText().toString());
            }
        });
        category.setText(products.get(position).getCategory().getName());
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new UpdateCategoryFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();
                SharedPreferenceStorage storage = new SharedPreferenceStorage(activity.getApplicationContext());
                storage.putBool("onUpdate",true);
                storage.put("name",category.getText().toString());
            }
        });


    }
    @Override
    public int getItemCount() {
        return products.size();
    }
}
