package com.abrarkotwal.mycontactbook.View;

import com.abrarkotwal.mycontactbook.Adapter.Pojo.Contacts;

import java.util.List;

public interface MainView {

    void showProgress();

    void hideProgress();

    void setQuote(List<Contacts> contactsList);
}
