package edu.upc.dsa.rankingStaff;

import android.content.Context;
import android.content.SharedPreferences;
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
import java.util.ArrayList;
import java.util.List;

import edu.upc.dsa.ApiInterface;
import edu.upc.dsa.R;
import edu.upc.dsa.models.Player;
import edu.upc.dsa.transferObjects.UserTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RanDistance extends Fragment {

    RanDistanceAdap adapter;
    RecyclerView recyclerView;
    ApiInterface apiInterface;
    List<UserTO> listUsers;
    public static final String BASE_URL = "http://147.83.7.203:8080/dsaApp/";

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
            getByDistance();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    public void initializeRecyclerView(List<Player> playerList){
        adapter = new RanDistanceAdap(playerList,getContext());
        recyclerView.setAdapter(adapter);LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
    }

    private void getByDistance () throws IOException {
        Call<List<UserTO>> call = apiInterface.getByDistance();
        call.enqueue(new Callback<List<UserTO>>() {
            @Override
            public void onResponse(Call<List<UserTO>> call, Response<List<UserTO>> response) {
                if (!response.isSuccessful()) {
                    Log.d("Ranking", "Error" + response.code());
                    return;
                }
                listUsers = response.body();
                List<Player> playerList = new ArrayList<Player>();
                for (UserTO user : listUsers) {
                    playerList.add(new Player(R.drawable.ic_menu_profile, "", 10.0, user.getPlayer().getPlayerName(), user.getPlayer().getMaxDistance(), user.getPlayer().getRol(), user.getPlayer().getTimeOfFlight(), user.getPlayer().getBitcoins()));
                }
                Log.d("Ranking", "Players: " + playerList);
                initializeRecyclerView(playerList);
            }

            @Override
            public void onFailure(Call<List<UserTO>> call, Throwable t) {
                Log.d("Ranking", "Error:" + t.getMessage());
            }
        });
    }
}
