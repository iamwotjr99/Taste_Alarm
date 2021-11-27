package org.techtown.tastealarm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private List<Review> mList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImg;
        TextView tvTitle;
        TextView tvName;
        TextView tvContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImg = itemView.findViewById(R.id.review_imageView);
            tvTitle = itemView.findViewById(R.id.item_title);
            tvName = itemView.findViewById(R.id.item_nickname);
            tvContent = itemView.findViewById(R.id.item_content);
        }
    }

    public ReviewAdapter(List<Review> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review list = mList.get(position);
        holder.tvTitle.setText(list.getTitle());
        holder.tvContent.setText(list.getContent());
        Glide.with(holder.ivImg.getContext())
                .load(list.getPicture())
                .override(80, 80)
                .into(holder.ivImg);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
