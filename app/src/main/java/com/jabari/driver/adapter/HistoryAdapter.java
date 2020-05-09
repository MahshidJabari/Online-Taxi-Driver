package com.jabari.driver.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jabari.driver.R;
import com.jabari.driver.global.DigitConverter;
import com.jabari.driver.network.model.History;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private Context context;
    private RecyclerView recyclerView;
    private List<History> history_list;
    public HistoryAdapterListener listener;

    public HistoryAdapter(Context context, RecyclerView recyclerView, List<History> history_list, HistoryAdapterListener listener) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.history_list = history_list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_history, parent, false);
        return new HistoryAdapter.HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int i) {
        holder.tv_date.setText(DigitConverter.convert(history_list.get(i).getCreatedAt()));
        holder.tv_code.setText(history_list.get(i).getId());
        holder.tv_money.setText(DigitConverter.convert(String.valueOf(history_list.get(i).getCashPayment())));
        holder.tv_start_address.setText(history_list.get(i).getLocationAddress());
        holder.tv_destination.setText(history_list.get(i).getDestinationAddress());
        if (history_list.get(i).isHaveReturn())
            holder.tv_money_back.setText("دارد");
        else holder.tv_money_back.setText("ندارد");
        if (history_list.get(i).isPayByRequest()) {
            holder.tv_payment_side.setText("مبدا");
            holder.tv_right_to_pay.setText("دارد");
        } else {
            holder.tv_payment_side.setText("مقصد");
            holder.tv_right_to_pay.setText("ندارد");
        }

    }

    @Override
    public int getItemCount() {
        return history_list.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_date, tv_money, tv_money_back, tv_payment_side, tv_right_to_pay, tv_start_address, tv_destination, tv_code, tv_detail;

        public HistoryViewHolder(View view) {
            super(view);

            tv_date = view.findViewById(R.id.tv_date);
            tv_money = view.findViewById(R.id.tv_money);
            tv_money_back = view.findViewById(R.id.tv_money_back);
            tv_payment_side = view.findViewById(R.id.tv_payment_side);
            tv_right_to_pay = view.findViewById(R.id.tv_pay_right);
            tv_start_address = view.findViewById(R.id.tv_start_address);
            tv_destination = view.findViewById(R.id.tv_destination_address);
            tv_code = view.findViewById(R.id.tv_code);
            tv_detail = view.findViewById(R.id.tv_detail);

            tv_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.detailOnClick(view, getAdapterPosition());
                }
            });
        }
    }

    public interface HistoryAdapterListener {

        void detailOnClick(View v, int position);

    }
}
