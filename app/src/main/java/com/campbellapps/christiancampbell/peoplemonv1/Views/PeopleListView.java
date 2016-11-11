package com.campbellapps.christiancampbell.peoplemonv1.Views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.campbellapps.christiancampbell.peoplemonv1.Adapters.PeopleListAdapter;
import com.campbellapps.christiancampbell.peoplemonv1.MainActivity;
import com.campbellapps.christiancampbell.peoplemonv1.Models.Auth;
import com.campbellapps.christiancampbell.peoplemonv1.Network.RestClient;
import com.campbellapps.christiancampbell.peoplemonv1.R;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by christiancampbell on 11/9/16.
 */

public class PeopleListView extends RelativeLayout {

    private Context context;
    private RestClient restClient;
    private PeopleListAdapter peopleAdapter;


    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;




    public PeopleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        restClient = new RestClient();

        ((MainActivity)context).showMenu(false);

        peopleAdapter = new PeopleListAdapter(new ArrayList<Auth>(), context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(peopleAdapter);


        loadPeople();

    }



    public void loadPeople(){
        restClient.getApiService().userCaught().enqueue(new Callback<Auth[]>() {
            @Override
            public void onResponse(Call<Auth[]> call, Response<Auth[]> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "People Received", Toast.LENGTH_SHORT).show();
                    peopleAdapter.caughtPeople = new ArrayList<Auth>(Arrays.asList(response.body()));
                    for(Auth item: peopleAdapter.caughtPeople){
                        peopleAdapter.notifyDataSetChanged();
                    }

                }else{
                    Toast.makeText(context, "People Not Received", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Auth[]> call, Throwable t) {
                Toast.makeText(context, "Fail Receiving", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
