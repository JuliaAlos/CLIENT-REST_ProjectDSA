package edu.upc.dsa.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

import edu.upc.dsa.RecyclerAdapter;
import edu.upc.dsa.transferObjects.InsigniaTO;

public class InsigniaRecycler extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    List<String> allInsignias = Arrays.asList("Diamond", "first_flight", "first_sale", "sold_out", "zombie", "marathon", "scorched");
    List<InsigniaTO> listInsigniasPlayer;
    Context context;

    public RecyclerAdapter(Context context, List<InsigniaTO> listInsigniasPlayer){
        this.listInsigniasPlayer = listInsigniasPlayer;
        this.context = context;
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
