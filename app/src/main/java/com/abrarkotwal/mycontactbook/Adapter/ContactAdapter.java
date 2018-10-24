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
import android.widget.ImageView;
import android.widget.TextView;

import com.abrarkotwal.mycontactbook.Adapter.Pojo.Contacts;
import com.abrarkotwal.mycontactbook.R;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

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
        public ImageView icon;

        public ViewItemHolder(View itemView) {
            super(itemView);
            this.contactNumber = (TextView) itemView.findViewById(R.id.contactNumber);
            this.contactName = (TextView) itemView.findViewById(R.id.contactName);
            this.icon = (ImageView) itemView.findViewById(R.id.image_view);
        }
    }

    @NonNull
    public ViewItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.display_single_contact, parent, false));
    }

    public void onBindViewHolder(@NonNull ViewItemHolder holder, int position) {
        Contacts contacts = contactList.get(position);
        holder.contactNumber.setText(contacts.getContactNumber());
        holder.contactName.setText(contacts.getContactName());

        String firstLetter = String.valueOf(contacts.getContactName().charAt(0));

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(position);
        TextDrawable drawable = TextDrawable
                .builder()
                .buildRound(firstLetter, color);

        holder.icon.setImageDrawable(drawable);

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
