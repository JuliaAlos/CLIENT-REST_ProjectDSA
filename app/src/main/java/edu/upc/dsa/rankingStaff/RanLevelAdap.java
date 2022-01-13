package edu.upc.dsa.rankingStaff;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.upc.dsa.R;
import edu.upc.dsa.models.Player;
import edu.upc.dsa.transferObjects.RankingTO;

public class RanLevelAdap extends RecyclerView.Adapter<RanLevelAdap.MyViewHolder> {

    List<RankingTO> players;
    Context context;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new
                MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ran_row, parent, false));
    }

    public RanLevelAdap(List<RankingTO> playerList, Context context){
        players = playerList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RankingTO player = players.get(position);
        holder.userName.setText(player.getUserName());
        holder.rol.setText("");
        holder.score.setText(player.getScore());
        Glide.with(context).load(player.getImage_url()).into(holder.images);
        holder.pos.setText(player.getPos());
        if (player.getPos().equals("1")){
            holder.row.setBackgroundColor(Color.parseColor("#FFD700"));
        } else if (player.getPos().equals("2")){
            holder.row.setBackgroundColor(Color.parseColor("#bfc1c1"));
        } else if (player.getPos().equals("3")){
            holder.row.setBackgroundColor(Color.parseColor("#9c5221"));
        }
    }

    @Override//Numero de items que tenim
    public int getItemCount() {
        return players.size();
    }
    /*****************************************************************
            Representa un sola fila del recycler view
     *****************************************************************/
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView userName,rol,score,pos;
        ImageView images;
        ConstraintLayout mainLayout,row;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            userName = itemView.findViewById(R.id.firstLine);
            rol = itemView.findViewById(R.id.secondLine);
            score = itemView.findViewById(R.id.scoreLine);
            pos = itemView.findViewById(R.id.position);
            images = itemView.findViewById(R.id.imageView);
            mainLayout = itemView.findViewById(R.id.myLayout);
            row=itemView.findViewById(R.id.row);
        }
    }
}
