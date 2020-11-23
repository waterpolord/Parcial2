package com.proyectoclase.segundoparcialrg.Fragments;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.proyectoclase.segundoparcialrg.MainActivity;
import com.proyectoclase.segundoparcialrg.R;
import com.proyectoclase.segundoparcialrg.conexion.DBManagerCategory;

import java.util.ArrayList;

public class CreateCategoryFragment extends Fragment {

    private TextView txtName;
    private Button btnSave;
    private DBManagerCategory managerCategory;
    private ArrayList<String> categories = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity())
                .setActionBarTitle("Add Category");

        ((MainActivity) getActivity())
                .setIconVisible(false);
        View root = inflater.inflate(R.layout.create_category_layout, container, false);
        txtName = root.findViewById(R.id.txtNameCategory);
        btnSave = root.findViewById(R.id.btnSaveCat);
        managerCategory = new DBManagerCategory(getActivity());
        managerCategory.open();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtName.getText().toString().isEmpty()){
                   txtName.setError("Name cannot be null");
                }
                else if(hasCategory(txtName.getText().toString())){
                    txtName.setError("Category name alredy exist");
                }
                else {
                    managerCategory.insert(txtName.getText().toString());
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Categoria Guardada")
                            .setMessage("Se Guardó "+txtName.getText().toString())
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
                    txtName.setText("");
                }

            }
        });
        return root;
    }

    public Boolean hasCategory(String category){
        Cursor cursor = managerCategory.findAll();
        while (cursor.moveToNext()){
            if(cursor.getString(1).equalsIgnoreCase(category.trim())){
                return true;
            }
        }
        return false;
    }
}
