package edu.upc.dsa;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import org.w3c.dom.Text;

import java.util.List;

import edu.upc.dsa.models.Plane;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    List<Plane> data = new ArrayList<>();
    Context context;
    public static final String EXTRA_MESSAGE_1 = "com.example.myfirstapp.MESSAGE";

    //Methods to modify the list of tracks. These should be imported from the server.

    //We create a constructor.
    public RecyclerAdapter(Context context, List<Plane> data){
        this.data = data;
        this.context = context;
    }

    @NonNull
    //To create teh views.
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fleet,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //To insert data into the views.
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //Once we have the data set, we put the data in the holder to display it.
        holder.title.setText(data.get(position).getModel());
        holder.description.setText(data.get(position).getEnginesLife());
        holder.transparentButton.setText(data.get(position).getEnginesLife());
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
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        Button transparentButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.planeModelTextID);
            description = itemView.findViewById(R.id.descriptionID);
            transparentButton = itemView.findViewById(R.id.transparentButtonID);
        }
    }
}
