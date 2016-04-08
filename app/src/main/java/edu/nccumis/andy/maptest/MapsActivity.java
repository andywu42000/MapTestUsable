package edu.nccumis.andy.maptest;

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
        DbHelper = new DBHelper(this);
        new Thread(){
            public void run(){
                db = DbHelper.getReadableDatabase();
            }
        }.start();
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
