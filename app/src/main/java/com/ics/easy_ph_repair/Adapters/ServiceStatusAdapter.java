package com.ics.easy_ph_repair.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ics.easy_ph_repair.JavaFiles.ServiceStatusData;
import com.ics.easy_ph_repair.NavigationActivity;
import com.ics.easy_ph_repair.R;
import com.ics.easy_ph_repair.ui.CollectUnit.CollectUnitFragment;

import java.util.List;

public class ServiceStatusAdapter extends RecyclerView.Adapter<ServiceStatusAdapter.MyViewHolder> {
    public static String JobId;
    private List<ServiceStatusData> modelList;

    private Context context;

    public ServiceStatusAdapter(Activity activity, List<ServiceStatusData> my_order_modelList) {
        this.context = activity.getBaseContext();
        this.modelList = my_order_modelList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_order_no, tv_order_status, tv_office_add, tv_time, tv_price, tv_item;

        public MyViewHolder(View view) {
            super(view);
            tv_order_no = (TextView) view.findViewById(R.id.tv_order_no);
            tv_order_status = (TextView) view.findViewById(R.id.tv_order_status);

        }
    }

    public ServiceStatusAdapter(List<ServiceStatusData> my_order_modelList) {
        this.modelList = my_order_modelList;
    }

    @Override
    public ServiceStatusAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.serviceapprovalitem, parent, false);

        context = parent.getContext();

        return new ServiceStatusAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ServiceStatusAdapter.MyViewHolder holder, int position) {
        final ServiceStatusData my_locatio_model = modelList.get(position);
//        holder.tv_office_add.setText(my_locatio_model.getPg_descri());
        /*My_Locatio_model mList = modelList.get(position);

        holder.tv_office_add.setText(mList.getPg_descri());*/
        holder.tv_order_no.setText(my_locatio_model.getJobid());

        holder.tv_order_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.tv_order_status.getText().toString().equals("Tap Here to Assign"))
                {
                    CollectUnitFragment fragment = new CollectUnitFragment();
                    Bundle bundle = new Bundle();
                    Log.e("jobid" , ""+my_locatio_model.getJobid().toString());
                    bundle.putString("jid", ""+my_locatio_model.getJobid().toString());
                    JobId = my_locatio_model.getJobid().toString();
                    fragment.setArguments(bundle);
//                    view.getContext().get
                    NavigationActivity.navController.navigate(R.id.nav_collecunit);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

}
