package com.campbellapps.christiancampbell.peoplemonv1.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.campbellapps.christiancampbell.peoplemonv1.Models.Auth;
import com.campbellapps.christiancampbell.peoplemonv1.Network.RestClient;
import com.campbellapps.christiancampbell.peoplemonv1.PeoplemonApplication;
import com.campbellapps.christiancampbell.peoplemonv1.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by christiancampbell on 11/7/16.
 */

public class RegisterView extends LinearLayout {
    private Context context;

    @Bind(R.id.username_field)
    EditText usernamefield;

    @Bind(R.id.password_field)
    EditText passwordField;


    @Bind(R.id.register_button)
    Button registerButton;

    @Bind(R.id.email_field)
    EditText emailField;

    @Bind(R.id.confirm_field)
    EditText confirmField;

    @Bind(R.id.spinner)
    ProgressBar spinner;




    public RegisterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register_button)
    public void register(){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE); //sets up imm
        imm.hideSoftInputFromWindow(usernamefield.getWindowToken(), 0); // gets keyboard off when when username is written.
        imm.hideSoftInputFromWindow(passwordField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(emailField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(confirmField.getWindowToken(),0);

        String username = usernamefield.getText().toString();
        String password = passwordField.getText().toString();
        String email = emailField.getText().toString();
        String confirm = confirmField.getText().toString();

        if(username.isEmpty() || password.isEmpty() || email.isEmpty() || confirm.isEmpty()){
            Toast.makeText(context, "You must provide a username, password, and email", Toast.LENGTH_SHORT).show();
        }
        else if (!password.equals(confirm)){
            Toast.makeText(context, "Passwords must match", Toast.LENGTH_SHORT).show();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(context, "You entered an invalid email address", Toast.LENGTH_SHORT).show();
        }
        else{
            registerButton.setEnabled(false);
            spinner.setVisibility(VISIBLE);

            Auth account = new Auth(email, username, password, "iOSandroid301november2016", "");
            RestClient restClient = new RestClient();
            restClient.getApiService().register(account).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){




                        Toast.makeText(context, "Successful Registration", Toast.LENGTH_SHORT).show();
                        resetView();
                        Flow flow = PeoplemonApplication.getMainFlow();
                        flow.goBack(); // go back method
                    }
                    else{
                        Toast.makeText(context, "Register Failed", Toast.LENGTH_SHORT).show();
                        resetView();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context, "Register Failed!", Toast.LENGTH_SHORT).show();
                    resetView();
                }
            });
        }




    }

    private void resetView(){
        registerButton.setEnabled(true);
        spinner.setVisibility(GONE);

    }


}
