package edu.upc.dsa.rankingStaff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.upc.dsa.R;
import edu.upc.dsa.models.Player;

public class RanCoins extends Fragment {

    private RanCoinsAdap adapter;
    private RecyclerView recyclerView;

    public RanCoins() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ranking_recyclerview, container, false);
        List<Player> playerList=new ArrayList<>();

        playerList.add(new Player(R.drawable.ic_menu_profile, "null", 10.0, "Julia", 1000, "Capitan", 1000, 1000));
        playerList.add(new Player(R.drawable.ic_menu_profile, "null", 9.0, "Pau", 900, "Capitan", 900, 900));
        playerList.add(new Player(R.drawable.ic_menu_profile, "null", 8.0, "Caty", 800, "Capitan", 800, 800));
        playerList.add(new Player(R.drawable.ic_menu_profile, "null", 7.0, "Marc", 700, "Capitan", 700, 700));
        playerList.add(new Player(R.drawable.ic_menu_profile, "null", 6.0, "Arnau", 600, "Capitan", 600, 600));
        playerList.add(new Player(R.drawable.ic_menu_profile, "null", 5.0, "Edu", 500, "Capitan", 500, 500));

        recyclerView = (RecyclerView) rootView.findViewById(R.id.distanceRanking);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        adapter = new RanCoinsAdap(playerList,getContext());

        recyclerView.setAdapter(adapter);
        return rootView;
    }

}
