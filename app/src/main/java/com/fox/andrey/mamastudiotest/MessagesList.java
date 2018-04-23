package com.fox.andrey.mamastudiotest;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessagesList {

    private static MessagesList instance;
    private List<Message> list;

    private MessagesList(){

        list = new ArrayList<>();

        getMessages();
    }

    public static MessagesList getInstance() {

        if (instance == null) {

            instance = new MessagesList();
        }

        return instance;

    }

    private void getMessages(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://mama-studio.com/tt/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MessageApi messagesApi = retrofit.create(MessageApi.class);

        Observable<List<Message>> messageObservable = messagesApi.getMessages();
        messageObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(messages -> list.addAll(messages)
                        ,throwable -> Log.d("TAG", throwable.getMessage())
                        ,()-> Log.d("TAGGGG", "Downloaded")
                );
    }

    public List<Message> getList() {
        return list;
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


}


