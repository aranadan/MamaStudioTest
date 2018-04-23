package com.fox.andrey.mamastudiotest;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class EventsActivity extends AppCompatActivity {
    TabLayout tabLayout;
    List<Message> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle(R.string.action_bar_title);

        if (savedInstanceState != null) {
            list = (List<Message>) savedInstanceState.getSerializable("list");
            replaceFragment();
        } else {
            list = new ArrayList<>();
            getMessages();

        }


        tabLayout = findViewById(R.id.tabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (list.size() == 0){
                    getMessages();
                } else {
                replaceFragment();}
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    public int getTabPosition() {
        return tabLayout.getSelectedTabPosition();
    }

    public void replaceFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameCont, new ListFragment());
        fragmentTransaction.commit();
    }

    private void getMessages() {

        if (isOnline()) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://mama-studio.com/tt/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MessageApi messagesApi = retrofit.create(MessageApi.class);

            Observable<List<Message>> messageObservable = messagesApi.getMessages();

            messageObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(messages -> list.addAll(messages), throwable -> Log.d("TAG", throwable.getMessage()), () -> replaceFragment());
        }else {
            Toast.makeText(EventsActivity.this, "Check internet connection.", Toast.LENGTH_SHORT).show();
        }
    }

    public List<Message> getEventsList() {
        List<Message> arrayList = new ArrayList<>();
        for (Message message : list) {
            if (message.getType().equals("event")) {
                arrayList.add(message);
            }
        }
        return arrayList;
    }

    public List<Message> getShopsList() {
        List<Message> arrayList = new ArrayList<>();
        for (Message message : list) {
            if (message.getType().equals("shop")) {
                arrayList.add(message);
            }
        }
        return arrayList;
    }

    //save my list
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("list", (Serializable) list);
    }

    //check internet connection
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}


