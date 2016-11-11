package com.campbellapps.christiancampbell.peoplemonv1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.campbellapps.christiancampbell.peoplemonv1.Models.ImageLoadedEvent;
import com.campbellapps.christiancampbell.peoplemonv1.Network.UserStore;
import com.campbellapps.christiancampbell.peoplemonv1.Stages.EditStage;
import com.campbellapps.christiancampbell.peoplemonv1.Stages.LoginStage;
import com.davidstemmer.flow.plugin.screenplay.ScreenplayDispatcher;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import flow.Flow;
import flow.History;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private Flow flow;
    private ScreenplayDispatcher dispatcher;
    public Bundle savedInstanceState;
    private Menu menu;
    MediaPlayer mp;

    private static int RESULT_LOAD_IMAGE = 1;

    @Bind(R.id.container)
    RelativeLayout container; //alternative to find view by id


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mp = MediaPlayer.create(getApplicationContext(), R.raw.pokemon);
        mp.start();

        flow = PeoplemonApplication.getMainFlow(); // reference to budgetapplication class
        dispatcher = new ScreenplayDispatcher(this, container); // controls ui for moving views in and off of screen. container is the view that we are going to move in and out of.
        dispatcher.setUp(flow); // set up based on what is in flow.

//        UserStore.getInstance().setToken(null);


        if (UserStore.getInstance().getToken() == null || UserStore.getInstance().getTokenExpiration() == null) {
            History newHistory = History.single(new LoginStage());
            flow.setHistory(newHistory, Flow.Direction.REPLACE); // tak
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }

            if ((ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }

            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    public void onBackPressed() { // when you press back button,
        if (!flow.goBack()) {
            flow.removeDispatcher(dispatcher);
            flow.setHistory(History.single(new LoginStage()),
                    Flow.Direction.BACKWARD);
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Flow flow = PeoplemonApplication.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new EditStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);


        return super.onOptionsItemSelected(item);
    }


    public void showMenu(boolean show){
        if(menu != null){
            menu.findItem(R.id.open_edit);
        }
    }


    public void getImage(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imageString = cursor.getString(columnIndex);
                cursor.close();

                Bitmap bm = BitmapFactory.decodeFile(imageString);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] bytes = baos.toByteArray();

                String encodedImage = Base64.encodeToString(bytes, Base64.DEFAULT);


                EventBus.getDefault().post(new ImageLoadedEvent(imageString)); // everything that is loaded on EventBus is fired off.
                //the three second rule, like the rules you learned in school. It's designed to keep my man in line as long as he knows the 3 second rule.
            }else{

            }
        }catch(Exception e){
            Toast.makeText(this, "Error Retrieving Image", Toast.LENGTH_SHORT).show();
        }
    }
}