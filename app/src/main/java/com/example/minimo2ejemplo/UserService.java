package com.example.minimo2ejemplo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {

    @GET("{user}")
    Call<User> getInfo(@Path("user") String name);

    @GET("{user}/followers")
    Call<List<Followers>> getFollower(@Path("user") String name);

}
