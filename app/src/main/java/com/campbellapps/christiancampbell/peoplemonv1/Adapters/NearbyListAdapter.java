package com.campbellapps.christiancampbell.peoplemonv1.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.campbellapps.christiancampbell.peoplemonv1.Models.Auth;
import com.campbellapps.christiancampbell.peoplemonv1.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by christiancampbell on 11/9/16.
 */

public class NearbyListAdapter extends RecyclerView.Adapter<NearbyListAdapter.NearbyExpenseHolder> {

    public ArrayList<Auth> nearbyPeople;
    private Context context;

    public NearbyListAdapter(ArrayList<Auth> nearbyPeople, Context context){
        this.nearbyPeople = nearbyPeople;
        this.context = context;
    }


    @Override
    public NearbyExpenseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedview = LayoutInflater.from(context).inflate(R.layout.nearby_list_item, parent, false);
        return new NearbyExpenseHolder(inflatedview);
    }

    @Override
    public void onBindViewHolder(NearbyExpenseHolder holder, int position) {
        Auth peoplemon = nearbyPeople.get(position);
        holder.bindExpense(peoplemon);
    }

    @Override
    public int getItemCount() {
        return nearbyPeople == null ? 0 : nearbyPeople.size();
    }


    class NearbyExpenseHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.nearby_text)
        TextView textField;

        public NearbyExpenseHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView); // because we are in a view holder, do context and view.
        }


        public void bindExpense(final Auth expense){ // pass an expense in and do these things to it.
            textField.setText(expense.getUserName());
        }
    }
}
