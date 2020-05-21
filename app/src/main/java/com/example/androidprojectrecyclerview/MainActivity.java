package com.example.androidprojectrecyclerview;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final String BASE_URL = "https://pokeapi.co/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        showList();
        makeApiCall();
    }

    private void showList(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<String> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }// define an adapter
        mAdapter = new ListAdapter(input);
        recyclerView.setAdapter(mAdapter);
    }


    private void makeApiCall(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        PokeAPI gerritAPI = retrofit.create(PokeAPI.class);

        Call<List<RestResponse>> call = PokeAPI.getPokemonResponse();
        call.enqueue(new Callback<List<RestResponse>>() {
            @Override
            public void onResponse(Call<List<RestResponse>> call, Response<List<RestResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Class<? extends List> pokemonList = response.body().getClass();
                    Toast.makeText(MainActivity.this, "API success", Toast.LENGTH_SHORT).show();
                }else{
                    showError();
                }
            }

            @Override
            public void onFailure(Call<List<RestResponse>> call, Throwable t) {
                showError();
            }
        });
    }
    private void showError(){
        Toast.makeText(this, "API error", Toast.LENGTH_SHORT).show();
    }
}
