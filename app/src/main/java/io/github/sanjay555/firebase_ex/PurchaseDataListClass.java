// Addappter for (view list)
package io.github.sanjay555.firebase_ex;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sanjayshr on 3/7/17.
 */

public class PurchaseDataListClass extends ArrayAdapter<PurchaseDetails> {

    private Activity context;
    private List<PurchaseDetails> purchaseDetailsList;

//    Create Constructor so it will init all the values

    public PurchaseDataListClass(Activity context, List<PurchaseDetails> purchaseDetailsList) {
        super(context, R.layout.custom_purchasedates, purchaseDetailsList);
        this.context = context;
        this.purchaseDetailsList = purchaseDetailsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.custom_purchasedates, null, true);

        TextView textViewDates = (TextView) listViewItem.findViewById(R.id.textViewPurchaseDate);

        PurchaseDetails purchaseDetails = purchaseDetailsList.get(position);

//        set here what you wanna show as a list
        textViewDates.setText(purchaseDetails.getDates());
        return listViewItem;
    }
}
