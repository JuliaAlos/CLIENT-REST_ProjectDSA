package edu.upc.dsa.ui.main;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import edu.upc.dsa.Insignia;
import edu.upc.dsa.RecyclerAdapter;
import edu.upc.dsa.transferObjects.InsigniaTO;

public class InsigniaRecycler extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    List<String> allInsignias = Arrays.asList("Diamond", "first_flight", "first_sale", "sold_out", "zombie", "marathon", "scorched");
    List<InsigniaTO> listInsigniasPlayer;
    Context context;

    public InsigniaRecycler(Context context, List<InsigniaTO> listInsigniasPlayer){
        this.listInsigniasPlayer = listInsigniasPlayer;
        this.context = context;
    }

    public InsigniaRecycler(Insignia insignia, List<InsigniaTO> listInsigniasPlayer) {
    }


    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
