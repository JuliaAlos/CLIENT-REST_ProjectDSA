package edu.upc.dsa.rankingStaff;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import edu.upc.dsa.ApiInterface;
import edu.upc.dsa.R;
import edu.upc.dsa.transferObjects.RankingTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RanTime extends Fragment {

    RanTimeAdap adapter;
    RecyclerView recyclerView;
    ApiInterface apiInterface;
    List<RankingTO> listUsers;
    public static final String BASE_URL = "http://147.83.7.203:8080/dsaApp/";

    public RanTime() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.ranking_recyclerview, container, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.distanceRanking);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        try {
            getByTime();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rootView;
    }


    public void initializeRecyclerView(List<RankingTO> playerList){
        adapter = new RanTimeAdap(playerList,getContext());
        recyclerView.setAdapter(adapter);LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
    }

    private void getByTime () throws IOException {
        Call<List<RankingTO>> call = apiInterface.getByTime();
        call.enqueue(new Callback<List<RankingTO>>() {
            @Override
            public void onResponse(Call<List<RankingTO>> call, Response<List<RankingTO>> response) {
                if (!response.isSuccessful()) {
                    Log.d("Ranking", "Error" + response.code());
                    return;
                }
                listUsers = response.body();
                Log.d("Ranking", "Respuesta" + response.body().get(0).getScore());
                initializeRecyclerView(listUsers);
            }

            @Override
            public void onFailure(Call<List<RankingTO>> call, Throwable t) {
                Log.d("Ranking", "Error:" + t.getMessage());
            }
        });
    }
}
