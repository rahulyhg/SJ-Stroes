package io.github.sanjay555.firebase_ex;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanjayshr on 6/7/17.
 * Adapter
 */

public class GiveAmountList extends ArrayAdapter<GivenAmountDetails> {

    public double total = 0.0;
    private Activity context;
    private List<GivenAmountDetails> givenAmountDateilsList;
    private ArrayList<GiveAmountList> givenTotalAmount;

    public GiveAmountList(Activity context1, List<GivenAmountDetails> givenAmountDateilsList) {
        super(context1, R.layout.custom_givenamountdates, givenAmountDateilsList);
        this.context = context1;
        this.givenAmountDateilsList = givenAmountDateilsList;
        this.givenTotalAmount = givenTotalAmount;

//        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.custom_givenamountdates, null, true); // pass a blank layout here

//        Get position in layout
        TextView textViewDates = (TextView) listViewItem.findViewById(R.id.textViewGivenAmountDates);
        TextView textViewAmount = (TextView) listViewItem.findViewById(R.id.textViewGivenAmount);


        GivenAmountDetails givenAmountDetails = givenAmountDateilsList.get(position);

//      Set values using  Getter
        textViewDates.setText(givenAmountDetails.getGivenGivenamountDate());
        String getTodayGivenAmountString = Double.toString(givenAmountDetails.getTodayGiveAmount());
        textViewAmount.setText(getTodayGivenAmountString);


        return listViewItem;
    }

    }





