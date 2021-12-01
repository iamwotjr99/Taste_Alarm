package org.techtown.tastealarm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private List<Review> mListAll;
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
        mListAll = new ArrayList<>(list);
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
        if(list.getPicture() != null) {
            holder.tvTitle.setText(list.getTitle());
            holder.tvContent.setText(list.getContent());
            holder.tvName.setText(list.getUserName());
            holder.ivImg.setVisibility(View.VISIBLE);
            Glide.with(holder.ivImg.getContext())
                    .load(list.getPicture())
                    .override(80, 80)
                    .into(holder.ivImg);
        } else {
            holder.tvTitle.setText(list.getTitle());
            holder.tvContent.setText(list.getContent());
            holder.tvName.setText(list.getUserName());
            holder.ivImg.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public Filter getFilter() {
        return reviewFilter;
    }

    private Filter reviewFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Review> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(mListAll);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Review data : mListAll) {
                    if(data.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(data);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mList.clear();
            mList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

}
