package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    private ArrayList<PostContents> postContentsArrayList;

    public void setOnItemclickListener(PostAdapter.OnItemClickListener onItemClickListener) {
        itemClickListener = onItemClickListener;
    }

    //click event implementation
    //OnItemClickListener 인터페이스 선언
    public interface OnItemClickListener {
        void onItemClicked(int position, String data);
    }

    //OnItemClickListener 참조 변수 선언
    private OnItemClickListener itemClickListener;

    //OnItemClickListener 전달 메소드
    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView idTextView;
        private TextView writeTimeTextView;
        private TextView hitsTextView;
        private TextView titleTextView;
        private TextView writerTextView;
        private TextView likeNumTextView;
        private LinearLayout board_recyclerview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.post_id);
            writeTimeTextView = itemView.findViewById(R.id.write_time);
            hitsTextView = itemView.findViewById(R.id.hits);
            titleTextView = itemView.findViewById(R.id.title_textview);
            writerTextView = itemView.findViewById(R.id.writer);
            likeNumTextView = itemView.findViewById(R.id.like_num);
            board_recyclerview = itemView.findViewById(R.id.board_recyclerview_item);
        }
    }
    public PostAdapter (ArrayList<PostContents> PostContentsSet) {
        postContentsArrayList = PostContentsSet;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.board_recyclerview_item, parent, false);
        PostAdapter.ViewHolder viewHolder = new PostAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        int id = postContentsArrayList.get(position).getId();
        String id_str = "" + id;
        holder.idTextView.setText(id_str);
        String write_time = postContentsArrayList.get(position).getUploadTime();
        holder.writeTimeTextView.setText(write_time);
        int hits = postContentsArrayList.get(position).getHits();
        String hitsStr = "" + hits;
        holder.hitsTextView.setText(hitsStr);
        String title = postContentsArrayList.get(position).getTitle();
        holder.titleTextView.setText(title);
        String writer = postContentsArrayList.get(position).getWriter();
        holder.writerTextView.setText(writer);
        int like_num = postContentsArrayList.get(position).getLike();
        String like_numStr = "" + like_num;
        holder.likeNumTextView.setText(like_numStr);

        String contents = postContentsArrayList.get(position).getContents();

        holder.board_recyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();

                Intent postInfoActivity = new Intent(context, PostInfoActivity.class);

                postInfoActivity.putExtra("id", id);
                postInfoActivity.putExtra("write_time", write_time);
                postInfoActivity.putExtra("hits", hitsStr);
                postInfoActivity.putExtra("title", title);
                postInfoActivity.putExtra("writer", writer);
                postInfoActivity.putExtra("like_num", like_numStr);
                postInfoActivity.putExtra("contents", contents);

                ((PostActivity)context).startActivity(postInfoActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (postContentsArrayList != null) {
            return postContentsArrayList.size();
        }
        else return 0;
    }
    public void setList(ArrayList<PostContents> newList){
        postContentsArrayList = newList;
    }
}
