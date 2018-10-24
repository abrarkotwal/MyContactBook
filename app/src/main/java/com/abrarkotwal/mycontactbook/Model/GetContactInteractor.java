package com.abrarkotwal.mycontactbook.Model;

import com.abrarkotwal.mycontactbook.Adapter.Pojo.Contacts;

import java.util.List;

public interface GetContactInteractor {

    interface OnFinishedListener {
        void onFinished(List<Contacts> contactList);
    }
    void getNextQuote(OnFinishedListener listener);
}
