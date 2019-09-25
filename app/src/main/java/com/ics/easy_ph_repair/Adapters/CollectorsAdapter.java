package com.ics.easy_ph_repair.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ics.easy_ph_repair.JavaFiles.Collectors;
import com.ics.easy_ph_repair.JavaFiles.ServiceStatusData;
import com.ics.easy_ph_repair.R;

import java.util.List;

public class CollectorsAdapter extends RecyclerView.Adapter<CollectorsAdapter.MyViewHolder> {

    private List<Collectors> modelList;

    private Context context;

    public CollectorsAdapter(Activity activity, List<Collectors> my_order_modelList) {
        this.context = activity.getBaseContext();
        this.modelList = my_order_modelList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView collected_by, service_Report, tv_office_add, tv_time, tv_price, tv_item;

        public MyViewHolder(View view) {
            super(view);
            collected_by = (TextView) view.findViewById(R.id.collected_by);
            service_Report = (TextView) view.findViewById(R.id.service_Report);

        }
    }

    public CollectorsAdapter(List<Collectors> my_order_modelList) {
        this.modelList = my_order_modelList;
    }

    @Override
    public CollectorsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collector, parent, false);

        context = parent.getContext();

        return new CollectorsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CollectorsAdapter.MyViewHolder holder, int position) {
        Collectors my_locatio_model = modelList.get(position);
        holder.collected_by.setText("Collected By: "+my_locatio_model.getCollectedBy());
        holder.service_Report.setText("Service Report: "+my_locatio_model.getServiceReport());
//        holder.tv_office_add.setText(my_locatio_model.getPg_descri());
        /*My_Locatio_model mList = modelList.get(position);

        holder.tv_office_add.setText(mList.getPg_descri());*/
//        holder.tv_order_no.setText(my_locatio_model.getJobi);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

}

