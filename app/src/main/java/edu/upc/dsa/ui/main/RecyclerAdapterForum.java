package edu.upc.dsa.ui.main;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.upc.dsa.ApiInterface;
import edu.upc.dsa.Forum;
import edu.upc.dsa.InfoPlane;
import edu.upc.dsa.Profile;
import edu.upc.dsa.R;
import edu.upc.dsa.models.ForumEntry;
import edu.upc.dsa.transferObjects.PlaneTO;
import edu.upc.dsa.transferObjects.RankingTO;
import edu.upc.dsa.transferObjects.UserTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerAdapterForum extends RecyclerView.Adapter<RecyclerAdapterForum.ViewHolder>{
    //To create the views
    List<ForumEntry> listEntries;
    Context context;
    String playerName;
    List<RankingTO> listUsers;

    public RecyclerAdapterForum(Context context, List<ForumEntry> listEntries, String playerName, List<RankingTO> listUsers){
        this.listEntries = listEntries;
        this.context = context;
        this.playerName = playerName;
        this.listUsers = listUsers;
    }

    @NonNull
    @Override
    public RecyclerAdapterForum.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.forum_entry,parent,false);
        return new RecyclerAdapterForum.ViewHolder(view);
    }

    //To insert data into the views.
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterForum.ViewHolder holder, int position) {
        //Once we have the data set, we put the data in the holder to display it.
        ForumEntry entry = this.listEntries.get(position);
        holder.user.setText(entry.getUserName());
        holder.message.setText(entry.getMessage());
        for (RankingTO user : listUsers){
            if (user.getUserName().equals(entry.getUserName())){
                Glide.with(context).load(user.getImage_url()).into(holder.profilePic);
            }
        }
    }

    //Creates the number of views.
    @Override
    public int getItemCount() {
        return listEntries.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView user, message;
        ImageView profilePic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.entryImageID);
            user = itemView.findViewById(R.id.userForumID);
            message = itemView.findViewById(R.id.messageForumID);
            profilePic = itemView.findViewById(R.id.imageForumID);


        }
    }
}
