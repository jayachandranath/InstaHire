package com.cloudeation.instahire;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PainterAdapter extends FirestoreRecyclerAdapter<PainterModel, PainterAdapter.PainterViewHolder> {
    private PainterAdapter.OnListItemClick onListItemClick;


    public PainterAdapter(@NonNull FirestoreRecyclerOptions<PainterModel> options, PainterAdapter.OnListItemClick onListItemClick) {
        super(options);
        this.onListItemClick = onListItemClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull PainterAdapter.PainterViewHolder painterViewHolder, int position, @NonNull PainterModel painterModel) {
        painterViewHolder.list_name.setText(painterModel.getFirst() + " " + painterModel.getLast());
        painterViewHolder.list_Address.setText(painterModel.getTaddress());
        painterViewHolder.list_Mobile.setText(painterModel.getMobile());
        painterViewHolder.list_Service.setText(painterModel.getService());


        painterViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @NonNull
    @Override
    public PainterAdapter.PainterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.painter_resource_layout, parent, false);


        return new PainterViewHolder(view);

    }

    public class PainterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView list_name;
        private TextView list_Address;
        private TextView list_Mobile;
        private TextView list_Service;


        public PainterViewHolder(@NonNull View itemView) {
            super(itemView);
            list_name = itemView.findViewById(R.id.tvpaName);
            list_Address = itemView.findViewById(R.id.tvpaAddress);
            list_Mobile = itemView.findViewById(R.id.tvpaMobile);
            list_Service = itemView.findViewById(R.id.tvpaService);
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
