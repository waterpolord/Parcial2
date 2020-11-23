package com.proyectoclase.segundoparcialrg.Fragments;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.RadialGradient;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class UpdateProductFragment extends Fragment {

    DBManagerProduct managerProduct;
    DBManagerCategory managerCategory;
    TextView txtname,price;
    Spinner cbxCat;
    Button btnUpdate,btnDelete,btnadd;
    List<String> names = new ArrayList<>();
    List<Integer> ids = new ArrayList<>();
    SharedPreferenceStorage storage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.update_product_layout, container, false);
        ((MainActivity) getActivity())
                .setActionBarTitle("Update Product");
        ((MainActivity) getActivity())
                .setIconVisible(false);
        AppCompatActivity activity = (AppCompatActivity) root.getContext();
        storage = new SharedPreferenceStorage(activity.getApplicationContext());
        managerProduct = new DBManagerProduct(getActivity());
        managerProduct.open();
        managerCategory = new  DBManagerCategory(getActivity());
        managerCategory.open();
        txtname = root.findViewById(R.id.txtNameUpdate);
        price = root.findViewById(R.id.txtPriceUpdate);

        cbxCat = root.findViewById(R.id.spnCatUpdate);
        findAllCategories();
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, names);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbxCat.setAdapter(spinnerArrayAdapter);

        btnUpdate = root.findViewById(R.id.btnupdate);
        btnDelete = root.findViewById(R.id.btndelete);
        btnadd = root.findViewById(R.id.btnAddCategoryUpdate);

        txtname.setText(storage.getString("name"));
        price.setText(storage.getString("price"));
        cbxCat.setSelection(getCategoryPosition(storage.getString("category")));

        btnadd.setOnClickListener(new View.OnClickListener() {
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

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtname.getText().toString().isEmpty()){
                    txtname.setError("Name cannot be empty");
                }
                else if(price.getText().toString().isEmpty()){
                    price.setError("Price cannot be empty");
                }
                else{
                    managerProduct.update(txtname.getText().toString(),Double.parseDouble(price.getText().toString()),ids.get(cbxCat.getSelectedItemPosition()),storage.getString("name"));
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Producto Actualizado")
                            .setMessage("Se Actualizó "+txtname.getText().toString()+" a un precio de "+price.getText().toString())
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.cancel();
                                }
                            }).show();
                }

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("¿Seguro que desea eliminar "+storage.getString("name")+" ?")
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // FIRE ZE MISSILES!
                                managerProduct.delete(storage.getString("name"));
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Producto Eliminado")
                                        .setMessage("Se Eliminó "+storage.getString("name"))
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                dialog.cancel();
                                            }
                                        }).show();
                                txtname.setText("");
                                price.setText("");
                                cbxCat.setSelection(0);
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                dialog.cancel();
                            }
                        }).show();



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

    public int getCategoryPosition(String category){
        int i = 0;
        for(String ind:names){
            if(ind.equalsIgnoreCase(category)){
                return i;
            }
            i++;
        }
        return 0;
    }


}
