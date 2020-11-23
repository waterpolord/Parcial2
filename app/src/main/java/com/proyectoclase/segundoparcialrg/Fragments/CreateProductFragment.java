package com.proyectoclase.segundoparcialrg.Fragments;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.proyectoclase.segundoparcialrg.MainActivity;
import com.proyectoclase.segundoparcialrg.Models.SharedPreferenceStorage;
import com.proyectoclase.segundoparcialrg.R;
import com.proyectoclase.segundoparcialrg.conexion.DBManagerCategory;
import com.proyectoclase.segundoparcialrg.conexion.DBManagerProduct;

import java.util.ArrayList;
import java.util.List;

public class CreateProductFragment extends Fragment {
    Button addCategory,save,updateCat;
    DBManagerProduct managerProduct;
    DBManagerCategory managerCategory;
    Spinner cbxCat;
    TextView txtName,txtPrice;
    List<String> names = new ArrayList<>();
    List<Integer> ids = new ArrayList<>();
    SharedPreferenceStorage storage;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.create_product_layout, container, false);
        ((MainActivity) getActivity())
                .setActionBarTitle("Add Product");

        ((MainActivity) getActivity())
                .setIconVisible(false);
        AppCompatActivity activity = (AppCompatActivity) root.getContext();
        storage = new SharedPreferenceStorage(activity.getApplicationContext());
        managerProduct = new DBManagerProduct(getActivity());
        managerProduct.open();
        managerCategory = new  DBManagerCategory(getActivity());
        managerCategory.open();
        addCategory = root.findViewById(R.id.btnAddCategory);
        updateCat = root.findViewById(R.id.btnupdatecatfromproduct);
        save = root.findViewById(R.id.btnSave);
        cbxCat = root.findViewById(R.id.spnCat);


        findAllCategories();

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, names);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbxCat.setAdapter(spinnerArrayAdapter);
        //cbxCat.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) getActivity());
        txtName = root.findViewById(R.id.txtName);
        txtPrice = root.findViewById(R.id.txtPrice);

        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment newFragment;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                newFragment = new CreateCategoryFragment();
                transaction.replace(R.id.nav_host_fragment, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtName.getText().toString().isEmpty() || txtPrice.getText().toString().isEmpty()){
                    if (txtName.getText().toString().isEmpty()) {
                        txtName.setError("Name cannot be empty");
                    } else {
                        txtPrice.setError("Price cannot be empty");
                    }
                }
                else{
                    managerProduct.insert(txtName.getText().toString(),Double.parseDouble(txtPrice.getText().toString()),ids.get(cbxCat.getSelectedItemPosition()));

                    new AlertDialog.Builder(getActivity())
                            .setTitle("Producto Guardado")
                            .setMessage("Se Guardó "+txtName.getText().toString()+" a un precio de "+txtPrice.getText().toString())
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.cancel();
                                }
                            }).show();
                    txtName.setText("");
                    txtPrice.setText("");
                }
              }
        });

        updateCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storage.put("name",cbxCat.getSelectedItem().toString());
                Fragment newFragment;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                newFragment = new UpdateCategoryFragment();
                transaction.replace(R.id.nav_host_fragment, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return root;
    }

    public void findAllCategories(){
        Cursor cursor = managerCategory.findAll();
        ids = new ArrayList<>();
        names = new ArrayList<>();
        while (cursor.moveToNext()){
            ids.add(cursor.getInt(0));
            names.add(cursor.getString(1));
        }
    }





}
