package com.campbellapps.christiancampbell.peoplemonv1.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.campbellapps.christiancampbell.peoplemonv1.MainActivity;
import com.campbellapps.christiancampbell.peoplemonv1.Models.Auth;
import com.campbellapps.christiancampbell.peoplemonv1.Models.ImageLoadedEvent;
import com.campbellapps.christiancampbell.peoplemonv1.Network.RestClient;
import com.campbellapps.christiancampbell.peoplemonv1.PeoplemonApplication;
import com.campbellapps.christiancampbell.peoplemonv1.R;
import com.campbellapps.christiancampbell.peoplemonv1.Stages.MapViewStage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
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
 * Created by christiancampbell on 11/10/16.
 */

public class EditView extends LinearLayout {
    private Context context;

    @Bind(R.id.newUsername)
    EditText username;

    @Bind(R.id.camera_button)
    Button picture;

    @Bind(R.id.newImage)
    ImageView newImage;

    @Bind(R.id.update_button)
    Button updateButton;

    private ArrayList<Auth> people;
    private String selectedImage;
    private String encoded;
    private Bitmap decodedByte;



    public EditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        getThings();
    }


    @OnClick(R.id.camera_button)
    public void pictureButton(){
        ((MainActivity)context).getImage();

    }


    public void getThings(){
        RestClient restClient = new RestClient();
        restClient.getApiService().accountInfo().enqueue(new Callback<Auth>() {
            @Override
            public void onResponse(Call<Auth> call, Response<Auth> response) {
                if(response.isSuccessful()){
                    people = new ArrayList<Auth>(Arrays.asList(response.body()));
                    for(Auth item : people){
                        String name = item.getFullname();
                        username.setText(name);
                        byte[] decodedString = Base64.decode(item.getImage(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        newImage.setImageBitmap(decodedByte);

                    }
                }
            }

            @Override
            public void onFailure(Call<Auth> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.update_button)
    public void updateButton(){


        final String name = username.getText().toString();
        final Auth update = new Auth(name, encoded);
        RestClient restClient = new RestClient();
        restClient.getApiService().update(update).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                Toast.makeText(context, "Information changed", Toast.LENGTH_SHORT).show();
                update.setFullname(name);
                Flow flow = PeoplemonApplication.getMainFlow();
                History newHistory = History.single(new MapViewStage());
                flow.setHistory(newHistory, Flow.Direction. BACKWARD);
                }else{
                    Toast.makeText(context, "Update Failed", Toast.LENGTH_SHORT).show();
                 }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setSelectedImage(ImageLoadedEvent event) {
        selectedImage = event.selectedImage;
        Bitmap image = BitmapFactory.decodeFile(selectedImage);
        newImage.setImageBitmap(image);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        byte[] decodedString = Base64.decode(encoded, Base64.DEFAULT);
        decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }


}
