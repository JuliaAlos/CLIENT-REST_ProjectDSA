package edu.upc.dsa.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import edu.upc.dsa.R;

import edu.upc.dsa.models.Insignia;
import edu.upc.dsa.transferObjects.InsigniaTO;

public class InsigniaRecycler extends RecyclerView.Adapter<InsigniaRecycler.ViewHolder>{

    List<String> allInsignias = Arrays.asList("Welcome", "Diamond", "First_purchase", "5min", "1hour", "24/7playing", "Centimetre", "World", "Wealth");
    List<InsigniaTO> listInsigniasPlayer;
    Context context;

    public InsigniaRecycler(Context context, List<InsigniaTO> listInsigniasPlayer){
        this.listInsigniasPlayer = listInsigniasPlayer;
        this.context = context;
    }



    @NonNull
    @Override
    public InsigniaRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.insignias_model,parent,false);
        return new InsigniaRecycler.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InsigniaRecycler.ViewHolder holder, int position) {
        //Once we have the data set, we put the data in the holder to display it.
        String model = this.allInsignias.get(position);
        System.out.println("SIZE: " + this.listInsigniasPlayer.size());

        int found = 0;
        int i = 0;
        while ((i < this.listInsigniasPlayer.size()) && (found == 0)) {
            InsigniaTO insignia = this.listInsigniasPlayer.get(i);
            if (model.equals(insignia.getName())) {
                found = 1;
                switch (model) {
                    case "Welcome":
                        holder.imageView.setImageResource(R.drawable.welcome);
                        break;
                    case "First_purchase":
                        holder.imageView.setImageResource(R.drawable.first_purchase);
                        break;
                    case "5min":
                        holder.imageView.setImageResource(R.drawable.five_min);
                        break;
                    case "1hour":
                        holder.imageView.setImageResource(R.drawable.onehour);
                        break;
                    case "24/7playing":
                        holder.imageView.setImageResource(R.drawable.all_day_playing);
                        break;
                    case "Centimetre":
                        holder.imageView.setImageResource(R.drawable.centimetre);
                        break;
                    case "World":
                        holder.imageView.setImageResource(R.drawable.world);
                        break;
                    case "Wealth":
                        holder.imageView.setImageResource(R.drawable.wealth);
                        break;
                    case "Diamond":
                        holder.imageView.setImageResource(R.drawable.diamond);
                        break;
                }
            } else {
                switch (model) {
                    case "Welcome":
                        holder.imageView.setImageResource(R.drawable.welcome);
                        break;
                    case "First_purchase":
                        holder.imageView.setImageResource(R.drawable.first_purchase_blocked);
                        break;
                    case "5min":
                        holder.imageView.setImageResource(R.drawable.five_min_blocked);
                        break;
                    case "1hour":
                        holder.imageView.setImageResource(R.drawable.onehour_blocked);
                        break;
                    case "24/7playing":
                        holder.imageView.setImageResource(R.drawable.all_day_playing_blocked);
                        break;
                    case "Centimetre":
                        holder.imageView.setImageResource(R.drawable.centimetre_blocked);
                        break;
                    case "World":
                        holder.imageView.setImageResource(R.drawable.world_blocked);
                        break;
                    case "Wealth":
                        holder.imageView.setImageResource(R.drawable.wealth_blocked);
                        break;
                    case "Diamond":
                        holder.imageView.setImageResource(R.drawable.diamond_blocked);
                        break;
                }
            }
            i++;
        }

        //For the case where the user does not have any airplane yet.
        if (this.listInsigniasPlayer.size() == 0) {
            switch (model) {
                case "Welcome":
                    holder.imageView.setImageResource(R.drawable.welcome);
                    break;
                case "First_purchase":
                    holder.imageView.setImageResource(R.drawable.first_purchase_blocked);
                    break;
                case "5min":
                    holder.imageView.setImageResource(R.drawable.five_min_blocked);
                    break;
                case "1hour":
                    holder.imageView.setImageResource(R.drawable.onehour_blocked);
                    break;
                case "24/7playing":
                    holder.imageView.setImageResource(R.drawable.all_day_playing_blocked);
                    break;
                case "Centimetre":
                    holder.imageView.setImageResource(R.drawable.centimetre_blocked);
                    break;
                case "World":
                    holder.imageView.setImageResource(R.drawable.world_blocked);
                    break;
                case "Wealth":
                    holder.imageView.setImageResource(R.drawable.wealth_blocked);
                    break;
                case "Diamond":
                    holder.imageView.setImageResource(R.drawable.diamond_blocked);
                    break;
            }

        }

    }

    @Override
    public int getItemCount() {
        return allInsignias.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView16);

        }
    }
}
