package com.back_ground_operations.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.back_ground_operations.R;
import com.back_ground_operations.adapter.ContactsAdapter;
import com.back_ground_operations.databinding.FragmentAsynBinding;
import com.back_ground_operations.listener.ItemClickListener;
import com.back_ground_operations.model.ContactModel;
import com.back_ground_operations.utils.AppConstant;
import com.back_ground_operations.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class AsynFragment extends Fragment {

    private Context mContext;
    private FragmentAsynBinding binding;
    private List<ContactModel> alContacts = new ArrayList<>();
    private ContactsAdapter adapter;
    private ListViewContactsLoader listViewContactsLoader = new ListViewContactsLoader();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_asyn, container, false);
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setContactRecycler();
    }

    private void setContactRecycler() {
        binding.rvContacts.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        binding.rvContacts.setLayoutManager(layoutManager);
        adapter = new ContactsAdapter(mContext, alContacts, new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        });
        binding.rvContacts.setAdapter(adapter);

        fetchContact();
    }

    private void fetchContact() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, AppConstant.CONTACT_REQUEST);
        } else {
            listViewContactsLoader.execute();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppConstant.CONTACT_REQUEST) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchContact();
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    requestContactPermission();
                }
            }
        }
    }

    private void requestContactPermission() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            fetchContact();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, AppConstant.CONTACT_REQUEST);
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ListViewContactsLoader extends AsyncTask<Void, Void, List<ContactModel>> {
        @Override
        protected List<ContactModel> doInBackground(Void... params) {
            final List<ContactModel> contactObject = new ArrayList<>();
            Uri contactsUri = ContactsContract.Contacts.CONTENT_URI;
            if (getActivity() != null) {
                Cursor contactsCursor = getActivity().getContentResolver().query(contactsUri,
                        null, null, null,
                        ContactsContract.Contacts.DISPLAY_NAME + " ASC ");
                if (contactsCursor != null && contactsCursor.moveToFirst()) {
                    do {
                        if (!listViewContactsLoader.isCancelled()) {
                            long contactId = contactsCursor.getLong(contactsCursor.getColumnIndex("_ID"));
                            Uri dataUri = ContactsContract.Data.CONTENT_URI;
                            Cursor dataCursor = getActivity().getContentResolver().query(dataUri,
                                    null, ContactsContract.Data.CONTACT_ID + "=" + contactId,
                                    null, null);

                            String displayName = "";
                            String nickName = "";
                            String Mobile = "";

                            if (dataCursor != null && dataCursor.moveToFirst()) {
                                displayName = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                                do {
                                    if (dataCursor.getString(dataCursor.getColumnIndex("mimetype")).equals(ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE))
                                        nickName = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                    if (dataCursor.getString(dataCursor.getColumnIndex("mimetype"))
                                            .equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                                        if (dataCursor.getInt(dataCursor.getColumnIndex("data2")) == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) {
                                            Mobile = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                        }
                                    }
                                } while (dataCursor.moveToNext());

                                final ContactModel contacts = new ContactModel();
                                contacts.setName(displayName);
                                contacts.setPhoneNumber(Mobile);
                                contactObject.add(contacts);

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        alContacts.add(contacts);
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            } else {
                                break;
                            }
                        }
                    } while (contactsCursor.moveToNext());
                }
            }
            return contactObject;
        }

        @Override
        protected void onPostExecute(List<ContactModel> result) {
            if (result.isEmpty()) {
                CommonUtils.showToast(mContext, getString(R.string.no_contacts));
            } else {
                CommonUtils.showToast(mContext, result.size() + getString(R.string.contact_fetched));
            }
        }
    }

    @Override
    public void onStop() {
        listViewContactsLoader.cancel(true);
        super.onStop();
    }

}
