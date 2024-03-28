package com.example.sugarsense.ui.explore_more;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.sugarsense.Assistance_Page;
import com.example.sugarsense.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;

import java.util.Collections;

public class Explore_More_Fragment extends Fragment {

    MaterialCardView Doctor_Assistance_card, Nearest_Diabetes_Center_card, Nearest_Testing_Center_card;
    ImageView Doctor_Assistance_img, Nearest_Diabetes_Center_img;
    ImageView Nearest_Testing_Center_img;
    Activity content;
    ProgressBar Progress_Bar;
    private static final int REQUEST_CHECK_SETTING = 1001;
    private LocationRequest locationRequest;
    public static Explore_More_Fragment newInstance() {
        return new Explore_More_Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        content = getActivity();

        return inflater.inflate(R.layout.fragment_explore__more_, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    public void onStart()
    {
        super.onStart();

        Doctor_Assistance_card = content.findViewById(R.id.Doctor_Assistance_card);
        Nearest_Diabetes_Center_card = content.findViewById(R.id.Nearest_Diabetes_Center_card);
        Nearest_Testing_Center_card = content.findViewById(R.id.Nearest_Testing_Center_card);

        Doctor_Assistance_img = content.findViewById(R.id.Doctor_Assistance_img);
        Nearest_Diabetes_Center_img = content.findViewById(R.id.Nearest_Diabetes_Center_img);
        Nearest_Testing_Center_img = content.findViewById(R.id.Nearest_Testing_Center_img);

        Progress_Bar = content.findViewById(R.id.Progress_Bar);

        locationRequest = com.google.android.gms.location.LocationRequest.create(); // create a location request
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); // set how accurately the location will be provided
        locationRequest.setInterval(5000); // interval at with the location is set
        locationRequest.setFastestInterval(2000);


        Animation animation = AnimationUtils.loadAnimation(content.getApplicationContext(), R.anim.fade_animation);
        Doctor_Assistance_img.startAnimation(animation);
        Nearest_Diabetes_Center_img.startAnimation(animation);
        Nearest_Testing_Center_img.startAnimation(animation);


        Doctor_Assistance_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(content, Assistance_Page.class));
                Animatoo.INSTANCE.animateZoom(content);
            }
        });

        Nearest_Diabetes_Center_card.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String destination = "Diabetes Center Near Me";

                Progress_Bar.setVisibility(View.VISIBLE);
                getDestinationLocation(destination);
            }
        });

        Nearest_Testing_Center_card.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String destination = "Diabetes Testing Center Near Me";

                Progress_Bar.setVisibility(View.VISIBLE);
                getDestinationLocation(destination);
            }
        });
    }

    public void getDestinationLocation(String destination)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(ActivityCompat.checkSelfPermission(content, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) // checks if the gps is on or not
            {
                if(isGPSEnable())
                {
                    LocationServices.getFusedLocationProviderClient(content).requestLocationUpdates(locationRequest, new LocationCallback() {
                        @Override
                        public void onLocationResult(@NonNull LocationResult locationResult) {
                            super.onLocationResult(locationResult);
                            LocationServices.getFusedLocationProviderClient(content).removeLocationUpdates(this);

                            if(locationResult != null && locationResult.getLocations().size() > 0)
                            {
                                int index =  locationResult.getLocations().size() - 1;
                                double latitude = locationResult.getLocations().get(index).getLatitude();
                                double longitude = locationResult.getLocations().get(index).getLongitude();

                                double Latitude = (double)latitude;
                                double Longitude = (double) longitude;

                                String source = Double.toString(Latitude) + "," + Double.toString(Longitude);


                                    get_Destination_Location_on_Map(source, destination);
                            }
                        }
                    }, Looper.getMainLooper());
                }

                else // when gps is off
                {
                    Progress_Bar.setVisibility(View.INVISIBLE);
                    turnOnGPS();
                }
            }

            else
            {
                Progress_Bar.setVisibility(View.INVISIBLE);
                requestPermissions(new String[]{ACCESS_FINE_LOCATION}, 44);
            }
        }
    }


    private void turnOnGPS()
    {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addAllLocationRequests(Collections.singleton(locationRequest)); //used to get the response as gps location of client
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(content.getApplicationContext()).checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(content, "GPS is On!!", Toast.LENGTH_LONG).show();

                }
                catch (ApiException e) // e here is the api exception error
                {
                    switch (e.getStatusCode())
                    {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED : //location setting status code used to get the location setting status(wether its turn on, or the device have location access or not etc)
                            //Resolution Required is used to check the location being turned on

                            try
                            {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e; // used to get the api exception error
                                resolvableApiException.startResolutionForResult(content, REQUEST_CHECK_SETTING);
                            }
                            catch (IntentSender.SendIntentException ex)
                            {
                                ex.printStackTrace();
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE :
                            break;
                    }
                }
            }
        });
    }

    private boolean isGPSEnable() // this method returns true is gps is turned on and returns false if gps is turned off
    {
        LocationManager locationManager = null;
        boolean isEnable = false;

        if(locationManager == null)
        {
            locationManager = (LocationManager)content.getSystemService(Context.LOCATION_SERVICE);
        }

        isEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnable;
    }

    private void get_Destination_Location_on_Map(String source, String destination)
    {
        Progress_Bar.setVisibility(View.INVISIBLE);
        Uri uri = Uri.parse("https://www.google.com/maps/dir/" + source + "/" + destination);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}