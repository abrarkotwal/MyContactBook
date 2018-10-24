package com.abrarkotwal.mycontactbook.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.abrarkotwal.mycontactbook.Adapter.Pojo.Contacts;
import com.abrarkotwal.mycontactbook.R;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends Adapter<ContactAdapter.ViewItemHolder> implements Filterable {
    private Context context;
    private List<Contacts> contactList;
    private List<Contacts> mFilteredList;

    public ContactAdapter(Context context, List<Contacts> contactList) {
        this.context = context;
        this.contactList = contactList;
        this.mFilteredList = contactList;
    }

    public class ViewItemHolder extends ViewHolder {
        public TextView contactNumber,contactName;

        public ViewItemHolder(View itemView) {
            super(itemView);
            this.contactNumber = (TextView) itemView.findViewById(R.id.contactNumber);
            this.contactName = (TextView) itemView.findViewById(R.id.contactName);
        }
    }

    @NonNull
    public ViewItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.display_single_contact, parent, false));
    }

    public void onBindViewHolder(@NonNull ViewItemHolder holder, int position) {
        holder.contactNumber.setText(contactList.get(position).getContactNumber());
        holder.contactName.setText(contactList.get(position).getContactName());
    }

    public int getItemCount() {
        return contactList.size();
    }


    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    contactList = mFilteredList;
                } else {

                    List<Contacts> filteredList = new ArrayList<>();

                    for (Contacts contacts : contactList) {

                        if (contacts.getContactName().toLowerCase().contains(charString)
                                || contacts.getContactNumber().toLowerCase().contains(charString)) {
                            filteredList.add(contacts);
                        }
                    }

                    contactList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactList = (List<Contacts>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
