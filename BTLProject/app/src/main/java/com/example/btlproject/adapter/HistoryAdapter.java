package com.example.btlproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlproject.DateUtils;
import com.example.btlproject.R;
import com.example.btlproject.models.Cart.CartItem;
import com.example.btlproject.models.HistoryOrder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{
    private Context context;
    private List<HistoryOrder> historyOrders;
    private String token;

    public HistoryAdapter(Context context, String token) {
        this.context = context;
        this.token = token;
    }
    public void setData(List<HistoryOrder> historyOrders){
        this.historyOrders = historyOrders;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemhistory,parent,false);
        return new HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HistoryOrder historyOrder = historyOrders.get(position);
        HistoryOrder.DeliveryInfo deliveryInfo = historyOrder.getDeliveryInfo();
        holder.txtid.setText(historyOrder.getId()+"");
        holder.txtstatus.setText(historyOrder.getOrderStatus());
        holder.txtTen.setText(deliveryInfo.getName()+" - "+deliveryInfo.getPhoneNumber());
        holder.txtaddress.setText(deliveryInfo.getStreetAddress()+", "+deliveryInfo.getCity()+", "+
                                    deliveryInfo.getState());
        holder.txtTotal.setText(historyOrder.getOrderTotal()+"");
        String formatdate = DateUtils.formatDate(historyOrder.getOrderDate());
        holder.txtDate.setText(formatdate);
    }

    @Override
    public int getItemCount() {
        if(historyOrders != null){
            return historyOrders.size();
        }
        return 0;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView txtid, txtstatus, txtTen, txtaddress, txtTotal, txtDate;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtid = itemView.findViewById(R.id.orderCode);
            txtstatus = itemView.findViewById(R.id.orderStatus);
            txtTen = itemView.findViewById(R.id.customerInfo);
            txtaddress = itemView.findViewById(R.id.customerAddress);
            txtTotal = itemView.findViewById(R.id.codAmount);
            txtDate = itemView.findViewById(R.id.pickupStatus);
        }
    }
}
