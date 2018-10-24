package com.abrarkotwal.mycontactbook.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.abrarkotwal.mycontactbook.Adapter.Pojo.Contacts;
import com.abrarkotwal.mycontactbook.Model.GetContactInteractorImpl;
import com.abrarkotwal.mycontactbook.Presenter.MainPresenter;
import com.abrarkotwal.mycontactbook.Presenter.MainPresenterImpl;
import com.abrarkotwal.mycontactbook.R;
import com.abrarkotwal.mycontactbook.Adapter.ContactAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements MainView {
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.contactRecyclerView)
    RecyclerView contactRecyclerView;
    MainPresenter presenter;

    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this, this);

        presenter = new MainPresenterImpl(this, new GetContactInteractorImpl(this));
        presenter.loadContacts();

        contactRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactRecyclerView.setHasFixedSize(true);
        contactRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        contactRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(GONE);
        contactRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setContactsList(List<Contacts> contactList) {
        Log.d("Abrar", String.valueOf(contactList.size()));
        removeDuplicatesTwo(contactList);
        Collections.sort(contactList, new Comparator<Contacts>() {
            @Override
            public int compare(Contacts con1, Contacts con2) {
                return con1.getContactName().compareTo(con2.getContactName());
            }
        });
        adapter = new ContactAdapter(this, contactList);
        contactRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        GetContactInteractorImpl.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void removeDuplicatesTwo(List<Contacts> contactsList) {
        for (int i = 0; i < contactsList.size(); i++) {
            for (int j = i + 1; j < contactsList.size(); j++) {
                if (contactsList.get(i).getContactNumber().equals(contactsList.get(j).getContactNumber())) {
                    contactsList.remove(j);
                    j--;
                }
            }
        }
        Log.d("Abrar", String.valueOf(contactsList.size()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);

        return super.onCreateOptionsMenu(menu);
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }


}