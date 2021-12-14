package edu.upc.dsa;

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

import java.util.List;

import edu.upc.dsa.models.Plane;
import edu.upc.dsa.models.PlaneModel;
import edu.upc.dsa.transferObjects.PlaneTO;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    List<PlaneTO> planesPlayer; //
    List<PlaneModel> allPlanes;
    Context context;
    Integer numPlanes;

    public RecyclerAdapter(Context context, List<PlaneModel> data, List<PlaneTO> planesPlayer){
        this.allPlanes = data;
        this.context = context;
        this.planesPlayer = planesPlayer;
    }

    //To create the views.
    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fleet_model,parent,false);
        return new ViewHolder(view);
    }

    //To insert data into the views.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Once we have the data set, we put the data in the holder to display it.
        PlaneModel plane = allPlanes.get(position);

        for (PlaneModel planeModel : allPlanes){
            String currentPlane = planeModel.getModel();
            int i = 0;
            int found = 0;
            while ((found == 0) && (i < this.planesPlayer.size())){
                if (currentPlane.equals(this.planesPlayer.get(i).getModel())){
                    found = 1;
                }
            }
            if (found == 1){
                if (currentPlane.equals("Airbus")){
                    holder.imageView.setImageResource(R.drawable.a320_entry);
                }
                if (currentPlane.equals("Cessna")){
                    holder.imageView.setImageResource(R.drawable.cessna_entry);
                }
                if (currentPlane.equals("Fighter")){
                    holder.imageView.setImageResource(R.drawable.fighter_entry);
                }

            }
            if (found == 0){
                if (currentPlane.equals("Airbus")){
                    holder.imageView.setImageResource(R.drawable.a320_bw_entry);
                }
                if (currentPlane.equals("Cessna")){
                    holder.imageView.setImageResource(R.drawable.cessna_bw_entry);
                }
                if (currentPlane.equals("Fighter")){
                    holder.imageView.setImageResource(R.drawable.fighter_bw_entry);
                }

            }
        }



        switch (plane.getModel()) {
            case "Airbus":
                holder.imageView.setImageResource(R.drawable.a320_entry);
                break;
            case "Cessna":
                holder.imageView.setImageResource(R.drawable.cessna_entry);
                break;
            case "Fighter":
                holder.imageView.setImageResource(R.drawable.fighter_entry);
                break;
        }


        /**
        holder.transparentButton.setOnClickListener(new View.OnClickListener() {
            //This is the code so that when a particular track is clicked, its information appears in the new layout.
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Info page of track " + data.get(position).title + " opens" , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, infoTrack.class);
                intent.putExtra(EXTRA_MESSAGE_1, holder.title.getText().toString());
                context.startActivity(intent);
            }

        });
         */
    }

    //Creates the number of views.
    @Override
    public int getItemCount() {
        this.numPlanes = allPlanes.size();
        return this.numPlanes;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.entryImageID);

        }
    }
}
