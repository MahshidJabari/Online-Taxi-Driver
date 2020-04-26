package com.jabari.driver.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jabari.driver.R;
import com.jabari.driver.network.model.History;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private Context context;
    private RecyclerView recyclerView;
    private List<History> histoey_list;

    public HistoryAdapter(Context context, List<History> histoey_list, RecyclerView recyclerView){

        this.context = context;
        this.recyclerView = recyclerView;
        this.histoey_list = histoey_list;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_history, parent, false);
        return new HistoryAdapter.HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder historyViewHolder, int i) {


    }

    @Override
    public int getItemCount() {
        return histoey_list.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_date,tv_money,tv_money_back,tv_payment_side,tv_right_to_pay,tv_start_address,tv_destination;

        public HistoryViewHolder(View view){
            super(view);

            tv_date = view.findViewById(R.id.tv_date);
            tv_money = view.findViewById(R.id.tv_money);
            tv_money_back = view.findViewById(R.id.tv_money_back);
            tv_payment_side = view.findViewById(R.id.tv_payment_side);
            tv_right_to_pay = view.findViewById(R.id.tv_pay_right);
            tv_start_address = view.findViewById(R.id.tv_start_address);
            tv_destination = view.findViewById(R.id.tv_destination_address);
          }
    }
}
