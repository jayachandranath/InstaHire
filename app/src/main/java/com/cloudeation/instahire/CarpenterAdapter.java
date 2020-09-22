package com.cloudeation.instahire;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CarpenterAdapter extends FirestoreRecyclerAdapter<CarpenterModel, CarpenterAdapter.CarpenterViewHolder> {


    private CarpenterAdapter.OnListItemClick onListItemClick;

    public CarpenterAdapter(@NonNull FirestoreRecyclerOptions<CarpenterModel> options, CarpenterAdapter.OnListItemClick onListItemClick) {
        super(options);


        this.onListItemClick = onListItemClick;

    }

    @Override
    protected void onBindViewHolder(@NonNull CarpenterViewHolder carpenterViewHolder, int position, @NonNull CarpenterModel carpenterModel) {

        carpenterViewHolder.list_name.setText(carpenterModel.getFirst() + " " + carpenterModel.getLast());
        carpenterViewHolder.list_Address.setText(carpenterModel.getTaddress());
        carpenterViewHolder.list_Mobile.setText(carpenterModel.getMobile());
        carpenterViewHolder.list_Service.setText(carpenterModel.getService());

        carpenterViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @NonNull
    @Override
    public CarpenterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carpenter_resource, parent, false);


        return new CarpenterViewHolder(view);

    }

    public class CarpenterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView list_name;
        private TextView list_Address;
        private TextView list_Mobile;
        private TextView list_Service;


        public CarpenterViewHolder(@NonNull View itemView) {
            super(itemView);
            list_name = itemView.findViewById(R.id.tvcName);
            list_Address = itemView.findViewById(R.id.tvcAddress);
            list_Mobile = itemView.findViewById(R.id.tvcMobile);
            list_Service = itemView.findViewById(R.id.tvcService);
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
