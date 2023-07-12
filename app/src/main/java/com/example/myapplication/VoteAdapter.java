package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VoteAdapter extends RecyclerView.Adapter<VoteAdapter.ViewHolder>{

    private ArrayList<VoteContents> voteContentsArrayList;

    public void setOnItemclickListener(VoteAdapter.OnItemClickListener onItemClickListener) {
        itemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClicked(int position, String data);
    }
    private OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView idTextView;
        private TextView writeTimeTextView;
        private TextView hitsTextView;
        private TextView titleTextView;
        private TextView writerTextView;
        private LinearLayout vote_recyclerview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.vote_id);
            writeTimeTextView = itemView.findViewById(R.id.vote_write_time);
            hitsTextView = itemView.findViewById(R.id.vote_hits);
            titleTextView = itemView.findViewById(R.id.vote_title_textview);
            writerTextView = itemView.findViewById(R.id.vote_writer);
            vote_recyclerview = itemView.findViewById(R.id.vote_recyclerview_item);
        }
    }
    public VoteAdapter(ArrayList<VoteContents> VoteContentsSet) {
        voteContentsArrayList = VoteContentsSet;
    }

    @NonNull
    @Override
    public VoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vote_recyclerview_item, parent, false);
        VoteAdapter.ViewHolder viewHolder = new VoteAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VoteAdapter.ViewHolder holder, int position) {
        int id = voteContentsArrayList.get(position).getId();
        String id_str = "" + id;
        holder.idTextView.setText(id_str);
        String write_time = voteContentsArrayList.get(position).getUploadTime();
        holder.writeTimeTextView.setText(write_time);
        int hits = voteContentsArrayList.get(position).getHits();
        String hitsStr = "" + hits;
        holder.hitsTextView.setText(hitsStr);
        String title = voteContentsArrayList.get(position).getTitle();
        holder.titleTextView.setText(title);
        String writer = voteContentsArrayList.get(position).getWriter();
        holder.writerTextView.setText(writer);

        String contents = voteContentsArrayList.get(position).getContents();

        holder.vote_recyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();

                Intent voteInfoActivity = new Intent(context, VoteInfoActivity.class);

                voteInfoActivity.putExtra("id", id);
                voteInfoActivity.putExtra("write_time", write_time);
                voteInfoActivity.putExtra("hits", hitsStr);
                voteInfoActivity.putExtra("title", title);
                voteInfoActivity.putExtra("writer", writer);
                voteInfoActivity.putExtra("contents", contents);

                ((PostActivity)context).startActivity(voteInfoActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (voteContentsArrayList != null) {
            return voteContentsArrayList.size();
        }
        else return 0;
    }
    public void setList(ArrayList<VoteContents> newList){
        voteContentsArrayList = newList;
    }
}
