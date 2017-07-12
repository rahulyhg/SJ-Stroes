// Adpater
package io.github.sanjay555.firebase_ex;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by sanjayshr on 1/7/17.
 * Adapter
 */

public class CustomerList extends ArrayAdapter<Customers> {

    private Activity context;
    private List<Customers> customerList;

    public CustomerList(Activity context, List<Customers> customerList){

        super(context, R.layout.list_customer, customerList); // blank(custom) layout

        this.context = context;
        this.customerList = customerList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_customer, null, true); // pass blank layout here

//        Set values here to display
        TextView textViewFName = (TextView) listViewItem.findViewById(R.id.textViewFName);
        TextView textViewLName = (TextView) listViewItem.findViewById(R.id.textViewLName);
        TextView textViewAddress = (TextView) listViewItem.findViewById(R.id.textViewAddress);

        Customers customers = customerList.get(position);

        textViewFName.setText(customers.getCustomerFName());
        textViewLName.setText(customers.getCustomerLName());
        textViewAddress.setText(customers.getCustomerAddress());

        return  listViewItem;

    }
}
