package com.example.mugeet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;



import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {


    private String[] localDataSet;

    public  Context context;

    public CustomAdapter(Context context){
        this.context = context;
    }



    public CustomAdapter(String[] items) {localDataSet = items;
    }

    @Override
    //Create an instance of view holder class in every item in our recycler view
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.songs_layout, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    //View holder argument
    //position => index of the item in the array
    public void onBindViewHolder(ViewHolder holder,final int position) {
        holder.songname.setText(localDataSet[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Snackbar.make(context, v,localDataSet[position]+" Selected", Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(v.getContext(),PlaySong.class);
                String currentSong = localDataSet[position];
                int pos = position;
                intent.putExtra("songList", localDataSet);
                intent.putExtra("currentSong", currentSong);
                intent.putExtra("position", pos);
                v.getContext().startActivity(intent);

            }
        });



        // to set onClickListener to the card view itself
//        holder.parent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Snackbar.make(context, v, contacts.get(position).getName() + " Selected", Snackbar.LENGTH_LONG).show();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
    
    //inner class
    //responsible for holding the view items for evey item
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView songname;
//        private CardView parent;
//        private ImageView imageView;
        public ViewHolder(View itemView){
            super(itemView);
            songname = itemView.findViewById(R.id.textView1);
        }

    }
}
