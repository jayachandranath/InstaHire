package com.cloudeation.instahire;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlumberFirestoreAdapter extends FirestoreRecyclerAdapter<PlumberModel, PlumberFirestoreAdapter.PlumberViewHolder> {
    private OnListItemClick onListItemClick;


    public PlumberFirestoreAdapter(@NonNull FirestoreRecyclerOptions<PlumberModel> options, OnListItemClick onListItemClick) {
        super(options);
        this.onListItemClick = onListItemClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull PlumberViewHolder plumberViewHolder, int position, @NonNull PlumberModel plumberModel) {
        plumberViewHolder.list_name.setText(plumberModel.getFirst() + " " + plumberModel.getLast());
        plumberViewHolder.list_Address.setText(plumberModel.getTaddress());
        plumberViewHolder.list_Mobile.setText(plumberModel.getMobile());
        plumberViewHolder.list_Service.setText(plumberModel.getService());


        plumberViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @NonNull
    @Override
    public PlumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plumber_resourse_layout, parent, false);


        return new PlumberViewHolder(view);

    }

    public class PlumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView list_name;
        private TextView list_Address;
        private TextView list_Mobile;
        private TextView list_Service;


        public PlumberViewHolder(@NonNull View itemView) {
            super(itemView);
            list_name = itemView.findViewById(R.id.tvpName);
            list_Address = itemView.findViewById(R.id.tvpAddress);
            list_Mobile = itemView.findViewById(R.id.tvpMobile);
            list_Service = itemView.findViewById(R.id.tvpService);
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
