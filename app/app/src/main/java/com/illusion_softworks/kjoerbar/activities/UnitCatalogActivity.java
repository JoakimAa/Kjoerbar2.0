package com.illusion_softworks.kjoerbar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.datahandler.AlcoholUnitCatalogDataHandler;
import com.illusion_softworks.kjoerbar.datahandler.UserDataHandler;
import com.illusion_softworks.kjoerbar.model.AlcoholUnit;

public class UnitCatalogActivity extends AppCompatActivity {
    private Button btnAddAlcoholUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_catalog);

        btnAddAlcoholUnit = findViewById(R.id.btnAddAlcoholUnit);
        btnAddAlcoholUnit.setOnClickListener(this::addAlcoholUnitToCatalog);
    }

    private void addAlcoholUnitToCatalog(View view) {
        AlcoholUnit greyGoose = new AlcoholUnit("Grey Goose","Grey Goose", "Vodka", "cl", 4,40.0, 12.6);
        UserDataHandler.addAlcoholUnitToCatalog(greyGoose);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.nav_alcohol_units_catalog) {
            Intent intent2 = new Intent(this, UnitCatalogActivity.class);
            startActivity(intent2);
            return true;
        }
        else if (id == R.id.nav_settings) {
            Intent intent3 = new Intent(this, SettingsActivity.class);
            startActivity(intent3);
            return true;
        }
        else if (id == R.id.nav_sessions) {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //return super.onOptionsItemSelected(item);
    // }
}
