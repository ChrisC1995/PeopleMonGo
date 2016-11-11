package com.campbellapps.christiancampbell.peoplemonv1.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.campbellapps.christiancampbell.peoplemonv1.Models.Auth;
import com.campbellapps.christiancampbell.peoplemonv1.Network.RestClient;
import com.campbellapps.christiancampbell.peoplemonv1.Network.UserStore;
import com.campbellapps.christiancampbell.peoplemonv1.PeoplemonApplication;
import com.campbellapps.christiancampbell.peoplemonv1.R;
import com.campbellapps.christiancampbell.peoplemonv1.Stages.MapViewStage;
import com.campbellapps.christiancampbell.peoplemonv1.Stages.RegisterStage;

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

public class LoginView extends LinearLayout {
    private Context context;

    @Bind(R.id.username_field)
    EditText usernamefield;

    @Bind(R.id.password_field)
    EditText passwordField;

    @Bind(R.id.login_button)
    Button loginButton;

    @Bind(R.id.register_button)
    Button registerButton;

    @Bind(R.id.spinner)
    ProgressBar spinner;

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register_button) //better on click listener
    public void showRegisterView() {
        Flow flow =  PeoplemonApplication.getMainFlow(); // main flow throughtout app
        History newHistory = flow.getHistory().buildUpon().push(new RegisterStage()).build(); // pushes on the stack
        flow.setHistory(newHistory, Flow.Direction.FORWARD); // sets new direction and history forward. only use FORWARD or REPLACE, sometimes backwards
    }


    @OnClick(R.id.login_button)
    public void login(){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE); //sets up imm
        imm.hideSoftInputFromWindow(usernamefield.getWindowToken(), 0); // gets keyboard off when when username is written.
        imm.hideSoftInputFromWindow(passwordField.getWindowToken(), 0); // drops keyboard off the screen when password is written

        String email = usernamefield.getText().toString();
        String password = passwordField.getText().toString();
        String grantType = "password";




        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(context, "Provide all fields", Toast.LENGTH_SHORT).show();
        }
        else{
            loginButton.setEnabled(false); // disables login button
            registerButton.setEnabled(false); // disables register button
            spinner.setVisibility(VISIBLE); // shows spinner

            RestClient restClient = new RestClient();
            restClient.getApiService().login(email, password, grantType).enqueue(new Callback<Auth>() {
                @Override
                public void onResponse(Call<Auth> call, Response<Auth> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(context, "Log in Successful", Toast.LENGTH_SHORT).show();
                        resetView();
                        
                        Auth authUser = response.body(); // info from response
                        UserStore.getInstance().setToken(authUser.getAccessToken());
                        UserStore.getInstance().setTokenExpriation(authUser.getExpires());
                        Toast.makeText(context, UserStore.getInstance().getToken().toString(), Toast.LENGTH_SHORT).show();



                        Flow flow = PeoplemonApplication.getMainFlow();
                        History newHistory = History.single(new MapViewStage());
                        flow.setHistory(newHistory, Flow.Direction.FORWARD);
                    }else{
                        Toast.makeText(context, "Log in failed 1", Toast.LENGTH_SHORT).show();
                        resetView();
                    }
                }

                @Override
                public void onFailure(Call<Auth> call, Throwable t) {
                    Toast.makeText(context, "Log In Failed 2", Toast.LENGTH_SHORT).show();
                    resetView();
                }
            });

        }
    }
    private void resetView(){
        loginButton.setEnabled(true); // sets all the buttons back to enabled and sets spinner back to gone.
        registerButton.setEnabled(true);
        spinner.setVisibility(GONE);
    }
}
