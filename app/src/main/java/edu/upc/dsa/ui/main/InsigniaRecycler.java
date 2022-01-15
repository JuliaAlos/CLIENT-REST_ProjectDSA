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

    List<String> allInsignias = Arrays.asList("Welcome", "Diamond", "First_purchase", "Zombie", "Scorched");
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
                    case "Diamond":
                        holder.imageView.setImageResource(R.drawable.diamond);
                        break;
                    case "First_purchase":
                        holder.imageView.setImageResource(R.drawable.first_purchase);
                        break;
                    case "Zombie":
                        holder.imageView.setImageResource(R.drawable.zombie);
                        break;
                    case "Scorched":
                        holder.imageView.setImageResource(R.drawable.scorched);
                        break;
                }
            } else {
                switch (model) {
                    case "Welcome":
                        holder.imageView.setImageResource(R.drawable.welcome);
                        break;
                    case "Diamond":
                        holder.imageView.setImageResource(R.drawable.diamond_blocked);
                        break;
                    case "First_purchase":
                        holder.imageView.setImageResource(R.drawable.first_purchase_blocked);
                        break;
                    case "Zombie":
                        holder.imageView.setImageResource(R.drawable.zombie_blocked);
                        break;
                    case "Scorched":
                        holder.imageView.setImageResource(R.drawable.scorched_blocked);
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
                case "Diamond":
                    holder.imageView.setImageResource(R.drawable.diamond_blocked);
                    break;
                case "First_purchase":
                    holder.imageView.setImageResource(R.drawable.first_purchase_blocked);
                    break;
                case "Zombie":
                    holder.imageView.setImageResource(R.drawable.zombie_blocked);
                    break;
                case "Scorched":
                    holder.imageView.setImageResource(R.drawable.scorched_blocked);
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
