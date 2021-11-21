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

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private List<Menu> menuList;

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivImg;
        TextView tvName;
        TextView tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImg = itemView.findViewById(R.id.notice_item_imageview);
            tvName = itemView.findViewById(R.id.notice_item_menu);
            tvPrice = itemView.findViewById(R.id.notice_item_cost);
        }
    }

    public MenuAdapter(List<Menu> list) {
        this.menuList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_noticerestaurant, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Menu list = menuList.get(position);
        holder.tvName.setText(list.getMenu());
        holder.tvPrice.setText(list.getPrice());
        Glide.with(holder.ivImg.getContext())
                .load(list.getPicture())
                .into(holder.ivImg);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }
}
