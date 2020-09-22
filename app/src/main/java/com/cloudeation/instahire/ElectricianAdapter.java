package com.cloudeation.instahire;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ElectricianAdapter extends FirestoreRecyclerAdapter<ElectricianModel, ElectricianAdapter.ElectricianViewHolder> {
    private ElectricianAdapter.OnListItemClick onListItemClick;


    public ElectricianAdapter(@NonNull FirestoreRecyclerOptions<ElectricianModel> options, ElectricianAdapter.OnListItemClick onListItemClick) {
        super(options);
        this.onListItemClick = onListItemClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull ElectricianAdapter.ElectricianViewHolder electricianViewHolder, int position, @NonNull ElectricianModel electricianModel) {
        electricianViewHolder.list_name.setText(electricianModel.getFirst() + " " + electricianModel.getLast());
        electricianViewHolder.list_Address.setText(electricianModel.getTaddress());
        electricianViewHolder.list_Mobile.setText(electricianModel.getMobile());
        electricianViewHolder.list_Service.setText(electricianModel.getService());


        electricianViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @NonNull
    @Override
    public ElectricianAdapter.ElectricianViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.electrician_resourcefile, parent, false);


        return new ElectricianViewHolder(view);

    }

    public class ElectricianViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView list_name;
        private TextView list_Address;
        private TextView list_Mobile;
        private TextView list_Service;


        public ElectricianViewHolder(@NonNull View itemView) {
            super(itemView);
            list_name = itemView.findViewById(R.id.tveName);
            list_Address = itemView.findViewById(R.id.tveAddress);
            list_Mobile = itemView.findViewById(R.id.tveMobile);
            list_Service = itemView.findViewById(R.id.tveService);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onListItemClick.onItemClick(getAdapterPosition());

        }

    }
    public interface OnListItemClick {
        void onItemClick(int position);

    }




}

