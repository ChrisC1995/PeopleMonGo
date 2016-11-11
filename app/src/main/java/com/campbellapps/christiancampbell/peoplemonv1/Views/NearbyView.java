package com.campbellapps.christiancampbell.peoplemonv1.Views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.campbellapps.christiancampbell.peoplemonv1.Adapters.NearbyListAdapter;
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
 * Created by christiancampbell on 11/11/16.
 */

public class NearbyView extends LinearLayout {

    private Context context;
    private RestClient restClient;
    private NearbyListAdapter adapter;


    @Bind(R.id.recycler_view2)
    RecyclerView recyclerView2;






    public NearbyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        restClient = new RestClient();

        adapter = new NearbyListAdapter(new ArrayList<Auth>(), context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView2.setLayoutManager(linearLayoutManager);
        recyclerView2.setAdapter(adapter);


        loadNearby();
    }

    public void loadNearby(){
        restClient.getApiService().getUsers(500).enqueue(new Callback<Auth[]>() {
            @Override
            public void onResponse(Call<Auth[]> call, Response<Auth[]> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "People Received", Toast.LENGTH_SHORT).show();
                    adapter.nearbyPeople = new ArrayList<Auth>(Arrays.asList(response.body()));
                    for(Auth item: adapter.nearbyPeople){
                        adapter.notifyDataSetChanged();
                    }

                }else{
                    Toast.makeText(context, "People Not Received", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Auth[]> call, Throwable t) {
                Toast.makeText(context, "Failure Receiving nearby users", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
