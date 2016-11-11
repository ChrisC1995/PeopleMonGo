package com.campbellapps.christiancampbell.peoplemonv1.Views;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.campbellapps.christiancampbell.peoplemonv1.MainActivity;
import com.campbellapps.christiancampbell.peoplemonv1.Models.Auth;
import com.campbellapps.christiancampbell.peoplemonv1.Network.RestClient;
import com.campbellapps.christiancampbell.peoplemonv1.PeoplemonApplication;
import com.campbellapps.christiancampbell.peoplemonv1.R;
import com.campbellapps.christiancampbell.peoplemonv1.Stages.PeopleListStage;
import com.campbellapps.christiancampbell.peoplemonv1.Stages.nearbyStage;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by christiancampbell on 11/7/16.
 */

public class MapPageView extends RelativeLayout implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener {

    public Context context;

    public String name;

    private ArrayList<Auth> people;

    @Bind(R.id.map_view)
    public MapView mapView;

    @Bind(R.id.button3)
    public FloatingActionButton listButton;

    public GoogleMap mMap;

    public ArrayList<String> caughtPeople;

    private String selectedImage;
    private String encoded;
    private Bitmap decodedByte;


    public MapPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }




    @Override
    protected void onFinishInflate() {
        ButterKnife.bind(this);
        super.onFinishInflate();

        mapView.getMapAsync(this);


        mapView.onCreate(((MainActivity)
                getContext()).savedInstanceState);

        mapView.onResume();

        ((MainActivity)context).showMenu(true);




    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {


    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setOnMyLocationChangeListener(myLocationChangeListener);

        try {
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException e) {
            Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show();
        }
//        mMap.clear();
        LatLng sydney = new LatLng(-41, 23);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));





//        RestClient restClient = new RestClient();
//        restClient.getApiService().getUsers(400).enqueue(new Callback<Auth[]>() {
//            @Override
//            public void onResponse(Call<Auth[]> call, Response<Auth[]> response) {
//                if(response.isSuccessful()){
//                Toast.makeText(context, "You win", Toast.LENGTH_SHORT).show();}
//                else{
//                    Toast.makeText(context, "Failure 1", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Auth[]> call, Throwable t) {
//                Toast.makeText(context, "Failure 2", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            location.setSpeed(1);
            location.getSpeed();
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());


            final Circle circle = mMap.addCircle(new CircleOptions().center(loc)
                    .strokeColor(Color.BLUE).radius(100));

            ValueAnimator valAnim = new ValueAnimator();
            valAnim.setRepeatCount(ValueAnimator.INFINITE);
            valAnim.setRepeatMode(ValueAnimator.RESTART);  /* PULSE */
            valAnim.setIntValues(0, 100);
            valAnim.setDuration(5000);
            valAnim.setEvaluator(new IntEvaluator());
            valAnim.setInterpolator(new AccelerateDecelerateInterpolator());
            valAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float animatedFraction = valueAnimator.getAnimatedFraction();
                    circle.setRadius(animatedFraction * 100);
                }
            });
            valAnim.start();

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 20.0f));
            Auth check = new Auth(location.getLatitude(), location.getLongitude());
            RestClient restClient = new RestClient();
            restClient.getApiService().checkIn(check).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){
                        userCheck();}
                    else{

                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }
    };

    public void userCheck() {
        RestClient restClient = new RestClient();
        restClient.getApiService().getUsers(100).enqueue(new Callback<Auth[]>() {
            @Override
            public void onResponse(Call<Auth[]> call, Response<Auth[]> response) {
                if (response.isSuccessful()) {
                    people = new ArrayList<Auth>(Arrays.asList(response.body()));
                    caughtPeople = new ArrayList<String>();
                    mMap.clear();
                    for(Auth person: people){
                        name = person.getId();
                        String userName = person.getUserName();
                        LatLng loc = new LatLng(person.getLatitude(), person.getLongitude());

                        String base64 = person.getImage();

                        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.rsz_1marker);


                        try {
                            byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                            Bitmap image = Bitmap.createScaledBitmap(woo, 90, 90, false);
                            Bitmap work = Bitmap.createScaledBitmap(decodedByte, 90, 90, false);
                            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(work);
                            mMap.addMarker(new MarkerOptions().position(loc).snippet(name).icon(bitmapDescriptor).title(person.getUserName()));
                        } catch(Exception e) {
                            mMap.addMarker(new MarkerOptions().position(loc).snippet(name).icon(icon).title(person.getUserName()));
                        }


//                        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.rsz_1marker);
//                        mMap.addMarker(new MarkerOptions().position(loc).snippet(name).icon(icon).title(userName));

                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(final Marker marker) {


                                Double latC = marker.getPosition().latitude;
                                Double lngC = marker.getPosition().longitude;
                                LatLng markCircle = new LatLng(latC, lngC);
                                final Circle circle = mMap.addCircle(new CircleOptions().center(markCircle)
                                        .strokeColor(Color.RED).radius(10));
                                ValueAnimator vAnimator = new ValueAnimator();
                                vAnimator.setRepeatCount(1);
                                vAnimator.setRepeatMode(ValueAnimator.REVERSE);  /* PULSE */
                                vAnimator.setIntValues(10, 0);
                                vAnimator.setDuration(500);
                                vAnimator.setEvaluator(new IntEvaluator());
                                vAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                                vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                        float animatedFraction = valueAnimator.getAnimatedFraction();
                                        circle.setRadius(animatedFraction * 10);
                                    }
                                });
                                vAnimator.start();


                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Do you want to catch "+ marker.getTitle()).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        Toast.makeText(context, "PeopleMon Ball Thrown at " + marker.getTitle(), Toast.LENGTH_SHORT).show();
                                        marker.remove();

                                        Auth caughtUser = new Auth(marker.getSnippet(), 100);
                                        RestClient restClient = new RestClient();
                                        restClient.getApiService().userCatch(caughtUser).enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                if(response.isSuccessful()){
                                                    Toast.makeText(context, "Peoplemon have been caught!", Toast.LENGTH_SHORT).show();
                                                }
                                                else{

                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {
                                                Toast.makeText(context, "Catch Error", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(context, "You didn't catch any peoplemon. :(", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                builder.create().show();


                                return false;
                            }
                        });
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<Auth[]> call, Throwable t) {

            }

        });
    }


    @OnClick(R.id.button3)
    public void goToList(){
        Flow flow = PeoplemonApplication.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new PeopleListStage())
                .build(); //pushes on the stack
        flow.setHistory(newHistory, Flow.Direction.FORWARD);
    }


    @OnClick(R.id.button2)
    public void goToNearby(){
        Flow flow = PeoplemonApplication.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new nearbyStage())
                .build(); //pushes on the stack
        flow.setHistory(newHistory, Flow.Direction.FORWARD);
    }



}
