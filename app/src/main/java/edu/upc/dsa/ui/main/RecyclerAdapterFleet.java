package edu.upc.dsa.ui.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import edu.upc.dsa.InfoPlane;
import edu.upc.dsa.R;
import edu.upc.dsa.transferObjects.PlaneTO;

public class RecyclerAdapterFleet extends RecyclerView.Adapter<RecyclerAdapterFleet.ViewHolder> {
    List<String> allModels = Arrays.asList("Cessna", "Airbus", "Fighter", "Helicopter", "Acrobatic");
    List<PlaneTO> listPlanesPlayer;
    Context context;

    public RecyclerAdapterFleet(Context context, List<PlaneTO> listPlanesPlayer){
        this.listPlanesPlayer = listPlanesPlayer;
        this.context = context;
    }

    //To create the views.
    @NonNull
    @Override
    public RecyclerAdapterFleet.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fleet_model,parent,false);
        return new ViewHolder(view);
    }

    //To insert data into the views.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Once we have the data set, we put the data in the holder to display it.
        String model = this.allModels.get(position);

        int found = 0;
        int i = 0;
        while ((i < this.listPlanesPlayer.size()) && (found == 0)){
            PlaneTO plane = this.listPlanesPlayer.get(i);
            if (model.equals(plane.getModel())){
                found = 1;
                switch (model) {
                    case "Airbus":
                        holder.imageView.setImageResource(R.drawable.a320_entry);
                        break;
                    case "Cessna":
                        holder.imageView.setImageResource(R.drawable.cessna_entry);
                        break;
                    case "Fighter":
                        holder.imageView.setImageResource(R.drawable.fighter_entry);
                        break;
                    case "Helicopter":
                        holder.imageView.setImageResource(R.drawable.helicopter_entry);
                        break;
                    case "Acrobatic":
                        holder.imageView.setImageResource(R.drawable.acrobatic_entry);
                        break;
                }
            }
            else{
                switch (model) {
                    case "Airbus":
                        holder.imageView.setImageResource(R.drawable.a320_bw_entry);
                        break;
                    case "Cessna":
                        holder.imageView.setImageResource(R.drawable.cessna_bw_entry);
                        break;
                    case "Fighter":
                        holder.imageView.setImageResource(R.drawable.fighter_bw_entry);
                        break;
                    case "Helicopter":
                        holder.imageView.setImageResource(R.drawable.helicopter_bw_entry);
                        break;
                    case "Acrobatic":
                        holder.imageView.setImageResource(R.drawable.acrobatic_bw_entry);
                        break;
                }
            }
            i++;
        }

         holder.imageView.setOnClickListener(new View.OnClickListener() {
         //This is the code so that when a particular track is clicked, its information appears in the new layout.
         @Override
         public void onClick(View v) {
         Intent intent = new Intent(context, InfoPlane.class);
         intent.putExtra("MODEL", model);
         context.startActivity(intent);
         }
         });

    }

    //Creates the number of views.
    @Override
    public int getItemCount() {
        return allModels.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.entryImageID);

        }
    }
}
