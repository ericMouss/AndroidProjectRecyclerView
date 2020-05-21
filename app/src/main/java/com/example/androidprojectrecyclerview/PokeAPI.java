package com.example.androidprojectrecyclerview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeAPI {
    @GET("/api/v2/pokemon")
    static Call<List<RestResponse>> getPokemonResponse() {
        return null;
    }

    @GET("/api/v2/ability")
    Call<List<RestResponse>> getability();
}
