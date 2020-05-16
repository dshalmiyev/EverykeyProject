package com.cwruride.everykeyproject;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText longitudeBox;
    private EditText latitudeBox;
    private Button submit;
    private Button getLocation;
    private TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        longitudeBox = findViewById(R.id.LongBox);
        latitudeBox = findViewById(R.id.LatBox);
        display = findViewById(R.id.OverheadTimes);
        getLocation = findViewById(R.id.getLocation);
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();

                longitudeBox.setText(longitude + "");
                latitudeBox.setText(latitude + "");
            }
        });

        submit = findViewById(R.id.Submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double longitude = Double.parseDouble(longitudeBox.getText().toString());
                double latitude = Double.parseDouble(latitudeBox.getText().toString());

                ISSRequest request = ISSRequest.makeRequest(longitude, latitude);
                String response;
                try {
                    response = request.sendRequest();
                    display.setText(response);

                } catch (IOException e) {
                    e.printStackTrace();
                    display.setText("failure");
                }
            }
        });

    }

}
