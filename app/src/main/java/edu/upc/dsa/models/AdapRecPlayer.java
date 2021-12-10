package edu.upc.dsa.models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.upc.dsa.ImagePlanes;
import edu.upc.dsa.R;

public class AdapRecPlayer extends RecyclerView.Adapter<AdapRecPlayer.ViewHolder>{

    Context context;
    ArrayList<Player> list;

    public AdapRecPlayer (Context context, ArrayList<Player> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ranking, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.image.setImageResource(list.get(position).getImage());
        holder.text1.setText(list.get(position).getPlayerName());
        holder.text2.setText(list.get(position).getRol());
        holder.text3.setText(list.get(position).getBitcoins());
        holder.text4.setText(list.get(position).getMaxDistance());
        holder.text5.setText(list.get(position).getTimeOfFlight());
        holder.bar.setRating((float) list.get(position).getQualification());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView text1;
        TextView text2;
        TextView text3;
        TextView text4;
        TextView text5;
        RatingBar bar;
        public ViewHolder(@NonNull View vista) {
            super(vista);
            image = vista.findViewById(R.id.imagePlayer);
            text1 = vista.findViewById(R.id.playerName);
            text2 = vista.findViewById(R.id.rol);
            text3 = vista.findViewById(R.id.bitcoins);
            text4 = vista.findViewById(R.id.maxDistance);
            text5 = vista.findViewById(R.id.timeOfFlight);
            bar = vista.findViewById(R.id.ratingBarRanking);
        }
    }
}

