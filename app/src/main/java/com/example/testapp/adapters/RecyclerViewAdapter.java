package com.example.testapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.testapp.Model.Individual;
import com.example.testapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public ArrayList<Individual> individualsList;

    public RecyclerViewAdapter() { individualsList = new ArrayList<>(); }

    public void setIndividualsModelList (ArrayList<Individual> individualsList)
    {
        this.individualsList = individualsList;
        notifyDataSetChanged();
    }

    //==============================================================================================
    // ViewHolder Class
    //==============================================================================================

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView avatar;
        TextView nameText;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            avatar =(CircleImageView)itemView.findViewById(R.id.avatar);
            nameText = (TextView)itemView.findViewById(R.id.nameText);
        }
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        String name = individualsList.get(position).getFirstName() + " " + individualsList.get(position).getLastName();
        holder.nameText.setText(name);
        Picasso.get().load(individualsList.get(position).getAvatar()).into(holder.avatar);
    }

    @Override
    public int getItemCount()
    {
        return individualsList.size();
    }
}
