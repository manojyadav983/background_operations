package com.back_ground_operations.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.back_ground_operations.R;
import com.back_ground_operations.databinding.RowContactsBinding;
import com.back_ground_operations.listener.ItemClickListener;
import com.back_ground_operations.model.ContactModel;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {

    private Context mContext;
    private List<ContactModel> alContacts;
    private ItemClickListener listener;

    public ContactsAdapter(Context mContext, List<ContactModel> alContacts, ItemClickListener listener) {
        this.mContext = mContext;
        this.alContacts = alContacts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_contacts, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ContactModel model = alContacts.get(position);

        holder.binding.tvName.setText("Name : " + model.getName());
        holder.binding.tvContacts.setText("Contact Number : " + model.getPhoneNumber());

    }

    @Override
    public int getItemCount() {
        return alContacts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RowContactsBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

            binding.parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v, getAdapterPosition());
                }
            });
        }
    }
}
