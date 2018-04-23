package com.fox.andrey.mamastudiotest;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MessageApi {
        @GET("TT.json")
        Observable<List<Message>> getMessages();
}
