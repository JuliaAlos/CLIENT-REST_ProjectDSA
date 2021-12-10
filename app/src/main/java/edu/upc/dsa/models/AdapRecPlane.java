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

public class AdapRecPlane extends RecyclerView.Adapter<AdapRecPlane.ViewHolder>{

    Context context;
    ArrayList<Plane> list;

    public AdapRecPlane (Context context, ArrayList<Plane> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.store, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.image.setImageResource(list.get(position).getImagePlane());
        holder.text1.setText(list.get(position).getModel());
        holder.text2.setText(list.get(position).getDescription());
        holder.bar.setRating((float) list.get(position).getQualification());
        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent (context, ImagePlanes.class);
                intent.putExtra("planeData", list.get(position).getImagePlane());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView text1;
        TextView text2;
        RatingBar bar;
        ConstraintLayout main;
        public ViewHolder(@NonNull View vista) {
            super(vista);
            image = vista.findViewById(R.id.imRec);
            text1 = vista.findViewById(R.id.tiRec);
            text2 = vista.findViewById(R.id.desRec);
            bar = vista.findViewById(R.id.ratingBarRec);
            main = vista.findViewById(R.id.main);
        }
    }
}
