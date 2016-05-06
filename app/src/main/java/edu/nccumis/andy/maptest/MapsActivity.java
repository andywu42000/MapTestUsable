package edu.nccumis.andy.maptest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SQLiteDatabase db;
    private DBHelper DbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //Intent i = new Intent();
        //i.setClass(MapsActivity.this, DBConnector.class);
        //startActivity(i);
        DbHelper = new DBHelper(this);
        new Thread(){
            public void run(){
                db = DbHelper.getReadableDatabase();
                ContentValues values = new ContentValues();
                values.put("mark_name", "Taipei Main Station");
                values.put("mark_info", "TRA/THSR/MRT");
                values.put("longit", "25.04775");
                values.put("latit", "121.51706");
                db.insertOrThrow("marks", null, values);

                ContentValues values2 = new ContentValues();
                values2.put("mark_name", "MRT Zhongxiao Fuxing");
                values2.put("mark_info", "Station on MRT Line 5");
                values2.put("longit", "25.04125");
                values2.put("latit", "121.543713");
                db.insertOrThrow("marks", null, values2);
            }
        }.start();
        //String initMap3 = "insert into marks values(null, 'MRT Zhongxiao Fuxing', 'MRT Line 5',
        // " +
                //"'25.04125', '121.543713')";
        DBConnector.sync();
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        LatLng nccu = new LatLng(24.9877, 121.5756);
        LatLng myHome = new LatLng(25.0885, 121.6994);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(nccu, 13));

        Cursor cursor = db.rawQuery(
                "select * from marks", null);
        while (cursor.moveToNext()) {
            int markId = cursor.getInt(0);
            String markName = cursor.getString(1);
            String markInfo = cursor.getString(2);
            float longit = cursor.getFloat(3);
            float latit = cursor.getFloat(4);
            LatLng markFromDb = new LatLng(longit, latit);

            map.addMarker(new MarkerOptions()
                    .title(markName)
                    .snippet(markInfo)
                    .position(markFromDb));
        }
        cursor.close();

        map.addMarker(new MarkerOptions()
                .title("NCCU")
                .snippet("National Chengchi University")
                .position(nccu));

        map.addMarker(new MarkerOptions()
                .title("My Home")
                .snippet("Where my home is.")
                .position(myHome));
    }
}
