package com.proyectoclase.segundoparcialrg;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.ClipData;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.proyectoclase.segundoparcialrg.Fragments.CreateProductFragment;
import com.proyectoclase.segundoparcialrg.Fragments.ListProductFragment;
import com.proyectoclase.segundoparcialrg.Fragments.UpdateProductFragment;
import com.proyectoclase.segundoparcialrg.Models.SharedPreferenceStorage;
import com.proyectoclase.segundoparcialrg.conexion.DBManagerCategory;
import com.proyectoclase.segundoparcialrg.conexion.DataBaseHelper;

public class MainActivity extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private DBManagerCategory dbManager;
    public DataBaseHelper dataBaseHelper;
    private Cursor cursor;
    private Boolean toRegister = false;
    private AppBarConfiguration mAppBarConfiguration;
    private SQLiteDatabase database;
    ClipData.Item item;
    Toolbar bar;
    MenuItem menuItem;
    //private SharedPreferencesStore preferencesStore;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBaseHelper = new DataBaseHelper(this);
        database = dataBaseHelper.getWritableDatabase();
        setContentView(R.layout.activity_main);
        dbManager = new DBManagerCategory(this);
        dbManager.open();
        bar = findViewById(R.id.toolbar);
        bar.setTitle("Products");
        setSupportActionBar(bar);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_create_product,R.id.nav_update_product, R.id.nav_product,R.id.recyclerId)
                .build();

    }

    @Override
    public void onBackPressed() {
        SharedPreferenceStorage storage = new SharedPreferenceStorage(this);

        if(storage.getBool("onUpdate") || storage.getBool("onRegister")){
            storage.putBool("onUpdate",false);
            //storage.putBool("onRegister",false);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
           // transaction.addToBackStack(null);
            transaction.replace(R.id.nav_host_fragment, new ListProductFragment());
            transaction.commit();
            menuItem.setVisible(true);
        }
        /*if(storage.getBool("onRegister")){
            storage.putBool("onRegister",false);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            // transaction.addToBackStack(null);
            transaction.replace(R.id.nav_host_fragment, new ListProductFragment());
            transaction.commit();
            bar.setTitle("Products");
        }*/
        /*if(toRegister){
            bar.setTitle("Products");
            toRegister = false;

        }*/

        super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_header, menu);
        MenuItem product = menu.findItem(R.id.header_add_product);
        menuItem = product;
         // product.setVisible(!toRegister);  //userRegistered is boolean, pointing if the user has registered or not.
          //toRegister = (toRegister==true)?false:true;

          //userRegistered is boolean, pointing if the user has registered or not.
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    /*public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem add = menu.findItem(R.id.header_add_product);
        toRegister = (toRegister==true)?false:true;
        add.setVisible(!toRegister);
        return true;

    }*/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        menuItem = item;
        bar.setTitle("Add Product");
        SharedPreferenceStorage storage = new SharedPreferenceStorage(this);
        storage.putBool("onRegister",true);
        toRegister = (toRegister==true)?false:true;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.nav_host_fragment, new CreateProductFragment());
        transaction.commit();
        return true;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void setIconVisible(Boolean value) {
        menuItem.setVisible(value);
    }
}