package com.example.sensorapplication;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;

import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SensorManager sm = null;
    TextView textView1 = null;
    List list;
    SensorEventListener sel;
    SensorManager smm;
    List<Sensor> sensor;
//    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = findViewById(R.id.textView1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
//        txtList = (TextView)findViewById(R.id.sensorslist);
        List<Sensor> sensorList = sm.getSensorList(Sensor.TYPE_ALL);
        StringBuilder strBuilder = new StringBuilder();
        for(Sensor s: sensorList){
            strBuilder.append(s.getName()+"\n");
        }
        textView1.setVisibility(View.VISIBLE);
        textView1.setText(strBuilder);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
//        sel = new SensorEventListener() {
//            public void onAccuracyChanged(Sensor sensor, int accuracy) {
//            }
//
//            public void onSensorChanged(SensorEvent event) {
//                float[] values = event.values;
//                textView1.setText("x: " + values[0] + "\ny: " + values[1] + "\nz: " + values[2]);
//            }
//        };
//        list = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
//        if (list.size() > 0) {
//            sm.registerListener(sel, (Sensor) list.get(0), SensorManager.SENSOR_DELAY_NORMAL);
//        } else {
//            Toast.makeText(getBaseContext(), "Error: No Accelerometer.", Toast.LENGTH_LONG).show();
//        }
    }

//    @Override
//    protected void onStop() {
//        if(list.size()>0){
//            sm.unregisterListener(sel);
//        }
//        super.onStop();
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void displaySelectedScreen(int itemId) {

//        Fragment fragment = null;

        switch (itemId) {
            case R.id.nav_menu1:
                Toast.makeText(this, "Menu 1", Toast.LENGTH_SHORT).show();
            AlertBoxMethod();

                break;
            case R.id.nav_menu2:
                Intent intent=new Intent(MainActivity.this,TypesofSensors.class);
                startActivity(intent);
//                Toast.makeText(this, "Menu 2", Toast.LENGTH_SHORT).show();
//                fragment = new Menu2();
                break;
//            case R.id.nav_menu3:
////                fragment = new Menu3();
//                Toast.makeText(this, "Menu 3", Toast.LENGTH_SHORT).show();
//                break;
        }

        //replacing the fragment
//        if (fragment != null) {
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content_frame, fragment);
//            ft.commit();
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void AlertBoxMethod() {

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE );
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (statusOfGPS){
            Intent intent=new Intent(MainActivity.this,MapsActivity.class);
            startActivity(intent);
        }else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
            final String message = "Do you want open GPS setting?";

            builder.setMessage(message)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int id) {
                                    MainActivity.this.startActivity(new Intent(action));
                                    d.dismiss();
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int id) {
                                    d.cancel();
                                }
                            });
            builder.create().show();
        }
    }


    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());

        return true;
    }


}