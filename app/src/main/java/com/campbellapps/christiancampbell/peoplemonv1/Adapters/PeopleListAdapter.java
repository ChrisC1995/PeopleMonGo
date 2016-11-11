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

public class PeopleListAdapter extends RecyclerView.Adapter<PeopleListAdapter.ExpenseHolder> {

    public ArrayList<Auth> caughtPeople;
    private Context context;

    public PeopleListAdapter(ArrayList<Auth> caughtPeople, Context context){
        this.caughtPeople = caughtPeople;
        this.context = context;
    }


    @Override
    public ExpenseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedview = LayoutInflater.from(context).inflate(R.layout.people_list_view_item, parent, false);
        return new ExpenseHolder(inflatedview);
    }

    @Override
    public void onBindViewHolder(ExpenseHolder holder, int position) {
        Auth peoplemon = caughtPeople.get(position);
        holder.bindExpense(peoplemon);
    }

    @Override
    public int getItemCount() {
        return caughtPeople == null ? 0 : caughtPeople.size();
    }


    class ExpenseHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.item_text_view)
        TextView textField;

        @Bind(R.id.date_text_view)
        TextView dateField;
        long date = System.currentTimeMillis();



        public ExpenseHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView); // because we are in a view holder, do context and view.
        }


        public void bindExpense(final Auth expense){ // pass an expense in and do these things to it.
            textField.setText("PeopleMon: " + expense.getUserName());
            dateField.setText("Account Created: " +expense.getDate());
        }
    }
}
