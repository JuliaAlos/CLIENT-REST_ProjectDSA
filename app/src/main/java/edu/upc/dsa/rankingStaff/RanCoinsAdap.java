package edu.upc.dsa.rankingStaff;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.upc.dsa.R;
import edu.upc.dsa.models.Player;
import edu.upc.dsa.transferObjects.RankingTO;

public class RanCoinsAdap extends RecyclerView.Adapter<RanCoinsAdap.MyViewHolder> {

    List<RankingTO> players;
    Context context;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new
                MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ran_row, parent, false));
    }

    public RanCoinsAdap(List<RankingTO> playerList, Context context){
        players = playerList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RankingTO player = players.get(position);
        holder.userName.setText(player.getUserName());
        holder.rol.setText(player.getRol());
        holder.score.setText(player.getScore());
        //holder.images.setImageResource(player.getImage_url());
    }

    @Override//Numero de items que tenim
    public int getItemCount() {
        return players.size();
    }
    /*****************************************************************
            Representa un sola fila del recycler view
     *****************************************************************/
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView userName,rol,score;
        ImageView images;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            userName = itemView.findViewById(R.id.firstLine);
            rol = itemView.findViewById(R.id.secondLine);
            score = itemView.findViewById(R.id.scoreLine);
            images = itemView.findViewById(R.id.imageView);
            mainLayout = itemView.findViewById(R.id.myLayout);
        }
    }
}
