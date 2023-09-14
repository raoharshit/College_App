package com.example.admincollegeapp.clubs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admincollegeapp.R;
import com.example.admincollegeapp.faculty.UpdateTeacherActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.ClubViewAdapter> {

    private List<ClubData> list;
    private Context context;

    public ClubAdapter(List<ClubData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ClubViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.club_item_layout,parent,false);
        return new ClubViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubViewAdapter holder, int position) {
        ClubData item = list.get(position);

        holder.clubName.setText(item.getName());
        holder.facultyName.setText(item.getFacCor());
        holder.studentName.setText(item.getStuCor());
        holder.contact.setText(item.getContact());

        try {
            Picasso.get().load(item.getImage()).into(holder.clubImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateClubActivity.class);
                intent.putExtra("name",item.getName());
                intent.putExtra("facCor",item.getFacCor());
                intent.putExtra("stuCor",item.getStuCor());
                intent.putExtra("contact",item.getContact());
                intent.putExtra("key",item.getKey());
                intent.putExtra("image",item.getImage());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ClubViewAdapter extends RecyclerView.ViewHolder{

        private TextView clubName,facultyName,studentName,contact;
        private ImageView clubImage;
        private Button update;


        public ClubViewAdapter(@NonNull View itemView) {
            super(itemView);
            clubName = itemView.findViewById(R.id.clubName);
            facultyName = itemView.findViewById(R.id.facultyName);
            studentName = itemView.findViewById(R.id.studentName);
            contact = itemView.findViewById(R.id.contact);
            clubImage = itemView.findViewById(R.id.clubImage);
            update = itemView.findViewById(R.id.clubUpdate);
        }
    }
}
