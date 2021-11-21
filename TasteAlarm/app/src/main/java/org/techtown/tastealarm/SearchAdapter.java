package org.techtown.tastealarm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<Restaurant> mListAll;
    private List<Restaurant> mList;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    private OnItemClickListener clickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvAddress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.search_name);
            tvAddress = itemView.findViewById(R.id.search_address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        if(clickListener != null) {
                            clickListener.onItemClick(view, position);
                        }
                    }
                }
            });
        }
    }

    public SearchAdapter(List<Restaurant> list) {
        this.mList = list;
        mListAll = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_searchrestaurant, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant data = mList.get(position);
        holder.tvName.setText(data.getName());
        holder.tvAddress.setText(data.getAddress());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Restaurant> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(mListAll);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Restaurant data : mListAll) {
                    if(data.getName().toLowerCase().contains(filterPattern)) {
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
