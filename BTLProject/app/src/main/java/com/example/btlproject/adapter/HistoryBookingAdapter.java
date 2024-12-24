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
import com.example.btlproject.models.Booking.HistoryBoooking;


import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryBookingAdapter extends RecyclerView.Adapter<HistoryBookingAdapter.BookingViewHolder>{
    private Context context;
    private List<HistoryBoooking> historyBookings;
    private String token;

    public HistoryBookingAdapter(Context context, String token) {
        this.context = context;
        this.token = token;
    }
    public void setData(List<HistoryBoooking> historyBookings){
        this.historyBookings = historyBookings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemhistory,parent,false);
        return new BookingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        HistoryBoooking historyBoooking = historyBookings.get(position);
        holder.txtid.setText(historyBoooking.getId()+"");
        holder.txtstatus.setText(historyBoooking.getBookingStatus());
        holder.txtTen.setText(historyBoooking.getName()+" - "+historyBoooking.getPhoneNumber());
        holder.txtaddress.setText("Số người: "+historyBoooking.getNumberOfGuests()+"");
        String formatdate = DateUtils.formatDate(historyBoooking.getBookingDate());
        holder.txtDate.setText(formatdate);
        holder.txtTotal.setText("");
    }

    @Override
    public int getItemCount() {
        if(historyBookings != null){
            return historyBookings.size();
        }
        return 0;
    }


    public class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView txtid, txtstatus, txtTen, txtaddress, txtTotal, txtDate;
        public BookingViewHolder(@NonNull View itemView) {
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
