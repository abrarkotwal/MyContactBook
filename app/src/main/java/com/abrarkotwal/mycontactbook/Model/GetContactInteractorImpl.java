package com.abrarkotwal.mycontactbook.Model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.abrarkotwal.mycontactbook.Adapter.Pojo.Contacts;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class GetContactInteractorImpl implements GetContactInteractor {

    @SuppressWarnings("serial")
    static
    List<Contacts> contactsList = new ArrayList<Contacts>();
    static Context context;
    static final int REQUEST_PERMISSION_READ_CONTACT = 1;

    public GetContactInteractorImpl(Context context) {
        this.context = context;
        showPhoneStatePermission();
    }

    @Override
    public void getNextQuote(final GetContactInteractor.OnFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onFinished(contactsList);
            }
        }, 1000);
    }

    private void showPhoneStatePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_CONTACTS)) {
                showExplanation("Permission Needed", "Rationale", Manifest.permission.READ_CONTACTS, REQUEST_PERMISSION_READ_CONTACT);
            } else {
                requestPermission(Manifest.permission.READ_CONTACTS, REQUEST_PERMISSION_READ_CONTACT);
            }
        } else {
           getContactDetail();
        }
    }


    public static void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_READ_CONTACT:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContactDetail();
                    Toast.makeText(context, "Permission Granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private static void getContactDetail() {
        Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        if (phones != null) {
            while (phones.moveToNext())
            {
                String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                phoneNumber = phoneNumber
                        .replaceAll("^0*", "")
                        .replace("+91","")
                        .replaceAll(" ","")
                        .replaceAll("-","");

                Contacts contacts = new Contacts(name,phoneNumber);
                contactsList.add(contacts);
            }
        }
        if (phones != null) {
            phones.close();
        }
    }

    private void showExplanation(String title, String message, final String permission, final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions((Activity) context, new String[]{permissionName}, permissionRequestCode);
    }

}