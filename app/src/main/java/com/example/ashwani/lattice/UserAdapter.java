package com.example.ashwani.lattice;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    List<User> userList =new ArrayList<>();
    public UserAdapter(List<User> userList) {
        this.userList=userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_recyclerview,parent,false);
       return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StringBuilder stringBuilder=new StringBuilder("");
        User user =userList.get(position);


            User u=userList.get(position);
            stringBuilder.append(u.getUserName()+" \n"+u.getUserAddress()+"\n "+u.getUserEmail()
                    +" \n"+u.getUserPhone()+"\n"+u.getUserPassword());

            holder.tv.setText(stringBuilder.toString());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.user_data_recycler_view);
        }
    }
}
