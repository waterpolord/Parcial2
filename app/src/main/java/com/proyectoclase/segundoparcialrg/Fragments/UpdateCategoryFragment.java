package com.proyectoclase.segundoparcialrg.Fragments;

import android.content.DialogInterface;
import android.database.Cursor;
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
import com.proyectoclase.segundoparcialrg.Models.Category;
import com.proyectoclase.segundoparcialrg.Models.Product;
import com.proyectoclase.segundoparcialrg.Models.SharedPreferenceStorage;
import com.proyectoclase.segundoparcialrg.R;
import com.proyectoclase.segundoparcialrg.conexion.DBManagerCategory;
import com.proyectoclase.segundoparcialrg.conexion.DBManagerProduct;

import java.util.ArrayList;
import java.util.List;

public class UpdateCategoryFragment extends Fragment {

    DBManagerProduct managerProduct;
    DBManagerCategory managerCategory;
    TextView txtname;
    Button btnUpdate,btnDelete;
    List<String> names = new ArrayList<>();
    List<Integer> ids = new ArrayList<>();
    SharedPreferenceStorage storage;
    ArrayList<Product> data = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.update_category_layout, container, false);
        ((MainActivity) getActivity())
                .setActionBarTitle("Update Category");
        ((MainActivity) getActivity())
                .setIconVisible(false);
        AppCompatActivity activity = (AppCompatActivity) root.getContext();
        storage = new SharedPreferenceStorage(activity.getApplicationContext());
        managerProduct = new DBManagerProduct(getActivity());
        managerProduct.open();
        managerCategory = new  DBManagerCategory(getActivity());
        managerCategory.open();
        txtname = root.findViewById(R.id.txtCatUpdate);

        btnUpdate = root.findViewById(R.id.btnUpdateCat);
        btnDelete = root.findViewById(R.id.btnDeleteCat);

        txtname.setText(storage.getString("name"));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtname.getText().toString().isEmpty()){
                    txtname.setError("Name cannot be empty");
                }
                else{
                    managerCategory.Update(storage.getString("name"),txtname.getText().toString());
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Categoría Actualizada")
                            .setMessage("Se Actualizó "+txtname.getText().toString())
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

                    builder.setMessage("¿Seguro que desea eliminar " + storage.getString("name") + " ?")
                            .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    int cant = productsHascategory(storage.getString("name"));
                                    if(cant > 0) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                                        builder.setMessage("Se eliminaran "+cant+" productos porque tienen esa categoría")
                                                .setPositiveButton("No me importa, Eliminar", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        // FIRE ZE MISSILES!
                                                        deleteProductsByCategory(storage.getString("name"));
                                                        managerCategory.delete(storage.getString("name"));
                                                        new AlertDialog.Builder(getActivity())
                                                                .setTitle("Categoría Eliminada")
                                                                .setMessage("Se Eliminó " + storage.getString("name"))
                                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog, int id) {

                                                                        dialog.cancel();
                                                                    }
                                                                }).show();
                                                        txtname.setText("");
                                                    }
                                                })
                                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        // User cancelled the dialog
                                                        dialog.cancel();
                                                    }
                                                }).show();
                                    }
                                    else{
                                        managerCategory.delete(storage.getString("name"));
                                        new AlertDialog.Builder(getActivity())
                                                .setTitle("Categoría Eliminada")
                                                .setMessage("Se Eliminó " + storage.getString("name"))
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {

                                                        dialog.cancel();
                                                    }
                                                }).show();

                                        txtname.setText("");
                                    }

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

    public int productsHascategory(String Cat){
        Cursor cursor = managerProduct.findAllProductsAndCategories();
        int cant = 0;
        data = new ArrayList<>();
        while (cursor.moveToNext()){
            data.add(new Product(
                    cursor.getString(0),
                    cursor.getDouble(1),
                    new Category(cursor.getString(2))
            ));
        }
        for(Product aux:data){
            if(aux.getCategory().getName().equalsIgnoreCase(Cat)){
                cant++;
            }
        }
        return cant;

    }

    public void deleteProductsByCategory(String Cat){


        for(Product aux:data){
            if(aux.getCategory().getName().equalsIgnoreCase(Cat)){
                managerProduct.delete(aux.getName());
            }
        }

    }


}
