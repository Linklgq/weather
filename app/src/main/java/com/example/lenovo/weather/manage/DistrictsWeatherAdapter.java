package com.example.lenovo.weather.manage;

import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.weather.R;
import com.example.lenovo.weather.data.entity.ManageItem;

import java.util.List;

public class DistrictsWeatherAdapter extends RecyclerView.Adapter<DistrictsWeatherAdapter.ViewHolder> {
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mCounty;
        TextView mParentDistrict;
        TextView mTemperature;
        TextView mWeatherType;

        View mItemView;

        public ViewHolder(View itemView) {
            super(itemView);

            mItemView=itemView;

            mCounty=mItemView.findViewById(R.id.tv_county);
            mParentDistrict=mItemView.findViewById(R.id.tv_parentDistrict);
            mTemperature=mItemView.findViewById(R.id.tv_tmp);
            mWeatherType=mItemView.findViewById(R.id.tv_weather_type);
        }
    }

    private List<ManageItem> mList;
    private ManageFragment.ItemListener mItemListener;
    private MenuInflater mMenuInflater;

    public DistrictsWeatherAdapter(List<ManageItem> list,ManageFragment.ItemListener itemListener,
                                   MenuInflater menuInflater) {
        mList = list;
        mItemListener=itemListener;
        mMenuInflater=menuInflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_item,parent,false);
        ViewHolder holder=new ViewHolder(view);

        holder.mItemView.setOnClickListener(v->{
            mItemListener.select(holder.getAdapterPosition());
        });

        PopupMenu popupMenu=new PopupMenu(parent.getContext(),holder.mItemView);
        mMenuInflater.inflate(R.menu.manage_item_menu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.menu_remove:
                    mItemListener.remove(holder.getAdapterPosition());
                    break;
            }
            return true;
        });

        holder.mItemView.setOnLongClickListener(v->{
            popupMenu.show();
            return true;
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ManageItem item=mList.get(position);

        holder.mCounty.setText(item.county);
        holder.mParentDistrict.setText(item.province+"  "+item.city);
        holder.mTemperature.setText(item.temperature+"℃");
        holder.mWeatherType.setText(item.weatherType);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
