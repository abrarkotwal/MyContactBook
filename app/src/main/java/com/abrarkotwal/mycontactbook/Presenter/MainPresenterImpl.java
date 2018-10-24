package com.abrarkotwal.mycontactbook.Presenter;

import com.abrarkotwal.mycontactbook.Adapter.Pojo.Contacts;
import com.abrarkotwal.mycontactbook.Model.GetContactInteractor;
import com.abrarkotwal.mycontactbook.View.MainView;

import java.util.List;

public class MainPresenterImpl implements MainPresenter, GetContactInteractor.OnFinishedListener {

    private MainView mainView;
    private GetContactInteractor getContactInteractor;

    public MainPresenterImpl(MainView mainView, GetContactInteractor getContactInteractor) {
        this.mainView = mainView;
        this.getContactInteractor = getContactInteractor;
    }

    @Override
    public void loadContacts() {
        if (mainView != null) {
            mainView.showProgress();
        }

        getContactInteractor.getNextQuote(this);
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void onFinished(List<Contacts> contactsList) {
        if (mainView != null) {
            mainView.setQuote(contactsList);
            mainView.hideProgress();
        }
    }
}